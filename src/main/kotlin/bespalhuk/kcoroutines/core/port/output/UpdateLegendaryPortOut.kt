package bespalhuk.kcoroutines.core.port.output

import bespalhuk.kcoroutines.core.domain.vo.UpdateLegendaryOutput

fun interface UpdateLegendaryPortOut {
    suspend fun publish(output: UpdateLegendaryOutput)
}
