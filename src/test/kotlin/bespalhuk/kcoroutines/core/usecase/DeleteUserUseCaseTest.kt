package bespalhuk.kcoroutines.core.usecase

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.core.port.output.DeleteUserPortOut
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class DeleteUserUseCaseTest : UnitTest() {

    private lateinit var deleteUserUseCase: DeleteUserUseCase

    private lateinit var deleteUserPortOut: DeleteUserPortOut

    @BeforeEach
    fun beforeEach() {
        deleteUserPortOut = mockk()
        deleteUserUseCase = DeleteUserUseCase(
            deleteUserPortOut,
        )
    }

    @Test
    fun `verify calls when user is deleted`() = runTest {
        val id = UUID.randomUUID().toString()

        coEvery {
            deleteUserPortOut.delete(any())
        } just Runs

        deleteUserUseCase.delete(id)

        coVerify(exactly = 1) {
            deleteUserPortOut.delete(any())
        }
    }
}
