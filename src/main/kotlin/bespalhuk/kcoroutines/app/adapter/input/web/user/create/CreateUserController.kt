package bespalhuk.kcoroutines.app.adapter.input.web.user.create

import bespalhuk.kcoroutines.app.adapter.input.web.user.UserResponse
import bespalhuk.kcoroutines.app.adapter.input.web.user.create.mapper.toInput
import bespalhuk.kcoroutines.app.adapter.input.web.user.mapper.toResponse
import bespalhuk.kcoroutines.core.port.input.CreateUserPortIn
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@Tag(name = "user")
@RestController
@RequestMapping("/api/users")
class CreateUserController(
    private val createUserPortIn: CreateUserPortIn,
) {

    @Operation(summary = "User", description = "Create user")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun create(
        @RequestBody request: CreateUserRequest,
    ): UserResponse {
        log.info("CreateUserController.create")
        val input = request.toInput()
        val result = createUserPortIn.create(input)
        return result.getOrThrow().toResponse()
    }
}
