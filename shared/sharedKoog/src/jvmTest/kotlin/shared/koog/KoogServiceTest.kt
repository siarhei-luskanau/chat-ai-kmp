package shared.koog

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.test.runTest
import org.testcontainers.containers.DockerModelRunnerContainer
import org.testcontainers.containers.GenericContainer
import shared.common.GenericResult
import shared.koog.Containers.EXPOSED_PORT

class KoogServiceTest {

    private val container: GenericContainer<*>? by lazy {
        Containers.getContainer()
    }

    private var baseContainerUrl: String? = null

    @BeforeTest
    fun start() {
        container?.start()
        baseContainerUrl = (container as? DockerModelRunnerContainer)?.openAIEndpoint
            ?: run { container?.getMappedPort(EXPOSED_PORT)?.let { port -> "http://localhost:$port/" } }
                ?.also { Containers.waitForOllamaServer(it) }
    }

    @AfterTest
    fun teardown() {
        container?.stop()
    }

    @Test
    fun textTest() = runTest(timeout = 10.minutes) {
        val koogService = KoogService(baseUrlProvider = baseContainerUrl?.let { { it } })
        val result = koogService.askLlm(promptText = "What is the capital of France?")
        println(result)
        assertIs<GenericResult.Success<String>>(value = result)
        assertNotNull(actual = result.result)
        assertContains(
            result.result,
            other = "Paris",
            ignoreCase = true,
            message = "Result should contain Paris"
        )
    }

    @Test
    fun attachmentTest() = runTest(timeout = 30.minutes) {
        val koogService = KoogService(baseUrlProvider = baseContainerUrl?.let { { it } })
        val result = koogService.askLlm(
            promptText = "What is in the attached image?",
            attachmentData = this::class.java.getResource("/image.jpg")?.readBytes(),
            attachmentFormat = "jpg"
        )
        println(result)
        assertIs<GenericResult.Success<String>>(value = result)
        assertNotNull(actual = result.result)
        assertContains(
            result.result,
            other = "circle",
            ignoreCase = true,
            message = "Result should contain: circle"
        )
    }
}
