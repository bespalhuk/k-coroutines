package bespalhuk.kcoroutines.app.adapter.output.web.pokemon

import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.PokemonResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "\${integration.client.pokemon-api.name}",
    url = "\${integration.client.pokemon-api.url}"
)
interface PokemonClient {

    @GetMapping("/pokemon/{number}")
    fun retrieve(@PathVariable number: Int): PokemonResponse
}
