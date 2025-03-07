package data.persistence.repository

import data.persistence.partidas.PartidaDao
import data.persistence.partidas.PartidaTable
import data.persistence.suspendTransaction
import domain.mapping.PartidaDaoToPartida
import domain.mapping.PartidaDaoToPartidaSinDni
import domain.mapping.toPartidaSinDni
import domain.models.partidas.Partida
import domain.models.partidas.PartidaSinDni
import domain.models.partidas.Resultado
import domain.models.partidas.UpdatePartida
import domain.repository.PartidaInterface
import io.ktor.util.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

class PersistencePartidaRepository: PartidaInterface {

    // Obtenemos todas las partidas
    override suspend fun getAllPartidas(dniUsuario: String): List<PartidaSinDni> {
        return  suspendTransaction {
            PartidaDao.find{
                PartidaTable.dni_usuario eq dniUsuario
            }.map(::PartidaDaoToPartidaSinDni)
        }
    }


    //Filtro por resultado de la partida
    override suspend fun getPartidasByResultado(resultado: Resultado, dniUsuario: String): List<Partida> {
        return suspendTransaction {
            PartidaDao.find{
                (PartidaTable.resultado eq resultado.toString()) and (PartidaTable.dni_usuario eq dniUsuario)
            }.map(::PartidaDaoToPartida)
        }
    }

    //Filtro por el nombre de la partida
    override suspend fun getPartidasByNombre(nombrePartida: String, dniUsuario: String): Partida? {
        return suspendTransaction {
            PartidaDao.find{
                (PartidaTable.nombrePartida eq nombrePartida) and (PartidaTable.dni_usuario eq dniUsuario)
            }.limit(1).map(::PartidaDaoToPartida).firstOrNull()
        }
    }

    // Insertamos una nueva partida, comprobando que el nombre no exista en la BBDD
    override suspend fun postPartida(partida: PartidaSinDni, dniUsuario: String): Partida? {
        val part = getPartidasByNombre(partida.nombrePartida, dniUsuario) //TODO Tiene que ser un atributo unico!!! (Por ahora el atributo unico es el nombre)

        return if (part == null){
             suspendTransaction {
                 val partidaCreada = PartidaDao.new {
                    this.nombrePartida = partida.nombrePartida
                    this.resultado = partida.resultado.toString()
                    this.estadistica = partida.estadistica
                    this.fecha = partida.fecha
                    this.dniUsuario = dniUsuario
                }
                PartidaDaoToPartida(partidaCreada)
             }
        }else
            null
    }

    //Actualizamos los datos de la partida, usando como buscador el nombre de la partida
    override suspend fun updatePartida(partida: UpdatePartida, nombreNuevo: String, dniUsuario: String): Partida? {
        var filas = 0
        try {
            suspendTransaction {
                filas = PartidaTable.update({ (PartidaTable.nombrePartida eq nombreNuevo) and (PartidaTable.dni_usuario eq dniUsuario)})
                    { part ->
                        partida.nombrePartida?.let { part[nombrePartida] = it }
                        partida.resultado?.let { part[resultado] = it.toString() }
                        partida.estadistica?.let { part[estadistica] = it }
                        partida.fecha?.let { part[fecha] = it }
                        partida.dniUsuario = dniUsuario

                    }
            }
        }catch (e: Exception){
        e.printStackTrace()
        }
        return if(filas == 1){
            val nombreBusqueda = partida.nombrePartida ?: nombreNuevo
            getPartidasByNombre(nombreBusqueda, dniUsuario)
        }else{
            null
        }
    }

    // Borramos la partida usando el nombre de la partida
    override suspend fun deletePartida(nombrePartida: String, dniUsuario: String): Partida? {

        val partidaEliminar = getPartidasByNombre(nombrePartida, dniUsuario) ?: return null

        var filas = 0
        try {
            suspendTransaction {
                filas =
                    PartidaTable.deleteWhere { (PartidaTable.nombrePartida eq nombrePartida) and (PartidaTable.dni_usuario eq dniUsuario) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return if (filas == 1) {
            partidaEliminar
        } else {
            null
        }
    }

}