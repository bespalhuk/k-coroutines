package bespalhuk.kcoroutines.core.port.input

import bespalhuk.kcoroutines.core.domain.User
import bespalhuk.kcoroutines.core.domain.vo.UpdateStarterInput

fun interface UpdateStarterPortIn {
    suspend fun update(input: UpdateStarterInput): Result<User>
}
