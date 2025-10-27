package shared.koog

import ai.koog.prompt.executor.clients.ConnectionTimeoutConfig
import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.executor.clients.deepseek.DeepSeekModels
import ai.koog.prompt.executor.clients.openai.OpenAIClientSettings
import ai.koog.prompt.executor.clients.openai.OpenAILLMClient
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import ai.koog.prompt.executor.ollama.client.OllamaClient
import ai.koog.prompt.llm.LLModel
import ai.koog.prompt.llm.OllamaModels
import shared.common.LlmProfile
import shared.common.toLlmProfile

object LLMClientFactory {

    suspend fun createLLMClient(baseUrlProvider: (() -> String)?): Pair<LLMClient, LLModel> {
        val baseUrl = baseUrlProvider?.invoke() ?: "http://localhost:11434"
        println("LLMClientFactory: $LLM_TYPE base url : $baseUrl")

        val model: LLModel = when (LLM_TYPE.toLlmProfile()) {
            LlmProfile.OLLAMA_GRANITE -> OllamaModels.Granite.GRANITE_3_2_VISION
            LlmProfile.DMR_DEEPSEEK -> DeepSeekModels.DeepSeekReasoner
        }

        val client: LLMClient = when (LLM_TYPE.toLlmProfile()) {
            LlmProfile.OLLAMA_GRANITE -> OllamaClient(
                baseUrl = baseUrl,
                timeoutConfig = ConnectionTimeoutConfig(
                    requestTimeoutMillis = 60_000_000,
                    socketTimeoutMillis = 60_000_000
                )
            ).also { it.getModelOrNull(model.id, pullIfMissing = true) }

            LlmProfile.DMR_DEEPSEEK -> OpenAILLMClient(
                apiKey = "",
                settings = OpenAIClientSettings(
                    baseUrl = baseUrl,
                    timeoutConfig = ConnectionTimeoutConfig(
                        requestTimeoutMillis = 60_000_000,
                        socketTimeoutMillis = 60_000_000
                    )
                )
            )
        }

        return Pair(client, model)
    }
}
