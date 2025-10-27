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

fun getLlmType(properties: () -> Properties): String = (
    System.getProperty("LLM_TYPE")
        ?: properties().getProperty("LLM_TYPE")
    ).let {
    when (it) {
        null -> throw IllegalArgumentException("LLM_TYPE is not set")
        else -> requireNotNull(it)
    }
}
