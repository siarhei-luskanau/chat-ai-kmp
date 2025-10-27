package ai.koog.integration.tests.utils

import ai.koog.prompt.executor.clients.anthropic.AnthropicModels
import ai.koog.prompt.executor.clients.google.GoogleModels
import ai.koog.prompt.executor.clients.openai.OpenAIModels
import java.util.stream.Stream
import org.junit.jupiter.params.provider.Arguments

object MediaTestScenarios {
    enum class ImageTestScenario {
        BASIC_PNG,
        BASIC_JPG,

        EMPTY_IMAGE,
        CORRUPTED_IMAGE,
        LARGE_IMAGE, // 20MB for Gemini and OpenAI, 5 MB for Anthropic
        LARGE_IMAGE_ANTHROPIC, // 20MB for Gemini and OpenAI, 5 MB for Anthropic
        SMALL_IMAGE // 1x1 pixel
    }

    enum class TextTestScenario {
        BASIC_TEXT,
        UTF8_ENCODING,
        ASCII_ENCODING,
        UNICODE_TEXT,
        CODE_SNIPPET,
        FORMATTED_TEXT,

        EMPTY_TEXT,
        LONG_TEXT_5_MB,
        CORRUPTED_TEXT
    }

    enum class MarkdownTestScenario {
        BASIC_MARKDOWN,
        HEADERS,
        LISTS,
        LINKS,
        CODE_BLOCKS,
        TABLES,
        FORMATTING,

        MALFORMED_SYNTAX,
        NESTED_FORMATTING,
        EMBEDDED_HTML,
        IRREGULAR_TABLES,
        MATH_NOTATION, // LaTeX
        EMPTY_CODE_BLOCKS,
        SPECIAL_CHARS_HEADERS,
        BROKEN_LINKS,
        EMPTY_MARKDOWN,
        MIXED_INDENTATION,
        COMMENTS,
        COMPLEX_NESTED_LISTS
    }

    enum class AudioTestScenario {
        BASIC_WAV,
        BASIC_MP3,
        CORRUPTED_AUDIO
    }

    @JvmStatic
    fun markdownScenarioModelCombinations(): Stream<Arguments> {
        val scenarios = MarkdownTestScenario.entries.toTypedArray()
        val models = listOf(
            AnthropicModels.Sonnet_3_7,
            GoogleModels.Gemini2_0Flash, // see KG-256
            OpenAIModels.Chat.GPT4o
        )
        return scenarios.flatMap { scenario ->
            models.map { model ->
                Arguments.of(scenario, model)
            }
        }.stream()
    }

    @JvmStatic
    fun imageScenarioModelCombinations(): Stream<Arguments> {
        val scenarios = ImageTestScenario.entries.toTypedArray()
        val models = listOf(
            OpenAIModels.Chat.GPT4o,
            AnthropicModels.Sonnet_3_7,
            GoogleModels.Gemini2_5Pro
        )
        return scenarios.flatMap { scenario ->
            models.map { model ->
                Arguments.of(scenario, model)
            }
        }.stream()
    }

    @JvmStatic
    fun textScenarioModelCombinations(): Stream<Arguments> {
        val scenarios = TextTestScenario.entries.toTypedArray()
        val models = listOf(
            OpenAIModels.Chat.GPT4o,
            AnthropicModels.Sonnet_3_7,
            GoogleModels.Gemini2_5Pro
        )
        return scenarios.flatMap { scenario ->
            models.map { model ->
                Arguments.of(scenario, model)
            }
        }.stream()
    }

    @JvmStatic
    fun audioScenarioModelCombinations(): Stream<Arguments> {
        val scenarios = AudioTestScenario.entries.toTypedArray()
        val models = listOf(
            OpenAIModels.Audio.GPT4oAudio,
            GoogleModels.Gemini2_5Pro
        )
        return scenarios.flatMap { scenario ->
            models.map { model ->
                Arguments.of(scenario, model)
            }
        }.stream()
    }
}
