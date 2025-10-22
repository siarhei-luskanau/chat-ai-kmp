import java.util.Properties

fun getServerDomain(properties: () -> Properties): String = (
    System.getProperty("SERVER_DOMAIN")
        ?: properties().getProperty("SERVER_DOMAIN")
    ).let {
    when (it) {
        null -> throw IllegalArgumentException("SERVER_DOMAIN is not set")
        else -> requireNotNull(it)
    }
}

fun getLlmType(properties: () -> Properties): LlmType = (
    System.getProperty("LLM_TYPE")
        ?: properties().getProperty("LLM_TYPE")
    ).let {
    when (it) {
        LlmType.OPENAI.llmName -> LlmType.OPENAI
        LlmType.OLLAMA.llmName -> LlmType.OLLAMA
        else -> throw IllegalArgumentException("Unexpected LLM_TYPE value: $it")
    }
}
