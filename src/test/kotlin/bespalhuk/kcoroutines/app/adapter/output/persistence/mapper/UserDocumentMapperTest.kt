package bespalhuk.kcoroutines.app.adapter.output.persistence.mapper

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.dataprovider.UserDataProvider
import bespalhuk.kcoroutines.dataprovider.UserDocumentDataProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class UserDocumentMapperTest : UnitTest() {

    @Test
    fun `map User to UserDocument`() {
        val user = UserDataProvider().user()

        val document = user.toDocument()
        assertThat(document.id).isEqualTo(user.id)
        assertThat(document.createdDate).isEqualTo(user.createdDate)
        assertThat(document.lastModified).isEqualTo(user.lastModified)
        assertThat(document.username).isEqualTo(user.username)
        assertThat(document.team.name).isEqualTo(user.team.name)
        assertThat(document.team.starter).isEqualTo(user.team.starter)
        assertThat(document.team.starterMove).isEqualTo(user.team.starterMove)
        assertThat(document.team.legendary).isEqualTo(user.team.legendary)
        assertThat(document.team.legendaryMove).isEqualTo(user.team.legendaryMove)
    }

    @Test
    fun `map UserDocument to User`() {
        val document = UserDocumentDataProvider().document()

        val user = document.toDomain()
        assertThat(document.id).isEqualTo(user.id)
        assertThat(document.createdDate).isEqualTo(user.createdDate)
        assertThat(document.lastModified).isEqualTo(user.lastModified)
        assertThat(document.username).isEqualTo(user.username)
        assertThat(document.team.name).isEqualTo(user.team.name)
        assertThat(document.team.starter).isEqualTo(user.team.starter)
        assertThat(document.team.starterMove).isEqualTo(user.team.starterMove)
        assertThat(document.team.legendary).isEqualTo(user.team.legendary)
        assertThat(document.team.legendaryMove).isEqualTo(user.team.legendaryMove)
    }
}
