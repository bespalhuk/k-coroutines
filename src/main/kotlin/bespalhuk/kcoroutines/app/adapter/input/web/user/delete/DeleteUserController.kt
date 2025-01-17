package bespalhuk.kcoroutines.app.adapter.input.web.user.delete

import bespalhuk.kcoroutines.core.port.input.DeleteUserPortIn
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@Tag(name = "user")
@RestController
@RequestMapping("/api/users")
class DeleteUserController(
    private val deleteUserPortIn: DeleteUserPortIn,
) {

    @Operation(summary = "User", description = "Delete user")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun delete(
        @PathVariable("id") id: String,
    ) {
        log.info("DeleteUserController.delete")
        deleteUserPortIn.delete(id)
    }
}
