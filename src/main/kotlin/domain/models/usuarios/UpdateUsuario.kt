package domain.models.usuarios

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUsuario(
    val name: String? = null,
    val dni: String? = null,
    val email: String? = null,
    val password: String? = null,
    val token: String? = null
)