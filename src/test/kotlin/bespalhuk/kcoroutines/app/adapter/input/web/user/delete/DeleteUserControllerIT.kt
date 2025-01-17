package bespalhuk.kcoroutines.app.adapter.input.web.user.delete

import bespalhuk.kcoroutines.abstraction.IntegrationTest
import bespalhuk.kcoroutines.app.adapter.output.persistence.UserDocumentRepository
import bespalhuk.kcoroutines.dataprovider.UserDocumentDataProvider
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient

class DeleteUserControllerIT(
    @Autowired
    private val webTestClient: WebTestClient,
    @Autowired
    private val userDocumentRepository: UserDocumentRepository,
) : IntegrationTest() {

    object Path {
        const val PATH: String = "/api/users/{id}"
    }

    @Test
    fun `given request, delete successfully`() = runTest {
        val document = UserDocumentDataProvider().document()
        userDocumentRepository.save(document)

        webTestClient.delete()
            .uri {
                it.path(Path.PATH)
                    .build(document.id)
            }
            .header("Content-Type", "application/json")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectStatus().isEqualTo(HttpStatus.NO_CONTENT)
    }
}
