package domain.repository

import domain.models.partidas.Partida
import domain.models.partidas.Resultado
import domain.models.partidas.UpdatePartida

interface PartidaInterface {

    suspend fun getAllPartidas(userId: Int): List<Partida>
    suspend fun getPartidasByResultado(resultado: Resultado, userId: Int): List<Partida>
    suspend fun getPartidasByNombre(nombrePartida: String, userId: Int): Partida?
    suspend fun postPartida(partida: Partida) : Boolean
    suspend fun updatePartida(partida: UpdatePartida, nombreNuevo: String): Boolean
    suspend fun deletePartida(nombrePartida: String): Boolean
}