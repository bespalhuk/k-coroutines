package bespalhuk.kcoroutines.core.usecase

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.core.common.exception.UserNotFoundException
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.port.output.FindUserPortOut
import bespalhuk.kcoroutines.core.port.output.SaveUserPortOut
import bespalhuk.kcoroutines.core.port.output.UpdateLegendaryPortOut
import bespalhuk.kcoroutines.core.port.output.UpdateStarterPortOut
import bespalhuk.kcoroutines.dataprovider.UpdateUserInputDataProvider
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

class UpdateUserUseCaseTest : UnitTest() {

    private lateinit var updateUserUseCase: UpdateUserUseCase

    private lateinit var findUserPortOut: FindUserPortOut
    private lateinit var saveUserPortOut: SaveUserPortOut
    private lateinit var updateStarterPortOut: UpdateStarterPortOut
    private lateinit var updateLegendaryPortOut: UpdateLegendaryPortOut

    @BeforeEach
    fun beforeEach() {
        findUserPortOut = mockk()
        saveUserPortOut = mockk()
        updateStarterPortOut = mockk()
        updateLegendaryPortOut = mockk()
        updateUserUseCase = UpdateUserUseCase(
            findUserPortOut,
            saveUserPortOut,
            updateStarterPortOut,
            updateLegendaryPortOut,
        )
    }

    @Test
    fun `verify calls when input return success and user has the same team members`() = runTest {
        val input = UpdateUserInputDataProvider().input()
        val user = UserDataProvider().user()

        coEvery {
            findUserPortOut.find(any())
        } returns user

        coEvery {
            saveUserPortOut.save(any())
        } returns user


        val updated = updateUserUseCase.update(input)
        assertThat(updated.isSuccess).isTrue
        assertThat(updated.getOrNull())
            .usingRecursiveComparison()
            .isEqualTo(user)

        coVerify(exactly = 1) {
            findUserPortOut.find(any())
            saveUserPortOut.save(any())
        }

        coVerify(exactly = 0) {
            updateStarterPortOut.publish(any())
            updateLegendaryPortOut.publish(any())
        }
    }

    @Test
    fun `verify calls when input return success and user has different team members`() = runTest {
        val input = UpdateUserInputDataProvider().input()
        val user = UserDataProvider().user()
        user.team.starter = StarterPokemonEnum.BULBASAUR
        user.team.legendary = LegendaryPokemonEnum.ARTICUNO

        coEvery {
            findUserPortOut.find(any())
        } returns user

        coEvery {
            updateStarterPortOut.publish(any())
        } just Runs

        coEvery {
            updateLegendaryPortOut.publish(any())
        } just Runs

        coEvery {
            saveUserPortOut.save(any())
        } returns user

        val updated = updateUserUseCase.update(input)
        assertThat(updated.isSuccess).isTrue
        assertThat(updated.getOrNull())
            .usingRecursiveComparison()
            .isEqualTo(user)

        coVerify(exactly = 1) {
            findUserPortOut.find(any())
            saveUserPortOut.save(any())
            updateStarterPortOut.publish(any())
            updateLegendaryPortOut.publish(any())
        }
    }

    @Test
    fun `verify calls when user does not exists`() = runTest {
        val input = UpdateUserInputDataProvider().input()

        coEvery {
            findUserPortOut.find(any())
        } throws UserNotFoundException()

        val updated = updateUserUseCase.update(input)
        assertThat(updated.isFailure).isTrue
        assertThrows<UserNotFoundException> {
            updated.getOrThrow()
        }

        coVerify(exactly = 1) {
            findUserPortOut.find(any())
        }

        coVerify(exactly = 0) {
            saveUserPortOut.save(any())
            updateStarterPortOut.publish(any())
            updateLegendaryPortOut.publish(any())
        }
    }
}
