package shared.llms.config

import ai.koog.prompt.llm.LLModel

data class LlmConfig(val lLModel: LLModel, val container: LlmContainerConfig)

data class LlmContainerConfig(val dockerImageName: String, val port: Int)
