package bespalhuk.kcoroutines.dataprovider.stub

import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.http.HttpStatus

class PokemonApiStub {

    companion object {
        private const val POKEMON_API: String = "/api"

        fun retrieve(number: Int, httpStatus: HttpStatus, body: String) {
            WireMock.stubFor(
                WireMock.get(
                    WireMock.urlPathEqualTo(
                        POKEMON_API + "/pokemon/${number}"
                    ),
                ).willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(httpStatus.value())
                        .withBody(body)
                ),
            )
        }
    }
}
