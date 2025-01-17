package bespalhuk.kcoroutines.core.domain

import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum.entries

enum class LegendaryPokemonEnum(val number: Int) {
    ARTICUNO(144),
    ZAPDOS(145),
    MOLTRES(146),
    MEWTWO(150),
    MEW(151),
    ;

    companion object {
        fun map(number: Int?): LegendaryPokemonEnum =
            entries.find { it.number == number } ?: MEW
    }
}
