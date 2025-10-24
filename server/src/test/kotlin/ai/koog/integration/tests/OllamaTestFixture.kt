package ai.koog.integration.tests

import ai.koog.prompt.executor.llms.SingleLLMPromptExecutor
import ai.koog.prompt.executor.ollama.client.OllamaClient
import ai.koog.prompt.llm.OllamaModels
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class OllamaTestFixture(host: String = "localhost", port: Int) {

    val client: OllamaClient by lazy {
        val baseUrl = "http://$host:$port"
        waitForOllamaServer(baseUrl)

        OllamaClient(baseUrl).also {
            // Always pull the models to ensure they're available
            runBlocking {
                try {
                    it.getModelOrNull(model.id, pullIfMissing = true)
                    it.getModelOrNull(visionModel.id, pullIfMissing = true)
                    it.getModelOrNull(moderationModel.id, pullIfMissing = true)
                } catch (e: Exception) {
                    println("Failed to pull models: ${e.message}")
                    throw e
                }
            }
        }
    }

    val executor: SingleLLMPromptExecutor by lazy { SingleLLMPromptExecutor(client) }
    val model = OllamaModels.Meta.LLAMA_3_2
    val visionModel = OllamaModels.Granite.GRANITE_3_2_VISION
    val moderationModel = OllamaModels.Meta.LLAMA_GUARD_3

    private fun waitForOllamaServer(baseUrl: String) {
        val httpClient = HttpClient {
            install(HttpTimeout) {
                connectTimeoutMillis = 1000
            }
        }

        val maxAttempts = 100

        runBlocking {
            for (attempt in 1..maxAttempts) {
                try {
                    val response = httpClient.get(baseUrl)
                    if (response.status.isSuccess()) {
                        httpClient.close()
                        return@runBlocking
                    }
                } catch (e: Exception) {
                    if (attempt == maxAttempts) {
                        httpClient.close()
                        throw IllegalStateException(
                            "Ollama server didn't respond after $maxAttempts attemps",
                            e
                        )
                    }
                }
                delay(1000)
            }
        }
    }
}
