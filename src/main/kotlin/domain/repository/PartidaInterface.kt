package domain.repository

import domain.models.partidas.Partida
import domain.models.partidas.Resultado
import domain.models.partidas.UpdatePartida

interface PartidaInterface {

    suspend fun getAllPartidas(): List<Partida>
    suspend fun getPartidasByResultado(resultado: Resultado): List<Partida>
    suspend fun getPartidasByNombre(nombrePartida: String): Partida
    suspend fun postPartida(partida: Partida) : Boolean
    suspend fun updatePartida(partida: UpdatePartida, nombreNuevo: String): Boolean
    suspend fun deletePartida(nombrePartida: String): Boolean
}