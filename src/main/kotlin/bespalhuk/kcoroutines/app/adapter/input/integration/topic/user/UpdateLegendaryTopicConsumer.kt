package bespalhuk.kcoroutines.app.adapter.input.integration.topic.user

import bespalhuk.kcoroutines.app.adapter.common.UpdateLegendaryMessage
import bespalhuk.kcoroutines.app.adapter.common.mapper.toInput
import bespalhuk.kcoroutines.core.port.input.UpdateLegendaryPortIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class UpdateLegendaryTopicConsumer(
    private val updateLegendaryPortIn: UpdateLegendaryPortIn,
) {

    @Bean
    fun updateLegendaryConsumer(): Consumer<Message<UpdateLegendaryMessage>> =
        Consumer { message ->
            CoroutineScope(Dispatchers.IO).launch {
                delay(1000)
                updateLegendaryPortIn.update(message.payload.toInput())
            }
        }
}
