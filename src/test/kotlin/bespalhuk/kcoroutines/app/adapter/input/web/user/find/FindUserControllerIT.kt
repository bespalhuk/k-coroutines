package bespalhuk.kcoroutines.app.adapter.input.web.user.find

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

class FindUserControllerIT(
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
    fun `given request, find successfully`() = runTest {
        val document = UserDocumentDataProvider().document()
        userDocumentRepository.save(document)

        webTestClient.get()
            .uri {
                it.path(Path.PATH)
                    .build(document.id)
            }
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectStatus().isEqualTo(HttpStatus.OK)
            .expectBody()
            .jsonPath("$.id").isEqualTo(document.id!!)
            .jsonPath("$.username").isEqualTo(document.username)
            .jsonPath("$.team").isEqualTo(document.team.name)
            .jsonPath("$.starterMove").isEqualTo(document.team.starterMove!!)
            .jsonPath("$.legendaryMove").isEqualTo(document.team.legendaryMove!!)
    }

    @Test
    fun `given request, fail finding`() = runTest {
        val document = UserDocumentDataProvider().document()

        webTestClient.get()
            .uri {
                it.path(Path.PATH)
                    .build(document.id)
            }
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().is4xxClientError
            .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
    }
}
