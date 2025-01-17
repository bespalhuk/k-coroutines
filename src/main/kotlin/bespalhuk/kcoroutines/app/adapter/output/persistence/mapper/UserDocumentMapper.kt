package bespalhuk.kcoroutines.app.adapter.output.persistence.mapper

import bespalhuk.kcoroutines.app.adapter.output.persistence.UserDocument
import bespalhuk.kcoroutines.core.domain.User

fun User.toDocument(
) = UserDocument(
    id = id,
    createdDate = createdDate,
    lastModified = lastModified,
    username = username,
    team = team,
)

fun UserDocument.toDomain(
) = User(
    id = id,
    createdDate = createdDate,
    lastModified = lastModified,
    username = username,
    team = team,
)
