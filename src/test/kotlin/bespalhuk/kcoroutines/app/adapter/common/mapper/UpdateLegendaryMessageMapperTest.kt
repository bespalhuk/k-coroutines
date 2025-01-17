package bespalhuk.kcoroutines.app.adapter.common.mapper

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.app.adapter.common.UpdateLegendaryMessage
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.vo.UpdateLegendaryOutput
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class UpdateLegendaryMessageMapperTest : UnitTest() {

    @Test
    fun `map UpdateLegendaryOutput to UpdateLegendaryMessage`() {
        val output = UpdateLegendaryOutput(
            UUID.randomUUID().toString(),
            LegendaryPokemonEnum.MEW,
        )

        val message = output.toMessage()
        assertThat(message.id).isEqualTo(output.id)
        assertThat(message.legendaryNumber).isEqualTo(output.legendary.number)
    }

    @Test
    fun `map UpdateLegendaryMessage to UpdateLegendaryInput`() {
        val message = UpdateLegendaryMessage(
            UUID.randomUUID().toString(),
            151,
        )

        val input = message.toInput()
        assertThat(input.id).isEqualTo(message.id)
        assertThat(input.legendary).isEqualTo(LegendaryPokemonEnum.map(message.legendaryNumber))
    }
}
