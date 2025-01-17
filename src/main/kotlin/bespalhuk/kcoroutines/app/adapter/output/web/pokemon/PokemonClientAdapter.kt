package bespalhuk.kcoroutines.app.adapter.output.web.pokemon

import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.dto.PokemonResponse
import bespalhuk.kcoroutines.app.adapter.output.web.pokemon.exception.PokemonResponseException
import bespalhuk.kcoroutines.config.client.exception.NotFoundClientException
import bespalhuk.kcoroutines.core.domain.LegendaryPokemonEnum
import bespalhuk.kcoroutines.core.domain.StarterPokemonEnum
import bespalhuk.kcoroutines.core.port.output.RetrievePokemonPortOut
import feign.FeignException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException

@Component
class PokemonClientAdapter(
    private val pokemonClient: PokemonClient,
) : RetrievePokemonPortOut {

    companion object {
        const val RETRIEVE_POKEMON = "PokemonClientAdapter.retrieve"
    }

    override suspend fun retrieveMoves(
        starter: StarterPokemonEnum,
        legendary: LegendaryPokemonEnum,
    ): Pair<String, String> {
        return try {
            coroutineScope {
                val starterMoves = async { retrieve(starter.number) }
                val legendaryMoves = async { retrieve(legendary.number) }

                Pair(
                    starterMoves.await().moves.random().move.name,
                    legendaryMoves.await().moves.random().move.name
                )
            }
        } catch (exception: RuntimeException) {
            throw PokemonResponseException(exception.message ?: "Failed trying to retrieving moves", exception)
        }
    }

    override suspend fun retrieveMove(starter: StarterPokemonEnum): String =
        retrieveMove(starter.number)

    override suspend fun retrieveMove(legendary: LegendaryPokemonEnum): String =
        retrieveMove(legendary.number)

    private suspend fun retrieveMove(number: Int): String {
        return try {
            retrieve(number).moves.random().move.name
        } catch (exception: RuntimeException) {
            throw PokemonResponseException("Failed trying to retrieving move from pokemon $number", exception)
        }
    }

    private suspend fun retrieve(number: Int): PokemonResponse {
        try {
            return pokemonClient.retrieve(number)
        } catch (exception: FeignException.NotFound) {
            throw NotFoundClientException("Pokemon $number not found.")
        } catch (exception: FeignException.FeignClientException) {
            throw HttpClientErrorException(HttpStatus.valueOf(exception.status()), RETRIEVE_POKEMON)
        } catch (exception: FeignException.FeignServerException) {
            throw HttpServerErrorException(HttpStatus.valueOf(exception.status()), RETRIEVE_POKEMON)
        } catch (exception: RuntimeException) {
            throw exception
        }
    }
}
