package injection

abstract class UserDataConcept(open val name: String, open val age: Int)

data class UserData(override val name: String, override val age: Int, val email: String) : UserDataConcept(name, age)

data class AuthorData(override val name: String, override val age: Int, val genre: String) : UserDataConcept(name, age)
data class BookData(val title: String, val author: AuthorData)
