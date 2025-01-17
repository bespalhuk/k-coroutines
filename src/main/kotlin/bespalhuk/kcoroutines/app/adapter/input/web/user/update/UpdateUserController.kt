package bespalhuk.kcoroutines.app.adapter.input.web.user.update

import bespalhuk.kcoroutines.app.adapter.input.web.user.UserResponse
import bespalhuk.kcoroutines.app.adapter.input.web.user.mapper.toResponse
import bespalhuk.kcoroutines.app.adapter.input.web.user.update.mapper.toInput
import bespalhuk.kcoroutines.core.port.input.UpdateUserPortIn
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@Tag(name = "user")
@RestController
@RequestMapping("/api/users")
class UpdateUserController(
    private val updateUserPortIn: UpdateUserPortIn,
) {

    @Operation(summary = "User", description = "Update user")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    suspend fun update(
        @PathVariable("id") id: String,
        @RequestBody request: UpdateUserRequest,
    ): UserResponse {
        log.info("UpdateUserController.update")
        val input = request.toInput(id)
        val result = updateUserPortIn.update(input)
        return result.getOrThrow().toResponse()
    }
}
