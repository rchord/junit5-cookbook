package callbacks

import io.github.serpro69.kfaker.Faker
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext

private val faker = Faker()

data class TestData(
    val user: String = faker.random.randomString(length = 12),
    val email: String = faker.random.randomString(length = 14)
)

class BeforeAllStoreForTestSuiteExtension : BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext) {
        val testUser = TestData()
        println("Created global user: $testUser")
        context.getStore(NAMESPACE).put(TEST_USER, testUser)
    }

    companion object {
        // Create the Namespace where the state will live in the context
        val NAMESPACE: ExtensionContext.Namespace =
            ExtensionContext.Namespace.create(BeforeAllStoreForTestSuiteExtension::class)
        const val TEST_USER = "TEST_USER"
    }
}

class BeforeEachUseStoreExtension : BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext) {
        val testUser = context.getStore(BeforeAllStoreForTestSuiteExtension.NAMESPACE)
            .get(BeforeAllStoreForTestSuiteExtension.TEST_USER, TestData::class.java)
        println("Retrieved testUser: $testUser")
    }
}

@ExtendWith(BeforeAllStoreForTestSuiteExtension::class, BeforeEachUseStoreExtension::class)
class BeforeAllStoreExample {
    @Test
    fun `Test 1`() {
        println("test1")
    }

    @Test
    fun `Test 2`() {
        println("test2")
    }
}
