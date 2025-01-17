package bespalhuk.kcoroutines.dataprovider

import bespalhuk.kcoroutines.app.adapter.output.persistence.UserDocument
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.domain.User
import java.time.Instant
import java.util.UUID

class UserDocumentDataProvider {

    fun document(): UserDocument = UserDocument(
        UUID.randomUUID().toString(),
        Instant.now(),
        Instant.now(),
        "username",
        User.Team(
            "rocket",
            StarterPokemonEnum.PIKACHU,
            "shock",
            LegendaryPokemonEnum.MEW,
            "hadouken",
        )
    )
}
