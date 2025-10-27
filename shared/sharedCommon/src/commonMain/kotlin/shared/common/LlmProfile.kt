package shared.common

enum class LlmProfile(val profileName: String) {
    DMR_DEEPSEEK(profileName = "dmr_deepseek"),
    OLLAMA_GRANITE(profileName = "ollama_granite")
}

fun String.toLlmProfile(): LlmProfile =
    LlmProfile.entries.firstOrNull { it.profileName == this } ?: throw Error("Invalid profile: $this")
