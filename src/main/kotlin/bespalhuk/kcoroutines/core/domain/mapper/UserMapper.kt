package bespalhuk.kcoroutines.core.domain.mapper

import bespalhuk.kcoroutines.core.domain.User
import bespalhuk.kcoroutines.core.domain.vo.CreateUserInput

fun CreateUserInput.toDomain(
) = User(
    id = null,
    createdDate = null,
    lastModified = null,
    username = username,
    team = User.Team(
        name = team,
        starter = starter,
        legendary = legendary,
    ),
)
