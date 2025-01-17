package bespalhuk.kcoroutines.app.adapter.output.integration.topic.user

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.app.adapter.common.mapper.toMessage
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.vo.UpdateLegendaryOutput
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.cloud.stream.function.StreamBridge
import java.util.UUID

class UpdateLegendaryTopicProducerTest : UnitTest() {

    private lateinit var updateLegendaryQueueProducer: UpdateLegendaryTopicProducer

    private lateinit var streamBridge: StreamBridge

    @BeforeEach
    fun beforeEach() {
        streamBridge = mockk()
        updateLegendaryQueueProducer = UpdateLegendaryTopicProducer(
            streamBridge,
        )
    }

    @Test
    fun `verify calls when publish`() = runTest {
        val output = UpdateLegendaryOutput(
            UUID.randomUUID().toString(),
            LegendaryPokemonEnum.MEW,
        )
        val message = output.toMessage()

        every {
            streamBridge.send(any(), message)
        } returns true

        updateLegendaryQueueProducer.publish(output)

        verify(exactly = 1) {
            streamBridge.send(any(), any())
        }
    }
}
