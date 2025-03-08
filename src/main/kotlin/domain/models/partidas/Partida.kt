package domain.models.partidas

import kotlinx.serialization.Serializable

enum class Resultado {
    GANADO, PERDIDO, EMPATE
}

@Serializable
data class Partida(
    val nombrePartida: String,
    val resultado: Resultado,
    val estadistica: String,
    val fecha: String,
    val dniUsuario: String,
)
