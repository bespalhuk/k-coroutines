package bespalhuk.kcoroutines.core.domain.vo

import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum

data class UpdateLegendaryInput(
    val id: String,
    val legendary: LegendaryPokemonEnum,
)
