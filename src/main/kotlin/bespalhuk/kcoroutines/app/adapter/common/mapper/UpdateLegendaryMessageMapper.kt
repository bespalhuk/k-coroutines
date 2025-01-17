package bespalhuk.kcoroutines.app.adapter.common.mapper

import bespalhuk.kcoroutines.app.adapter.common.UpdateLegendaryMessage
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.vo.UpdateLegendaryInput
import bespalhuk.kcoroutines.core.domain.vo.UpdateLegendaryOutput

fun UpdateLegendaryOutput.toMessage(
) = UpdateLegendaryMessage(
    id = id,
    legendaryNumber = legendary.number,
)

fun UpdateLegendaryMessage.toInput(
) = UpdateLegendaryInput(
    id = id,
    legendary = LegendaryPokemonEnum.map(legendaryNumber),
)
