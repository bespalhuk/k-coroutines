package bespalhuk.kcoroutines.core.usecase

import bespalhuk.kcoroutines.core.domain.User
import bespalhuk.kcoroutines.core.domain.service.PokemonService
import bespalhuk.kcoroutines.core.domain.vo.UpdateLegendaryInput
import bespalhuk.kcoroutines.core.port.input.UpdateLegendaryPortIn
import bespalhuk.kcoroutines.core.port.output.FindUserPortOut
import bespalhuk.kcoroutines.core.port.output.SaveUserPortOut
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class UpdateLegendaryUseCase(
    private val findUserPortOut: FindUserPortOut,
    private val pokemonService: PokemonService,
    private val saveUserPortOut: SaveUserPortOut,
) : UpdateLegendaryPortIn {

    override suspend fun update(input: UpdateLegendaryInput): Result<User> {
        log.info("UpdateLegendaryUseCase.update")
        try {
            val user = findUserPortOut.find(input.id)
            update(user, input)
            val saved = saveUserPortOut.save(user)
            return Result.success(saved)
        } catch (exception: Exception) {
            log.error { "Error updating user: ${input.id} - legendary: ${input.legendary.number}" }
            return Result.failure(exception)
        }
    }

    private suspend fun update(
        user: User,
        input: UpdateLegendaryInput,
    ) {
        user.team.legendary = input.legendary
        applyMove(user)
    }

    private suspend fun applyMove(user: User) {
        val move = pokemonService.getMove(user.team.legendary)
        user.apply {
            team.legendaryMove = move
        }
    }
}
