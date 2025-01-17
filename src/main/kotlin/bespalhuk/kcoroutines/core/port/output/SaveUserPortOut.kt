package bespalhuk.kcoroutines.core.port.output

import bespalhuk.kcoroutines.core.domain.User

fun interface SaveUserPortOut {
    suspend fun save(user: User): User
}
