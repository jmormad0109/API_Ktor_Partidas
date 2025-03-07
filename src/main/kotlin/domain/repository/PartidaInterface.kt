package domain.repository

import domain.models.partidas.Partida
import domain.models.partidas.PartidaSinDni
import domain.models.partidas.Resultado
import domain.models.partidas.UpdatePartida

interface PartidaInterface {

    suspend fun getAllPartidas(dniUsuario: String): List<PartidaSinDni>
    suspend fun getPartidasByResultado(resultado: Resultado, dniUsuario: String): List<Partida>
    suspend fun getPartidasByNombre(nombrePartida: String, dniUsuario: String): Partida?
    suspend fun postPartida(partida: PartidaSinDni, dniUsuario: String) : Partida?
    suspend fun updatePartida(partida: UpdatePartida, nombreNuevo: String, dniUsuario: String): Partida?
    suspend fun deletePartida(nombrePartida: String, dniUsuario: String): Partida?
}