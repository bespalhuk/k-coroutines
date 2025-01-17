package bespalhuk.kcoroutines.app.adapter.input.web.user.update.mapper

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.app.adapter.input.web.user.update.UpdateUserRequest
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.UUID

class UpdateUserWebMapperTest : UnitTest() {

    @Test
    fun `map UpdateUserRequest to UpdateUserInput`() {
        val request = UpdateUserRequest(
            "rocket",
            25,
            151,
        )
        val id = UUID.randomUUID().toString()

        val input = request.toInput(id)
        assertThat(input.id).isEqualTo(id)
        assertThat(input.team).isEqualTo(request.team)
        assertThat(input.starter).isEqualTo(StarterPokemonEnum.map(request.starterNumber))
        assertThat(input.legendary).isEqualTo(LegendaryPokemonEnum.map(request.legendaryNumber))
    }
}
