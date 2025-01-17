package bespalhuk.kcoroutines.app.adapter.output.integration.queue.user

import bespalhuk.kcoroutines.app.adapter.common.mapper.toMessage
import bespalhuk.kcoroutines.core.domain.vo.UpdateStarterOutput
import bespalhuk.kcoroutines.core.port.output.UpdateStarterPortOut
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Component

@Component
class UpdateStarterQueueProducer(
    private val streamBridge: StreamBridge,
) : UpdateStarterPortOut {

    override suspend fun publish(output: UpdateStarterOutput) {
        streamBridge.send("updateStarterProducer-out-0", output.toMessage())
    }
}
