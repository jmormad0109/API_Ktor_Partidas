package domain.models.partidas

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePartida(
    var nombrePartida: String? = null,
    var resultado: String? = null,
    var estadistica: String? = null,
    var fecha: String? = null,
    var token: String? = null
)
