package bespalhuk.kcoroutines.core.usecase

import bespalhuk.kcoroutines.core.domain.User
import bespalhuk.kcoroutines.core.port.input.FindUserPortIn
import bespalhuk.kcoroutines.core.port.output.FindUserPortOut
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class FindUserUseCase(
    private val findUserPortOut: FindUserPortOut,
) : FindUserPortIn {

    override suspend fun find(id: String): Result<User> {
        log.info("FindUserUseCase.find")
        try {
            val user = findUserPortOut.find(id)
            return Result.success(user)
        } catch (exception: Exception) {
            log.error { "Error finding user: $id" }
            return Result.failure(exception)
        }
    }
}
