package bespalhuk.kcoroutines.app.adapter.input.integration.queue.user

import bespalhuk.kcoroutines.abstraction.IntegrationTest
import bespalhuk.kcoroutines.app.adapter.output.integration.queue.user.UpdateStarterQueueProducer
import bespalhuk.kcoroutines.app.adapter.output.persistence.UserDocumentRepository
import bespalhuk.kcoroutines.app.adapter.output.persistence.mapper.toDocument
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.Move
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.MoveItem
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.PokemonResponse
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.domain.vo.UpdateStarterOutput
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

class UpdateStarterQueueConsumerIT(
    @Autowired
    private val userDocumentRepository: UserDocumentRepository,
    @Autowired
    private val updateStarterQueueProducer: UpdateStarterQueueProducer,
) : IntegrationTest() {

    @AfterEach
    fun afterEach() = runBlocking {
        userDocumentRepository.deleteAll()
    }

    @Test
    fun `given update output, update user starter`() = runTest {
        val user = userDocumentRepository.save(
            UserDataProvider().newUser().toDocument()
        )

        val starter = StarterPokemonEnum.BULBASAUR

        stub(starter)

        val output = UpdateStarterOutput(
            user.id!!,
            starter,
        )
        updateStarterQueueProducer.publish(output)

        withContext(Dispatchers.Default.limitedParallelism(1)) {
            withTimeout(2000) {
                while (userDocumentRepository.findById(output.id)?.team?.starter != starter) {
                    delay(100)
                }
            }
        }

        val found = userDocumentRepository.findById(output.id)
        assertThat(found?.team?.starter).isEqualTo(starter)
        assertThat(found?.team?.starterMove).isNotEqualTo(user.team.starterMove)
    }

    private fun stub(starter: StarterPokemonEnum) {
        val response = response()
        PokemonApiStub.retrieve(starter.number, HttpStatus.OK, toJson(response))
    }

    private fun response(): PokemonResponse =
        PokemonResponse(
            listOf(
                MoveItem(
                    Move("vine whip")
                )
            )
        )
}
