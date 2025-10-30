package shared.common

enum class LlmProfile(val profileName: String) {
    OLLAMA_GRANITE(profileName = "granite3.2-visio"),
    QWEN3_VL_4B(profileName = "qwen3-vl:4b")
}

fun String.toLlmProfile(): LlmProfile =
    LlmProfile.entries.firstOrNull { it.profileName == this } ?: throw Error("Invalid profile: $this")
