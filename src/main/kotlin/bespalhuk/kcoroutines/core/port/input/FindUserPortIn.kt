package bespalhuk.kcoroutines.core.port.input

import bespalhuk.kcoroutines.core.domain.User

fun interface FindUserPortIn {
    suspend fun find(id: String): Result<User>
}
