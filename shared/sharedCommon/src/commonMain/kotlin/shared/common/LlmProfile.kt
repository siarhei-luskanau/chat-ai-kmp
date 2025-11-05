package shared.common

enum class LlmProfile(val profileName: String) {
    GRANITE(profileName = "granite3.2-visio"),
    LLAMA3_2_1B(profileName = "llama3.2:1b"),
    QWEN3_VL_4B(profileName = "qwen3-vl:4b"),
    QWEN3_0_6B(profileName = "qwen3:0.6b")
}

fun String.toLlmProfile(): LlmProfile =
    LlmProfile.entries.firstOrNull { it.profileName == this } ?: throw Error("Invalid profile: $this")
