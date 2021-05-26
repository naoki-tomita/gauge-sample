import com.thoughtworks.gauge.Step
import org.amshove.kluent.shouldBeEqualTo

class ResponseAssertion {
    @Step("ステータスコード<code>が返る")
    fun verifyStatusCode(code: Int) {
        DataStore.StatusCode.get() shouldBeEqualTo code
    }
}