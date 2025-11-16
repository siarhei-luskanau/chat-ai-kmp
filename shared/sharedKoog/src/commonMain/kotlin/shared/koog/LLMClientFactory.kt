package shared.koog

import ai.koog.prompt.executor.clients.LLMClient
import ai.koog.prompt.executor.ollama.client.OllamaClient
import ai.koog.prompt.llm.LLMCapability
import ai.koog.prompt.llm.LLMProvider
import ai.koog.prompt.llm.LLModel
import ai.koog.prompt.llm.OllamaModels
import shared.common.LlmProfile
import shared.common.toLlmProfile

object LLMClientFactory {

    suspend fun createLLMClient(baseUrlProvider: (() -> String)?): Pair<LLMClient, LLModel> {
        val baseUrl = baseUrlProvider?.invoke() ?: "http://localhost:11434"
        println("LLMClientFactory: $LLM_TYPE base url : $baseUrl")

        val model: LLModel = when (LLM_TYPE.toLlmProfile()) {
            LlmProfile.GRANITE -> OllamaModels.Granite.GRANITE_3_2_VISION

            LlmProfile.LLAMA3_2_1B -> OllamaModels.Meta.LLAMA_3_2.copy(id = LLM_TYPE)

            LlmProfile.QWEN3_VL_4B -> LLModel(
                provider = LLMProvider.Ollama,
                id = LLM_TYPE,
                capabilities =
                    listOf(
                        LLMCapability.Temperature,
                        LLMCapability.Schema.JSON.Basic,
                        LLMCapability.Tools,
                        LLMCapability.Vision.Image,
                        LLMCapability.Document
                    ),
                contextLength = 256 * 1024
            )

            LlmProfile.QWEN3_0_6B -> LLModel(
                provider = LLMProvider.Ollama,
                id = LLM_TYPE,
                capabilities =
                    listOf(
                        LLMCapability.Temperature,
                        LLMCapability.Schema.JSON.Basic,
                        LLMCapability.Tools,
                        LLMCapability.Vision.Image,
                        LLMCapability.Document
                    ),
                contextLength = 40 * 1024
            )
        }

        val client = OllamaClient(baseUrl = baseUrl)
        client.getModelOrNull(model.id, pullIfMissing = true)

        return Pair(client, model)
    }
}
