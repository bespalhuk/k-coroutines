package bespalhuk.kcoroutines.app.adapter.input.web.user.mapper

import bespalhuk.kcoroutines.app.adapter.input.web.user.UserResponse
import bespalhuk.kcoroutines.core.domain.User

fun User.toResponse(
) = UserResponse(
    id = id!!,
    username = username,
    team = team.name,
    starter = team.starter,
    starterMove = team.starterMove!!,
    legendary = team.legendary,
    legendaryMove = team.legendaryMove!!,
)
