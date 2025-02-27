package bespalhuk.kcoroutines.app.adapter.input.web.user

import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum

data class UserResponse(
    val id: String,
    val username: String,
    val team: String,
    val starter: StarterPokemonEnum,
    val starterMove: String,
    val legendary: LegendaryPokemonEnum,
    val legendaryMove: String,
)
