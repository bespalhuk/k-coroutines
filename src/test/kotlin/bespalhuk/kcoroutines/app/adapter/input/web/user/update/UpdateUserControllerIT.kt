package bespalhuk.kcoroutines.app.adapter.input.web.user.update

import bespalhuk.kcoroutines.abstraction.IntegrationTest
import bespalhuk.kcoroutines.app.adapter.output.persistence.UserDocumentRepository
import bespalhuk.kcoroutines.dataprovider.UserDocumentDataProvider
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient

class UpdateUserControllerIT(
    @Autowired
    private val webTestClient: WebTestClient,
    @Autowired
    private val userDocumentRepository: UserDocumentRepository,
) : IntegrationTest() {

    object Path {
        const val PATH: String = "/api/users/{id}"
    }

    @AfterEach
    fun afterEach() = runBlocking {
        userDocumentRepository.deleteAll()
    }

    @Test
    fun `given request, update successfully`() = runTest {
        val document = UserDocumentDataProvider().document()
        userDocumentRepository.save(document)

        val request = UpdateUserRequest(
            "leaders",
            document.team.starter.number,
            document.team.legendary.number,
        )

        webTestClient.put()
            .uri {
                it.path(Path.PATH)
                    .build(document.id)
            }
            .header("Content-Type", "application/json")
            .bodyValue(request)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectStatus().isEqualTo(HttpStatus.ACCEPTED)
            .expectBody()
            .jsonPath("$.id").isEqualTo(document.id!!)
            .jsonPath("$.username").isEqualTo(document.username)
            .jsonPath("$.team").isEqualTo(request.team)
            .jsonPath("$.starterMove").isEqualTo(document.team.starterMove!!)
            .jsonPath("$.legendaryMove").isEqualTo(document.team.legendaryMove!!)
    }

    @Test
    fun `given request, fail updating`() = runTest {
        val document = UserDocumentDataProvider().document()

        val request = UpdateUserRequest(
            "leaders",
            document.team.starter.number,
            document.team.legendary.number,
        )

        webTestClient.put()
            .uri {
                it.path(Path.PATH)
                    .build(document.id)
            }
            .header("Content-Type", "application/json")
            .bodyValue(request)
            .exchange()
            .expectStatus().is4xxClientError
            .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
    }
}
