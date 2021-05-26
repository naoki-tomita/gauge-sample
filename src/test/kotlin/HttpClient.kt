import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request.Builder
import okhttp3.RequestBody.Companion.toRequestBody

class HttpClient(val baseUrl: String) {
    val client = OkHttpClient()
    val gson = Gson()
    fun post(path: String, body: Any = emptyMap<String, String>(), headers: Map<String, String> = emptyMap()): Triple<Int, Map<String, String>, Map<*, *>> {
        return Builder()
            .url(baseUrl + path)
            .apply { headers.entries.forEach { header(it.key, it.value) } }
            .post(gson.toJson(body).toRequestBody(APPLICATION_JSON))
            .build()
            .let { client.newCall(it) }
            .execute()
            .let { response ->
                val responseHeaders = response.headers.names()
                    .map { key -> key to response.headers.get(key)!! }
                    .fold(mutableMapOf<String, String>()) { acc, (key, value) -> acc.apply { acc[key] = value } }
                val responseBody = response.body!!.string().let { Gson().fromJson(it, Map::class.java) }
                Triple(response.code, responseHeaders.toMap(), responseBody)
            }
    }
}

val APPLICATION_JSON = "application/json".toMediaTypeOrNull()