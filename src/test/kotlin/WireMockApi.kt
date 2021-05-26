import com.github.tomakehurst.wiremock.client.WireMock
import com.google.gson.Gson

class WireMockApi {
    private val wiremock = WireMock("localhost", 8080)
    private val gson = Gson()

    fun setup() {
        wiremock.register(
            WireMock.post(WireMock.urlPathEqualTo("/v1/systems/ping"))
                .willReturn(WireMock.okJson("""{"hello": "world"}""")))
    }

    fun verifyRequestPost(path: String, body: Map<String, Any>) {
        wiremock.verifyThat(
            WireMock.postRequestedFor(WireMock.urlPathEqualTo(path))
                .withRequestBody(WireMock.equalToJson(gson.toJson(body), false, true))
        )
    }
}
