package shared.koog

import ai.koog.agents.core.agent.AIAgent
import ai.koog.prompt.executor.llms.all.simpleOllamaAIExecutor
import ai.koog.prompt.llm.OllamaModels
import shared.common.GenericResult

class KoogService(private val baseUrlProvider: suspend () -> GenericResult<String>) {

    suspend fun askLlm(agentInput: String): GenericResult<String> = when (val result = baseUrlProvider.invoke()) {
        is GenericResult.Failure -> GenericResult.Failure(error = result.error)
        is GenericResult.Success -> {
            val agent = AIAgent(
                promptExecutor = simpleOllamaAIExecutor(baseUrl = result.result),
                llmModel = OllamaModels.Meta.LLAMA_3_2
            )
            val output = agent.run(agentInput)
            println(output)
            GenericResult.Success(output)
        }
    }
}
