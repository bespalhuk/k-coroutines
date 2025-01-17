package bespalhuk.kcoroutines.app.adapter.input.integration.topic.user

import bespalhuk.kcoroutines.abstraction.IntegrationTest
import bespalhuk.kcoroutines.app.adapter.output.integration.topic.user.UpdateLegendaryTopicProducer
import bespalhuk.kcoroutines.app.adapter.output.persistence.UserDocumentRepository
import bespalhuk.kcoroutines.app.adapter.output.persistence.mapper.toDocument
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.Move
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.MoveItem
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.PokemonResponse
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.vo.UpdateLegendaryOutput
import bespalhuk.kcoroutines.dataprovider.UserDataProvider
import bespalhuk.kcoroutines.dataprovider.stub.PokemonApiStub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class UpdateLegendaryTopicConsumerIT(
    @Autowired
    private val userDocumentRepository: UserDocumentRepository,
    @Autowired
    private val updateLegendaryTopicProducer: UpdateLegendaryTopicProducer,
) : IntegrationTest() {

    @AfterEach
    fun afterEach() = runBlocking {
        userDocumentRepository.deleteAll()
    }

    @Test
    fun `given update output, update user legendary`() = runTest {
        val user = userDocumentRepository.save(
            UserDataProvider().newUser().toDocument()
        )

        val legendary = LegendaryPokemonEnum.MEWTWO

        stub(legendary)

        val output = UpdateLegendaryOutput(
            user.id!!,
            legendary,
        )
        updateLegendaryTopicProducer.publish(output)

        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(2000) {
                while (userDocumentRepository.findById(output.id)?.team?.legendary != legendary) {
                    delay(100)
                }
            }
        }

        val found = userDocumentRepository.findById(output.id)
        assertThat(found?.team?.legendary).isEqualTo(legendary)
        assertThat(found?.team?.legendaryMove).isNotEqualTo(user.team.legendaryMove)
    }

    private fun stub(legendary: LegendaryPokemonEnum) {
        val response = response()
        PokemonApiStub.retrieve(legendary.number, HttpStatus.OK, toJson(response))
    }

    private fun response(): PokemonResponse =
        PokemonResponse(
            listOf(
                MoveItem(
                    Move("gou hadouken")
                )
            )
        )
}
