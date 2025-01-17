package bespalhuk.kcoroutines.app.adapter.output.web.pokemon

import bespalhuk.kcoroutines.abstraction.IntegrationTest
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.Move
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.MoveItem
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.PokemonResponse
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.dataprovider.stub.PokemonApiStub
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

class PokemonClientAdapterIT(
    @Autowired
    private val pokemonClientAdapter: PokemonClientAdapter,
) : IntegrationTest() {

    @Test
    fun `given starter and legendary, retrieve moves`() = runTest {
        val starter = StarterPokemonEnum.PIKACHU
        val starterResponse = response("shock")
        PokemonApiStub.retrieve(starter.number, HttpStatus.OK, toJson(starterResponse))

        val legendary = LegendaryPokemonEnum.MEW
        val legendaryResponse = response("hadouken")
        PokemonApiStub.retrieve(legendary.number, HttpStatus.OK, toJson(legendaryResponse))

        val moves = pokemonClientAdapter.retrieveMoves(starter, legendary)
        assertThat(moves.first).isNotNull()
        assertThat(moves.second).isNotNull()
    }

    private fun response(move: String): PokemonResponse =
        PokemonResponse(
            listOf(
                MoveItem(
                    Move(move)
                )
            )
        )

    @Test
    fun `given starter, retrieve move`() = runTest {
        val starter = StarterPokemonEnum.PIKACHU
        val starterResponse = response("shock")
        PokemonApiStub.retrieve(starter.number, HttpStatus.OK, toJson(starterResponse))

        val move = pokemonClientAdapter.retrieveMove(starter)
        assertThat(move).isNotNull()
    }

    @Test
    fun `given legendary, retrieve move`() = runTest {
        val legendary = LegendaryPokemonEnum.MEW
        val legendaryResponse = response("hadouken")
        PokemonApiStub.retrieve(legendary.number, HttpStatus.OK, toJson(legendaryResponse))

        val move = pokemonClientAdapter.retrieveMove(legendary)
        assertThat(move).isNotNull()
    }
}
