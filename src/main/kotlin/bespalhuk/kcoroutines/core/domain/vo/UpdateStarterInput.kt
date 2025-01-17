package bespalhuk.kcoroutines.core.domain.vo

import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum

data class UpdateStarterInput(
    val id: String,
    val starter: StarterPokemonEnum,
)
