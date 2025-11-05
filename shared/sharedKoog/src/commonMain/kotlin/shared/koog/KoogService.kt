package shared.koog

import ai.koog.prompt.dsl.prompt
import ai.koog.prompt.executor.llms.SingleLLMPromptExecutor
import ai.koog.prompt.message.AttachmentContent
import ai.koog.prompt.message.ContentPart
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import shared.common.GenericResult

@OptIn(ExperimentalTime::class)
class KoogService(private val baseUrlProvider: (() -> String)? = null) {

    @OptIn(ExperimentalUuidApi::class)
    suspend fun askLlm(
        promptText: String,
        attachmentData: ByteArray? = null,
        attachmentFormat: String? = null
    ): GenericResult<String> = try {
        println("KoogService: creating LLMClient ...")
        val (client, model) = LLMClientFactory.createLLMClient(baseUrlProvider = baseUrlProvider)
        println("KoogService: LLMClient is created: $client")
        println("KoogService: LLModel is used: $model")
        val promptExecutor = SingleLLMPromptExecutor(client)
        val prompt = prompt(id = Uuid.random().toString()) {
            system(content = "You are a helpful assistant.")
            user {
                text(text = promptText)
                if (attachmentData != null && attachmentFormat != null) {
                    image(
                        image = ContentPart.Image(
                            content = AttachmentContent.Binary.Bytes(data = attachmentData),
                            format = attachmentFormat
                        )
                    )
                }
            }
        }
        println("KoogService: execute agent prompt: $prompt")
        val response = promptExecutor.execute(prompt = prompt, model = model).single()
        val responseTime = response.metaInfo.timestamp - prompt.messages.first().metaInfo.timestamp
        println("KoogService: response time: $responseTime")
        println("KoogService: agent response: $response")
        GenericResult.Success(result = "${model.id} $responseTime\n${response.content}")
    } catch (error: Throwable) {
        GenericResult.Failure(error = error)
    }
}
