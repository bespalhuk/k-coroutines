package bespalhuk.kcoroutines.app.adapter.common.mapper

import bespalhuk.kcoroutines.app.adapter.common.UpdateStarterMessage
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.domain.vo.UpdateStarterInput
import bespalhuk.kcoroutines.core.domain.vo.UpdateStarterOutput

fun UpdateStarterOutput.toMessage(
) = UpdateStarterMessage(
    id = id,
    starterNumber = starter.number,
)

fun UpdateStarterMessage.toInput(
) = UpdateStarterInput(
    id = id,
    starter = StarterPokemonEnum.map(starterNumber),
)
