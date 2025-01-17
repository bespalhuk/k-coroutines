package bespalhuk.kcoroutines.dataprovider

import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.domain.vo.UpdateUserInput
import java.util.UUID

class UpdateUserInputDataProvider {

    fun input(): UpdateUserInput = UpdateUserInput(
        UUID.randomUUID().toString(),
        "rocket",
        StarterPokemonEnum.PIKACHU,
        LegendaryPokemonEnum.MEW,
    )
}
