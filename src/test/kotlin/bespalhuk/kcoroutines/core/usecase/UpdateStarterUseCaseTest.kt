package bespalhuk.kcoroutines.core.usecase

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.domain.service.PokemonService
import bespalhuk.kcoroutines.core.domain.vo.UpdateStarterInput
import bespalhuk.kcoroutines.core.port.output.FindUserPortOut
import bespalhuk.kcoroutines.core.port.output.SaveUserPortOut
import bespalhuk.kcoroutines.dataprovider.UserDataProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UpdateStarterUseCaseTest : UnitTest() {

    private lateinit var updateStarterUseCase: UpdateStarterUseCase

    private lateinit var findUserPortOut: FindUserPortOut
    private lateinit var pokemonService: PokemonService
    private lateinit var saveUserPortOut: SaveUserPortOut

    @BeforeEach
    fun beforeEach() {
        findUserPortOut = mockk()
        pokemonService = mockk()
        saveUserPortOut = mockk()
        updateStarterUseCase = UpdateStarterUseCase(
            findUserPortOut,
            pokemonService,
            saveUserPortOut,
        )
    }

    @Test
    fun `verify calls when starter was changed`() = runTest {
        val user = UserDataProvider().user()
        val input = UpdateStarterInput(
            user.id!!,
            StarterPokemonEnum.BULBASAUR,
        )
        val move = "vine whip"

        coEvery {
            findUserPortOut.find(any())
        } returns user

        coEvery {
            pokemonService.getMove(any<StarterPokemonEnum>())
        } returns move

        coEvery {
            saveUserPortOut.save(any())
        } returns user

        val updated = updateStarterUseCase.update(input)
        assertThat(updated.isSuccess).isTrue
        assertThat(updated.getOrThrow())
            .usingRecursiveComparison()
            .isEqualTo(user)
        assertThat(updated.getOrNull()!!.team.starterMove)
            .isEqualTo(move)

        coVerify(exactly = 1) {
            findUserPortOut.find(any())
            pokemonService.getMove(any<StarterPokemonEnum>())
            saveUserPortOut.save(any())
        }
    }
}
