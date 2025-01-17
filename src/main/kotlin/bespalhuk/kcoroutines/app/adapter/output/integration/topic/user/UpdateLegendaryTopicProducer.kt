package bespalhuk.kcoroutines.app.adapter.output.integration.topic.user

import bespalhuk.kcoroutines.app.adapter.common.mapper.toMessage
import bespalhuk.kcoroutines.core.domain.vo.UpdateLegendaryOutput
import bespalhuk.kcoroutines.core.port.output.UpdateLegendaryPortOut
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Component

@Component
class UpdateLegendaryTopicProducer(
    private val streamBridge: StreamBridge,
) : UpdateLegendaryPortOut {

    override suspend fun publish(output: UpdateLegendaryOutput) {
        streamBridge.send("updateLegendaryProducer-out-0", output.toMessage())
    }
}
