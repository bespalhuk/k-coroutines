package bespalhuk.kcoroutines.core.usecase

import bespalhuk.kcoroutines.core.port.input.DeleteUserPortIn
import bespalhuk.kcoroutines.core.port.output.DeleteUserPortOut
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class DeleteUserUseCase(
    private val deleteUserPortOut: DeleteUserPortOut,
) : DeleteUserPortIn {

    override suspend fun delete(id: String) {
        log.info("DeleteUserUseCase.delete")
        deleteUserPortOut.delete(id)
    }
}
