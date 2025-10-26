package shared.koog

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.minutes
import kotlinx.coroutines.test.runTest
import org.testcontainers.containers.GenericContainer
import shared.common.GenericResult
import shared.llms.container.Containers
import shared.llms.container.Containers.EXPOSED_PORT

class KoogServiceTest {

    private val container: GenericContainer<*> by lazy {
        Containers.getContainer()
    }

    @BeforeTest
    fun start() {
        container.start()
        val port = container.getMappedPort(EXPOSED_PORT)
        val baseContainerUrl = "http://localhost:$port/"
        Containers.waitForOllamaServer(baseContainerUrl)
    }

    @AfterTest
    fun teardown() {
        container.stop()
    }

    @Test
    fun test() = runTest(timeout = 30.minutes) {
        val koogService = KoogService({
            val port = container.getMappedPort(EXPOSED_PORT)
            GenericResult.Success("http://localhost:$port/")
        })
        val result = koogService.askLlm(agentInput = "What is the capital of France?")
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
}
