package bespalhuk.kcoroutines.core.usecase

import bespalhuk.kcoroutines.core.common.exception.UserAlreadyExistsException
import bespalhuk.kcoroutines.core.domain.User
import bespalhuk.kcoroutines.core.domain.mapper.toDomain
import bespalhuk.kcoroutines.core.domain.service.PokemonService
import bespalhuk.kcoroutines.core.domain.vo.CreateUserInput
import bespalhuk.kcoroutines.core.port.input.CreateUserPortIn
import bespalhuk.kcoroutines.core.port.output.SaveUserPortOut
import mu.KotlinLogging
import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class CreateUserUseCase(
    private val pokemonService: PokemonService,
    private val saveUserPortOut: SaveUserPortOut,
) : CreateUserPortIn {

    override suspend fun create(input: CreateUserInput): Result<User> {
        log.info("CreateUserUseCase.create")
        val user = input.toDomain()
        try {
            applyMoves(user)
            val saved = saveUserPortOut.save(user)
            return Result.success(saved)
        } catch (exception: Exception) {
            return onErrorReturn(exception, input)
        }
    }

    private suspend fun applyMoves(user: User) {
        val moves = pokemonService.getMoves(user.team)
        user.apply {
            team.starterMove = moves.first
            team.legendaryMove = moves.second
        }
    }

    private fun onErrorReturn(
        throwable: Throwable,
        input: CreateUserInput,
    ): Result<User> =
        if (throwable is DuplicateKeyException) {
            log.error { "User already exists: ${input.username}." }
            Result.failure(UserAlreadyExistsException())
        } else {
            log.error { "Generic error." }
            throw throwable
        }
}
