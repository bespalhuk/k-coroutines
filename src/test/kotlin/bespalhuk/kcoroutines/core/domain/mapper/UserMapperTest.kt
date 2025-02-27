package bespalhuk.kcoroutines.core.domain.mapper

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.dataprovider.CreateUserInputDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserMapperTest : UnitTest() {

    @Test
    fun `map UserInput to User`() {
        val input = CreateUserInputDataProvider().input()

        val user = input.toDomain()
        assertThat(user.username).isEqualTo(input.username)
        assertThat(user.team.name).isEqualTo(input.team)
        assertThat(user.team.starter).isEqualTo(input.starter)
        assertThat(user.team.legendary).isEqualTo(input.legendary)
    }
}
