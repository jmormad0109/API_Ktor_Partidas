package domain.models.usuarios

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUsuario(
    var name: String? = null,
    var dni: String? = null,
    var email: String? = null,
    var password: String? = null,
    var token: String? = null
)