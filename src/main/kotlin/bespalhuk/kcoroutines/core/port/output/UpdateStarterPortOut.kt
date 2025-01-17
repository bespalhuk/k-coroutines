package bespalhuk.kcoroutines.core.port.output

import bespalhuk.kcoroutines.core.domain.vo.UpdateStarterOutput

fun interface UpdateStarterPortOut {
    suspend fun publish(output: UpdateStarterOutput)
}
