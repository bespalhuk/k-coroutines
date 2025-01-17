package bespalhuk.kcoroutines.dataprovider

import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.domain.vo.CreateUserInput

class CreateUserInputDataProvider {

    fun input(): CreateUserInput = CreateUserInput(
        "username",
        "rocket",
        StarterPokemonEnum.PIKACHU,
        LegendaryPokemonEnum.MEW,
    )
}
