package bespalhuk.kcoroutines.core.port.output

import bespalhuk.kcoroutines.core.domain.User

fun interface FindUserPortOut {
    suspend fun find(id: String): User
}
