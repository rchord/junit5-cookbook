# junit5-cookbook

# Extensions
Extensions allow for extending Junit's lifecycle hooks within a class. 
This functionality allows us a higher degree of code reuse, as well as a modularity of tests. 
In Junit 4, you may have had to create helper methods to handle setting up or tearing down tests. 
Or perhaps copied and pasted common code and then adjusted it to your needs. 

With extensions, we are able to create a class that handles a before, an after and/or a parameter resolver for a specific scenario or object.
These extensions can be stacked on a test to provide a declarative way of declaring test setup and tear down.

Extensions allow you to focus on your tests and not the setup
## Befores
Hooks that run before the tests
### BeforeAll
Interface: `BeforeAllCallback`
> ⚠️ Will run when the `@ExtendWith(<CustomBeforeAllExtensionClass>)` is placed at the class level, NOT the test method.

#### Basic usage of BeforeAll
```kotlin
class BeforeAllBasicSetupExampleExtension : BeforeAllCallback, BeforeEachCallback {
    override fun beforeAll(context: ExtensionContext) {
        println("Setting up before ALL")
    }

    override fun beforeEach(context: ExtensionContext) {
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
```
#### Storing things created at BeforeAll
If there is something you only want to create once in the test suite and share it between extensions you will 
need to use the [contextStore](https://junit.org/junit5/docs/current/user-guide/#extensions-keeping-state) to keep and 
share state.



Here are some examples of sharing state between extensions:
```kotlin
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
```

Tests using the extensions:
```kotlin
/*
When running the test class, the following will be outputted:

Created global user: TestData(user=ol2qkU4WBRur, email=bXoFFluUh3BodS)
Retrieved testUser: TestData(user=ol2qkU4WBRur, email=bXoFFluUh3BodS)
test1
Retrieved testUser: TestData(user=ol2qkU4WBRur, email=bXoFFluUh3BodS)
test2

 */
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
```
### BeforeEach
Interface: `BeforeEachCallback`
BeforeEach Callbacks run before each test method.

## ParameterResolver
### Annotations for Customization
## Afters
### AfterAll
### AfterEach
## BeforeAll
## AfterAll
