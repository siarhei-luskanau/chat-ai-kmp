package shared.koog

import ai.koog.agents.core.agent.AIAgent
import ai.koog.agents.core.tools.ToolRegistry
import ai.koog.agents.ext.tool.SayToUser
import ai.koog.prompt.executor.llms.SingleLLMPromptExecutor
import ai.koog.prompt.executor.ollama.client.OllamaClient
import ai.koog.prompt.llm.OllamaModels
import shared.common.GenericResult

class KoogService(private val baseUrlProvider: suspend () -> GenericResult<String>) {

    suspend fun askLlm(agentInput: String): GenericResult<String> = baseUrlProvider.invoke().mapSuspend { baseUrl ->
        println("KoogService: baseUrl: $baseUrl")
        val client = OllamaClient(baseUrl)
        val model = OllamaModels.Meta.LLAMA_3_2
        println("KoogService: pull ${model.id}")
        client.getModelOrNull(model.id, pullIfMissing = true)
        println("KoogService: create AIAgent")
        val promptExecutor = SingleLLMPromptExecutor(client)
        val agent = AIAgent(
            promptExecutor = promptExecutor,
            llmModel = model,
            temperature = 0.0,
            toolRegistry = ToolRegistry.Companion { tool(SayToUser) },
            maxIterations = 10
        )
        println("KoogService: run agent input: $agentInput")
        val output = agent.run(agentInput)
        println("KoogService: agent output: $output")
        output
    }
}
