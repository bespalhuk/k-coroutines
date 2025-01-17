package bespalhuk.kcoroutines.core.usecase

import bespalhuk.kcoroutines.core.domain.User
import bespalhuk.kcoroutines.core.domain.service.PokemonService
import bespalhuk.kcoroutines.core.domain.vo.UpdateStarterInput
import bespalhuk.kcoroutines.core.port.input.UpdateStarterPortIn
import bespalhuk.kcoroutines.core.port.output.FindUserPortOut
import bespalhuk.kcoroutines.core.port.output.SaveUserPortOut
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class UpdateStarterUseCase(
    private val findUserPortOut: FindUserPortOut,
    private val pokemonService: PokemonService,
    private val saveUserPortOut: SaveUserPortOut,
) : UpdateStarterPortIn {

    override suspend fun update(input: UpdateStarterInput): Result<User> {
        log.info("UpdateStarterUseCase.update")
        try {
            val user = findUserPortOut.find(input.id)
            update(user, input)
            val saved = saveUserPortOut.save(user)
            return Result.success(saved)
        } catch (exception: Exception) {
            log.error { "Error updating user: ${input.id} - starter: ${input.starter.number}" }
            return Result.failure(exception)
        }
    }

    private suspend fun update(
        user: User,
        input: UpdateStarterInput,
    ) {
        user.team.starter = input.starter
        applyMove(user)
    }

    private suspend fun applyMove(user: User) {
        val move = pokemonService.getMove(user.team.starter)
        user.apply {
            team.starterMove = move
        }
    }
}
