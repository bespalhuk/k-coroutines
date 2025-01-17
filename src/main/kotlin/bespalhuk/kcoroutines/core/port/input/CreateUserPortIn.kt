package bespalhuk.kcoroutines.core.port.input

import bespalhuk.kcoroutines.core.domain.User
import bespalhuk.kcoroutines.core.domain.vo.CreateUserInput

fun interface CreateUserPortIn {
    suspend fun create(input: CreateUserInput): Result<User>
}
