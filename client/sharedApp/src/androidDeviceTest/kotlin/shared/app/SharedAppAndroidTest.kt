package shared.app

import androidx.test.platform.app.InstrumentationRegistry
import kotlin.test.Test
import kotlin.test.assertEquals

class SharedAppAndroidTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals(
            expected = "shared.app.test",
            actual = appContext.packageName
        )
    }
}
