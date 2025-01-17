package bespalhuk.kcoroutines.core.port.input

import bespalhuk.kcoroutines.core.domain.User
import bespalhuk.kcoroutines.core.domain.vo.UpdateLegendaryInput

fun interface UpdateLegendaryPortIn {
    suspend fun update(input: UpdateLegendaryInput): Result<User>
}
