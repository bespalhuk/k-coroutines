package bespalhuk.kcoroutines.core.usecase

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.service.PokemonService
import bespalhuk.kcoroutines.core.domain.vo.UpdateLegendaryInput
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

class UpdateLegendaryUseCaseTest : UnitTest() {

    private lateinit var updateLegendaryUseCase: UpdateLegendaryUseCase

    private lateinit var findUserPortOut: FindUserPortOut
    private lateinit var pokemonService: PokemonService
    private lateinit var saveUserPortOut: SaveUserPortOut

    @BeforeEach
    fun beforeEach() {
        findUserPortOut = mockk()
        pokemonService = mockk()
        saveUserPortOut = mockk()
        updateLegendaryUseCase = UpdateLegendaryUseCase(
            findUserPortOut,
            pokemonService,
            saveUserPortOut,
        )
    }

    @Test
    fun `verify calls when legendary was changed`() = runTest {
        val user = UserDataProvider().user()
        val input = UpdateLegendaryInput(
            user.id!!,
            LegendaryPokemonEnum.MEWTWO,
        )
        val move = "gou hadouken"

        coEvery {
            findUserPortOut.find(any())
        } returns user

        coEvery {
            pokemonService.getMove(any<LegendaryPokemonEnum>())
        } returns move

        coEvery {
            saveUserPortOut.save(any())
        } returns user

        val updated = updateLegendaryUseCase.update(input)
        assertThat(updated.isSuccess).isTrue
        assertThat(updated.getOrThrow())
            .usingRecursiveComparison()
            .isEqualTo(user)
        assertThat(updated.getOrNull()!!.team.legendaryMove)
            .isEqualTo(move)

        coVerify(exactly = 1) {
            findUserPortOut.find(any())
            pokemonService.getMove(any<LegendaryPokemonEnum>())
            saveUserPortOut.save(any())
        }
    }
}
