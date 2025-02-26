package domain.models.usuarios

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val name: String,
    val dni: String,
    val email: String,
    val password: String,
    var token: String? = null
)