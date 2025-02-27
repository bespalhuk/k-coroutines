package bespalhuk.kcoroutines.core.domain

import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum.entries

enum class StarterPokemonEnum(val number: Int) {
    BULBASAUR(1),
    CHARMANDER(4),
    SQUIRTLE(7),
    PIKACHU(25),
    ;

    companion object {
        fun map(number: Int?): StarterPokemonEnum =
            entries.find { it.number == number } ?: PIKACHU
    }
}
