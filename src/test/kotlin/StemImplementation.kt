import com.thoughtworks.gauge.BeforeScenario
import com.thoughtworks.gauge.Step
import com.thoughtworks.gauge.datastore.ScenarioDataStore

class StemImplementation {
    private val httpClient = HttpClient("http://localhost:8080")
    private val wiremock = WireMockApi()

    @BeforeScenario
    fun setup() {
        wiremock.setup()
    }

    @Step("WireMockの<path>にPOSTリクエストする")
    fun requestPost(path: String) {
        val (status, headers, body) = httpClient.post(path, mapOf("foo" to "bar", "hoge" to mapOf("fuga" to "piyo")))
        DataStore.Body.set(body)
        DataStore.Headers.set(headers)
        DataStore.StatusCode.set(status)
    }

    private val path = DataStore<String>("REQUEST_PATH")
    @Step("WireMockの<path>に")
    fun verifyRequestPath(pathString: String) {
        path.set(pathString)
    }

    private val body = DataStore<MutableMap<String, Any>>("REQUEST_BODY")
    @Step("Key: <key> Value: <value>")
    fun verifyKeyValue(key: String, value: String) {
        (body.get() ?: mutableMapOf<String, Any>())
            .let { MapBuilder(it).put(key, value).build() }
            .let { body.set(it) }
    }

    @Step("のJSONでPOSTリクエストが行われる")
    fun verifyPostWithJson() {
        wiremock.verifyRequestPost(path.get()!!, body.get()!!)
    }
}


class DataStore<T>(val key: String) {
    companion object {
        val StatusCode = DataStore<Int>("STATUS_CODE")
        val Body = DataStore<Map<*, *>>("BODY")
        val Headers = DataStore<Map<String, String>>("HEADERS")
    }

    fun get(): T? {
        return ScenarioDataStore.get(key) as T
    }

    fun set(value: T) {
        ScenarioDataStore.put(key, value)
    }
}
