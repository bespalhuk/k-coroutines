package bespalhuk.kcoroutines.core.usecase

import bespalhuk.kcoroutines.core.domain.User
import bespalhuk.kcoroutines.core.domain.vo.UpdateLegendaryOutput
import bespalhuk.kcoroutines.core.domain.vo.UpdateStarterOutput
import bespalhuk.kcoroutines.core.domain.vo.UpdateUserInput
import bespalhuk.kcoroutines.core.port.input.UpdateUserPortIn
import bespalhuk.kcoroutines.core.port.output.FindUserPortOut
import bespalhuk.kcoroutines.core.port.output.SaveUserPortOut
import bespalhuk.kcoroutines.core.port.output.UpdateLegendaryPortOut
import bespalhuk.kcoroutines.core.port.output.UpdateStarterPortOut
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class UpdateUserUseCase(
    private val findUserPortOut: FindUserPortOut,
    private val saveUserPortOut: SaveUserPortOut,
    private val updateStarterPortOut: UpdateStarterPortOut,
    private val updateLegendaryPortOut: UpdateLegendaryPortOut,
) : UpdateUserPortIn {

    override suspend fun update(input: UpdateUserInput): Result<User> {
        log.info("UpdateUserUseCase.update")
        try {
            val user = findUserPortOut.find(input.id)
            update(user, input)
            val saved = saveUserPortOut.save(user)
            return Result.success(saved)
        } catch (exception: Exception) {
            log.error { "Error updating user: ${input.id}" }
            return Result.failure(exception)
        }
    }

    private suspend fun update(
        user: User,
        input: UpdateUserInput,
    ) {
        user.team.name = input.team
        updateMembers(user, input)
    }

    private suspend fun updateMembers(
        user: User,
        input: UpdateUserInput,
    ) {
        if (user.team.starter != input.starter) {
            updateStarter(input)
        }
        if (user.team.legendary != input.legendary) {
            updateLegendary(input)
        }
    }

    private suspend fun updateStarter(input: UpdateUserInput) =
        updateStarterPortOut.publish(
            UpdateStarterOutput(
                input.id,
                input.starter,
            )
        )

    private suspend fun updateLegendary(input: UpdateUserInput) =
        updateLegendaryPortOut.publish(
            UpdateLegendaryOutput(
                input.id,
                input.legendary,
            )
        )
}
