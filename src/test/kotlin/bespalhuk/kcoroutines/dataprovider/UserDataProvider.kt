package bespalhuk.kcoroutines.dataprovider

import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.domain.User
import java.time.Instant
import java.util.UUID

class UserDataProvider {

    fun newUser(): User = User(
        null,
        null,
        null,
        "username",
        User.Team(
            "rocket",
            StarterPokemonEnum.PIKACHU,
            "shock",
            LegendaryPokemonEnum.MEW,
            "hadouken",
        )
    )

    fun user(): User = User(
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
