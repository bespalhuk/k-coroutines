package bespalhuk.kcoroutines.app.adapter.output.integration.queue.user

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.app.adapter.common.mapper.toMessage
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.domain.vo.UpdateStarterOutput
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.cloud.stream.function.StreamBridge
import java.util.UUID

class UpdateStarterQueueProducerTest : UnitTest() {

    private lateinit var updateStarterQueueProducer: UpdateStarterQueueProducer

    private lateinit var streamBridge: StreamBridge

    @BeforeEach
    fun beforeEach() {
        streamBridge = mockk()
        updateStarterQueueProducer = UpdateStarterQueueProducer(
            streamBridge,
        )
    }

    @Test
    fun `verify calls when publish`() = runTest {
        val output = UpdateStarterOutput(
            UUID.randomUUID().toString(),
            StarterPokemonEnum.PIKACHU,
        )
        val message = output.toMessage()

        every {
            streamBridge.send(any(), message)
        } returns true

        updateStarterQueueProducer.publish(output)

        verify(exactly = 1) {
            streamBridge.send(any(), any())
        }
    }
}
