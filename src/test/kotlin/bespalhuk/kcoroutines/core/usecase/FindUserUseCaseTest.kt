package bespalhuk.kcoroutines.core.usecase

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.core.common.exception.UserNotFoundException
import bespalhuk.kcoroutines.core.port.output.FindUserPortOut
import bespalhuk.kcoroutines.dataprovider.UserDataProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class FindUserUseCaseTest : UnitTest() {

    private lateinit var findUserUseCase: FindUserUseCase

    private lateinit var findUserPortOut: FindUserPortOut

    @BeforeEach
    fun beforeEach() {
        findUserPortOut = mockk()
        findUserUseCase = FindUserUseCase(
            findUserPortOut,
        )
    }

    @Test
    fun `verify calls when user is found`() = runTest {
        val user = UserDataProvider().user()

        coEvery {
            findUserPortOut.find(any())
        } returns user

        val found = findUserUseCase.find(user.id!!)
        assertThat(found.isSuccess).isTrue
        assertThat(found.getOrNull())
            .usingRecursiveComparison()
            .isEqualTo(user)

        coVerify(exactly = 1) {
            findUserPortOut.find(any())
        }
    }

    @Test
    fun `verify calls when user is not found`() = runTest {
        val id = UUID.randomUUID().toString()

        coEvery {
            findUserPortOut.find(any())
        } throws UserNotFoundException()

        val found = findUserUseCase.find(id)
        assertThat(found.isFailure).isTrue
        assertThrows<UserNotFoundException> {
            found.getOrThrow()
        }

        coVerify(exactly = 1) {
            findUserPortOut.find(any())
        }
    }
}
