package domain.models.partidas

import kotlinx.serialization.Serializable


@Serializable
data class PartidaSinDni(
    val nombrePartida: String,
    val resultado: Resultado,
    val estadistica: String,
    val fecha: String
)