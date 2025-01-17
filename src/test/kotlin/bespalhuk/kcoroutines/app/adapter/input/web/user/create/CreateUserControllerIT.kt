package bespalhuk.kcoroutines.app.adapter.input.web.user.create

import bespalhuk.kcoroutines.abstraction.IntegrationTest
import bespalhuk.kcoroutines.app.adapter.output.persistence.UserDocumentRepository
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.Move
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.MoveItem
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.PokemonResponse
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.dataprovider.UserDocumentDataProvider
import bespalhuk.kcoroutines.dataprovider.stub.PokemonApiStub
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient

class CreateUserControllerIT(
    @Autowired
    private val webTestClient: WebTestClient,
    @Autowired
    private val userDocumentRepository: UserDocumentRepository,
) : IntegrationTest() {

    object Path {
        const val PATH: String = "/api/users"
    }

    @AfterEach
    fun afterEach() = runBlocking {
        userDocumentRepository.deleteAll()
    }

    @Test
    fun `given request, create successfully`() = runTest {
        val document = UserDocumentDataProvider().document()

        val request = CreateUserRequest(
            document.username,
            document.team.name,
            document.team.starter.number,
            document.team.legendary.number,
        )

        stub()

        webTestClient.post()
            .uri {
                it.path(Path.PATH).build()
            }
            .header("Content-Type", "application/json")
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectStatus().isEqualTo(HttpStatus.CREATED)
            .expectBody()
            .jsonPath("$.id").isNotEmpty
            .jsonPath("$.username").isEqualTo(document.username)
            .jsonPath("$.team").isEqualTo(document.team.name)
            .jsonPath("$.starterMove").isEqualTo(document.team.starterMove!!)
            .jsonPath("$.legendaryMove").isEqualTo(document.team.legendaryMove!!)
    }

    @Test
    fun `given request, fail on creation`() = runTest {
        val document = UserDocumentDataProvider().document()
        userDocumentRepository.save(document)

        val request = CreateUserRequest(
            document.username,
            document.team.name,
            document.team.starter.number,
            document.team.legendary.number,
        )

        stub()

        webTestClient.post()
            .uri {
                it.path(Path.PATH).build()
            }
            .header("Content-Type", "application/json")
            .bodyValue(request)
            .exchange()
            .expectStatus().is4xxClientError
            .expectStatus().isEqualTo(HttpStatus.CONFLICT)
    }

    private fun stub() {
        val starter = StarterPokemonEnum.PIKACHU
        val starterResponse = response("shock")
        PokemonApiStub.retrieve(starter.number, HttpStatus.OK, toJson(starterResponse))

        val legendary = LegendaryPokemonEnum.MEW
        val legendaryResponse = response("hadouken")
        PokemonApiStub.retrieve(legendary.number, HttpStatus.OK, toJson(legendaryResponse))
    }

    private fun response(move: String): PokemonResponse =
        PokemonResponse(
            listOf(
                MoveItem(
                    Move(move)
                )
            )
        )
}
