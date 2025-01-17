package bespalhuk.kcoroutines.core.usecase

import bespalhuk.kcoroutines.abstraction.UnitTest
import bespalhuk.kcoroutines.core.domain.service.PokemonService
import bespalhuk.kcoroutines.core.port.output.SaveUserPortOut
import bespalhuk.kcoroutines.dataprovider.CreateUserInputDataProvider
import bespalhuk.kcoroutines.dataprovider.UserDataProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateUserUseCaseTest : UnitTest() {

    private lateinit var createUserUseCase: CreateUserUseCase

    private lateinit var pokemonService: PokemonService
    private lateinit var saveUserPortOut: SaveUserPortOut

    @BeforeEach
    fun beforeEach() {
        pokemonService = mockk()
        saveUserPortOut = mockk()
        createUserUseCase = CreateUserUseCase(
            pokemonService,
            saveUserPortOut,
        )
    }

    @Test
    fun `verify calls when input return success`() = runTest {
        val input = CreateUserInputDataProvider().input()
        val moves = Pair(
            "shock",
            "hadouken",
        )
        val user = UserDataProvider().user()

        coEvery {
            pokemonService.getMoves(any())
        } returns moves

        coEvery {
            saveUserPortOut.save(any())
        } returns user

        val created = createUserUseCase.create(input)
        assertThat(created.isSuccess).isTrue
        assertThat(created.getOrNull())
            .usingRecursiveComparison()
            .isEqualTo(user)

        coVerify(exactly = 1) {
            pokemonService.getMoves(any())
            saveUserPortOut.save(any())
        }
    }
}
