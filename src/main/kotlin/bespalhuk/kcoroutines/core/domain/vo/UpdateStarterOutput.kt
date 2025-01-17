package bespalhuk.kcoroutines.core.domain.vo

import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum

data class UpdateStarterOutput(
    val id: String,
    val starter: StarterPokemonEnum,
)
