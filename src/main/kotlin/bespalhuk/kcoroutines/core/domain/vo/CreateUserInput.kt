package bespalhuk.kcoroutines.core.domain.vo

import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum

data class CreateUserInput(
    val username: String,
    val team: String,
    val starter: StarterPokemonEnum,
    val legendary: LegendaryPokemonEnum,
)
