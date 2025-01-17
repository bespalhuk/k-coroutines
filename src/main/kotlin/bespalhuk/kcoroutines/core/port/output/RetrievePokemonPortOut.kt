package bespalhuk.kcoroutines.core.port.output

import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum

interface RetrievePokemonPortOut {
    suspend fun retrieveMoves(starter: StarterPokemonEnum, legendary: LegendaryPokemonEnum): Pair<String, String>
    suspend fun retrieveMove(starter: StarterPokemonEnum): String
    suspend fun retrieveMove(legendary: LegendaryPokemonEnum): String
}
