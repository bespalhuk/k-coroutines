package bespalhuk.kcoroutines.app.adapter.output.persistence

import bespalhuk.kcoroutines.abstraction.IntegrationTest
import bespalhuk.kcoroutines.core.common.exception.UserNotFoundException
import bespalhuk.kcoroutines.dataprovider.UserDataProvider
import bespalhuk.kcoroutines.dataprovider.UserDocumentDataProvider
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import java.util.UUID

class UserPersistenceAdapterIT(
    @Autowired
    private val userPersistenceAdapter: UserPersistenceAdapter,
    @Autowired
    private val userDocumentRepository: UserDocumentRepository,
) : IntegrationTest() {

    @AfterEach
    fun afterEach() = runBlocking {
        userDocumentRepository.deleteAll()
    }

    @Test
    fun `given user, save successfully`() = runTest {
        val user = UserDataProvider().newUser()

        val saved = userPersistenceAdapter.save(user)
        assertThat(saved.id).isNotNull()
        assertThat(saved).usingRecursiveComparison()
            .ignoringFields("id", "createdDate", "lastModified")
            .isEqualTo(user)
    }

    @Test
    fun `given user, fail to save`() = runTest {
        userDocumentRepository.save(UserDocumentDataProvider().document())

        assertThrows<DuplicateKeyException> {
            userPersistenceAdapter.save(UserDataProvider().newUser())
        }
    }

    @Test
    fun `given id, find successfully`() = runTest {
        val document = UserDocumentDataProvider().document()
        userDocumentRepository.save(document)

        val found = userPersistenceAdapter.find(document.id!!)
        assertThat(found).isNotNull()
    }

    @Test
    fun `given id, fail to find`() = runTest {
        assertThrows<UserNotFoundException> {
            userPersistenceAdapter.find(UUID.randomUUID().toString())
        }
    }

    @Test
    fun `given id, delete successfully when user exists`() = runTest {
        val document = UserDocumentDataProvider().document()
        val id = document.id!!
        userDocumentRepository.save(document)

        var exists = userDocumentRepository.existsById(id)
        assertThat(exists).isTrue()

        userPersistenceAdapter.delete(id)

        exists = userDocumentRepository.existsById(id)
        assertThat(exists).isFalse()
    }

    @Test
    fun `given id, delete successfully when user does not exists`() = runTest {
        val id = UUID.randomUUID().toString()

        val exists = userDocumentRepository.existsById(id)
        assertThat(exists).isFalse()

        userPersistenceAdapter.delete(id)
    }
}
