package bespalhuk.kcoroutines.app.adapter.common.mapper

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.app.adapter.common.UpdateStarterMessage
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.domain.vo.UpdateStarterOutput
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class UpdateStarterMessageMapperTest : UnitTest() {

    @Test
    fun `map UpdateStarterOutput to UpdateStarterMessage`() {
        val output = UpdateStarterOutput(
            UUID.randomUUID().toString(),
            StarterPokemonEnum.PIKACHU,
        )

        val message = output.toMessage()
        assertThat(message.id).isEqualTo(output.id)
        assertThat(message.starterNumber).isEqualTo(output.starter.number)
    }

    @Test
    fun `map UpdateStarterMessage to UpdateStarterInput`() {
        val message = UpdateStarterMessage(
            UUID.randomUUID().toString(),
            25,
        )

        val input = message.toInput()
        assertThat(input.id).isEqualTo(message.id)
        assertThat(input.starter).isEqualTo(StarterPokemonEnum.map(message.starterNumber))
    }
}
