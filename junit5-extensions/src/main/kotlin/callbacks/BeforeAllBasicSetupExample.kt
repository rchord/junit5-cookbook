package callbacks

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext

class BeforeAllBasicSetupExampleExtension : BeforeAllCallback, BeforeEachCallback {
    override fun beforeAll(context: ExtensionContext) {
        println("Setting up before ALL")
    }

    override fun beforeEach(context: ExtensionContext?) {
        println("Setting up before each")
    }
}

/*
 This will output:
    Setting up before ALL
    Setting up before each
    Running Test
 */
@ExtendWith(BeforeAllBasicSetupExampleExtension::class)
class BeforeAllRunBasicSetupExample {
    @Test
    fun `test BeforeAll will run with Extension`() {
        println("Running Test")
    }
}

/*
 This will output:
    Setting up before each
    Running Test
 */
class BeforeAllNotRunBasicSetupExample {
    @Test
    @ExtendWith(BeforeAllBasicSetupExampleExtension::class)
    fun `test BeforeAll wont run with Extension`() {
        println("Running Test")
    }
}
