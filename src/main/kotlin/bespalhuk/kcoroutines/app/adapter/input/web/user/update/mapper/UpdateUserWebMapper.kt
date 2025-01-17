package bespalhuk.kcoroutines.app.adapter.input.web.user.update.mapper

import bespalhuk.kcoroutines.app.adapter.input.web.user.update.UpdateUserRequest
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.domain.vo.UpdateUserInput

fun UpdateUserRequest.toInput(
    id: String,
) = UpdateUserInput(
    id = id,
    team = team,
    starter = StarterPokemonEnum.map(starterNumber),
    legendary = LegendaryPokemonEnum.map(legendaryNumber),
)
