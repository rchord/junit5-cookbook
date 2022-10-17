package abstractextensions

import injection.UserData
import kotlin.random.Random
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

abstract class AbstractExtension : BeforeEachCallback, ParameterResolver {

    val KEY = "AUTH_USER"
    protected val authUser = createData()

    override fun beforeEach(context: ExtensionContext) {
        context.getStore(ExtensionContext.Namespace.GLOBAL)
            .put(KEY, authUser)
    }

    private fun createData(): UserData {
        return UserData(name = "NAME", age = 45, email = "EMAIL")
    }
}

class ImplementedExtension : AbstractExtension() {
    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        return parameterContext.parameter.type == UserData::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): UserData {
        return extensionContext.getStore(ExtensionContext.Namespace.GLOBAL).get(KEY, UserData::class.java)
    }
}

class BeforeAllUser : BeforeAllCallback, ParameterResolver {
    val KEY = "AUTH_USER"
    protected val authUser = createData()

    private fun createData(): UserData {
        return UserData(name = "BEFORE ALL", age = Random.nextInt(), email = "EMAIL_EVERYTHING")
    }

    override fun beforeAll(context: ExtensionContext) {
        context.getStore(ExtensionContext.Namespace.GLOBAL)
            .put(KEY, authUser)
    }

    override fun supportsParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Boolean {
        return parameterContext.parameter.type == UserData::class.java
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): UserData {
        return extensionContext.getStore(ExtensionContext.Namespace.GLOBAL).get(KEY, UserData::class.java)
    }
}

@ExtendWith(BeforeAllUser::class)
class TestAbstractExtension(private val user: UserData) {

    @Test
    fun `test that user is the same in all tests1`() {
        println("A: $user")
    }
    @Test
    fun `test that user is the same in all tests2`() {
        println("B: $user")
    }
}
