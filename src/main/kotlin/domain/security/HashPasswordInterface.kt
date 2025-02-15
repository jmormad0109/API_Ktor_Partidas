package domain.security

interface HashPasswordInterface {
    fun hash(pass: String): String
    fun verify(pass: String, passHash: String): Boolean
}