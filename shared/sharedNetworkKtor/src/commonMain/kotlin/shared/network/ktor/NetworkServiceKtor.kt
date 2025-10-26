package shared.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.serialization.json.Json
import shared.common.GenericResult
import shared.network.api.NetworkService

class NetworkServiceKtor : NetworkService {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    override suspend fun getLlmUrl(): GenericResult<String> = try {
        client.get(SERVER_URL + "container-port")
            .let { response ->
                if (response.status == HttpStatusCode.OK) {
                    val port: String = response.body()
                    val url = "http://${SERVER_DOMAIN}:$port/"
                    GenericResult.Success(result = url)
                } else {
                    GenericResult.Failure(error = Error(response.status.toString()))
                }
            }
    } catch (error: CancellationException) {
        throw error
    } catch (error: Throwable) {
        GenericResult.Failure(error = error)
    }

    companion object {
        const val SERVER_URL = "http://${SERVER_DOMAIN}:8080/"
    }
}
