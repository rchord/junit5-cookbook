package callbacks

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class BeforeEachBasicExtension : BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        println("Create Resources, inject into DB, make calls to other services etc")
    }
}