package bespalhuk.kcoroutines.app.adapter.input.web.user.find

import bespalhuk.kcoroutines.app.adapter.input.web.user.UserResponse
import bespalhuk.kcoroutines.app.adapter.input.web.user.mapper.toResponse
import bespalhuk.kcoroutines.core.port.input.FindUserPortIn
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@Tag(name = "user")
@RestController
@RequestMapping("/api/users")
class FindUserController(
    private val findUserPortIn: FindUserPortIn,
) {

    @Operation(summary = "User", description = "Find user")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun find(
        @PathVariable("id") id: String,
    ): UserResponse {
        log.info("FindUserController.find")
        val result = findUserPortIn.find(id)
        return result.getOrThrow().toResponse()
    }
}
