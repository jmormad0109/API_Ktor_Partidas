package domain.models.partidas

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePartida(
    var nombrePartida: String? = null,
    var resultado: Resultado? = null,
    var estadistica: String? = null,
    var fecha: String? = null
)
