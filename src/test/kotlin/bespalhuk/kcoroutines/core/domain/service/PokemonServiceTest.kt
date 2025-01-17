package bespalhuk.kcoroutines.core.domain.service

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.port.output.RetrievePokemonPortOut
import bespalhuk.kcoroutines.dataprovider.UserDataProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PokemonServiceTest : UnitTest() {

    private lateinit var pokemonService: PokemonService

    private lateinit var retrievePokemonPortOut: RetrievePokemonPortOut

    @BeforeEach
    fun beforeEach() {
        retrievePokemonPortOut = mockk()
        pokemonService = PokemonService(
            retrievePokemonPortOut,
        )
    }

    @Test
    fun `verify calls when retrieving moves`() = runTest {
        val team = UserDataProvider().user().team

        val moves = Pair(
            "shock",
            "hadouken",
        )

        coEvery {
            retrievePokemonPortOut.retrieveMoves(
                team.starter,
                team.legendary,
            )
        } returns moves

        val movesGotten = pokemonService.getMoves(team)
        assertThat(movesGotten)
            .usingRecursiveComparison()
            .isEqualTo(moves)

        coVerify(exactly = 1) {
            retrievePokemonPortOut.retrieveMoves(
                team.starter,
                team.legendary,
            )
        }
    }

    @Test
    fun `verify calls when retrieving starter move`() = runTest {
        val starter = StarterPokemonEnum.PIKACHU
        val move = "shock"

        coEvery {
            retrievePokemonPortOut.retrieveMove(starter)
        } returns move

        val moveGotten = pokemonService.getMove(starter)
        assertThat(moveGotten).isEqualTo(move)

        coVerify(exactly = 1) {
            retrievePokemonPortOut.retrieveMove(starter)
        }
    }

    @Test
    fun `verify calls when retrieving legendary move`() = runTest {
        val legendary = LegendaryPokemonEnum.MEW
        val move = "hadouken"

        coEvery {
            retrievePokemonPortOut.retrieveMove(legendary)
        } returns move

        val moveGotten = pokemonService.getMove(legendary)
        assertThat(moveGotten).isEqualTo(move)

        coVerify(exactly = 1) {
            retrievePokemonPortOut.retrieveMove(legendary)
        }
    }
}
