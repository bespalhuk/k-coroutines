package bespalhuk.kcoroutines.app.adapter.input.integration.queue.user

import bespalhuk.kcoroutines.app.adapter.common.UpdateStarterMessage
import bespalhuk.kcoroutines.app.adapter.common.mapper.toInput
import bespalhuk.kcoroutines.core.port.input.UpdateStarterPortIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.context.annotation.Bean
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class UpdateStarterQueueConsumer(
    private val updateStarterPortIn: UpdateStarterPortIn,
) {

    @Bean
    fun updateStarterConsumer(): Consumer<Message<UpdateStarterMessage>> =
        Consumer { message ->
            CoroutineScope(Dispatchers.IO).launch {
                updateStarterPortIn.update(message.payload.toInput())
            }
        }
}
