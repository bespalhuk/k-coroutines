package bespalhuk.kcoroutines.app.adapter.input.web.user.create.mapper

import bespalhuk.kcoroutines.app.adapter.input.web.user.create.CreateUserRequest
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.domain.vo.CreateUserInput

fun CreateUserRequest.toInput(
) = CreateUserInput(
    username = username,
    team = team,
    starter = StarterPokemonEnum.map(starterNumber),
    legendary = LegendaryPokemonEnum.map(legendaryNumber),
)
