package shared.koog

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.llms.SingleLLMPromptExecutor
import ai.koog.prompt.executor.ollama.client.OllamaClient
import ai.koog.prompt.llm.OllamaModels
import shared.common.GenericResult

class KoogService(private val baseUrlProvider: suspend () -> GenericResult<String>) {

    suspend fun askLlm(agentInput: String): GenericResult<String> = baseUrlProvider.invoke().mapSuspend { baseUrl ->
        println("baseUrl: $baseUrl")
        val client = OllamaClient(baseUrl)
        val model = OllamaModels.Meta.LLAMA_3_2
        val visionModel = OllamaModels.Granite.GRANITE_3_2_VISION
        val moderationModel = OllamaModels.Meta.LLAMA_GUARD_3
        client.getModelOrNull(model.id, pullIfMissing = true)
        client.getModelOrNull(visionModel.id, pullIfMissing = true)
        client.getModelOrNull(moderationModel.id, pullIfMissing = true)
        val promptExecutor = SingleLLMPromptExecutor(client)
        val agent = AIAgent(promptExecutor = promptExecutor, llmModel = model)
        val output = agent.run(agentInput)
        println(output)
        output
    }
}
