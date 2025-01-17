package bespalhuk.kcoroutines.app.adapter.output.persistence

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.app.adapter.output.persistence.mapper.toDocument
import bespalhuk.kcoroutines.core.common.exception.UserNotFoundException
import bespalhuk.kcoroutines.dataprovider.UserDataProvider
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DuplicateKeyException
import java.util.UUID

class UserPersistenceAdapterTest : UnitTest() {

    private lateinit var userPersistenceAdapter: UserPersistenceAdapter

    private lateinit var userDocumentRepository: UserDocumentRepository

    @BeforeEach
    fun beforeEach() {
        userDocumentRepository = mockk()
        userPersistenceAdapter = UserPersistenceAdapter(
            userDocumentRepository,
        )
    }

    @Test
    fun `verify calls when user is saved successfully`() = runTest {
        val user = UserDataProvider().user()
        val document = user.toDocument()

        coEvery {
            userDocumentRepository.save(any())
        } returns document

        val saved = userPersistenceAdapter.save(user)
        assertThat(saved)
            .usingRecursiveComparison()
            .isEqualTo(user)

        coVerify(exactly = 1) {
            userDocumentRepository.save(any())
        }
    }

    @Test
    fun `verify calls when trying to save user but failed`() = runTest {
        val user = UserDataProvider().user()

        coEvery {
            userDocumentRepository.save(any())
        } throws DuplicateKeyException("exception")

        assertThrows<DuplicateKeyException> {
            userPersistenceAdapter.save(user)
        }

        coVerify(exactly = 1) {
            userDocumentRepository.save(any())
        }
    }

    @Test
    fun `verify calls when user is found successfully`() = runTest {
        val user = UserDataProvider().user()
        val document = user.toDocument()

        coEvery {
            userDocumentRepository.findById(user.id!!)
        } returns document

        val found = userPersistenceAdapter.find(user.id!!)
        assertThat(found)
            .usingRecursiveComparison()
            .isEqualTo(user)

        coVerify(exactly = 1) {
            userDocumentRepository.findById(any<String>())
        }
    }

    @Test
    fun `verify calls when user is not found`() = runTest {
        val id = UUID.randomUUID().toString()

        coEvery {
            userDocumentRepository.findById(id)
        } returns null

        assertThrows<UserNotFoundException> {
            userPersistenceAdapter.find(id)
        }

        coVerify(exactly = 1) {
            userDocumentRepository.findById(any<String>())
        }
    }

    @Test
    fun `verify calls when user is deleted`() = runTest {
        val id = UUID.randomUUID().toString()

        coEvery {
            userDocumentRepository.deleteById(id)
        } just Runs

        userPersistenceAdapter.delete(id)

        coVerify(exactly = 1) {
            userDocumentRepository.deleteById(any<String>())
        }
    }
}
