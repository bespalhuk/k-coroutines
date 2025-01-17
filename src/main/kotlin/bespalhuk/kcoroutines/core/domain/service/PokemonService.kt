package bespalhuk.kcoroutines.core.domain.service

import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.domain.User
import bespalhuk.kcoroutines.core.port.output.RetrievePokemonPortOut
import org.springframework.stereotype.Service

@Service
class PokemonService(
    private val retrievePokemonPortOut: RetrievePokemonPortOut,
) {

    suspend fun getMoves(team: User.Team): Pair<String, String> =
        retrievePokemonPortOut.retrieveMoves(team.starter, team.legendary)

    suspend fun getMove(starter: StarterPokemonEnum): String =
        retrievePokemonPortOut.retrieveMove(starter)

    suspend fun getMove(legendary: LegendaryPokemonEnum): String =
        retrievePokemonPortOut.retrieveMove(legendary)
}
