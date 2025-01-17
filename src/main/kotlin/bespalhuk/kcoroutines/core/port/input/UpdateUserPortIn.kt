package bespalhuk.kcoroutines.core.port.input

import bespalhuk.kcoroutines.core.domain.User
import bespalhuk.kcoroutines.core.domain.vo.UpdateUserInput

fun interface UpdateUserPortIn {
    suspend fun update(input: UpdateUserInput): Result<User>
}
