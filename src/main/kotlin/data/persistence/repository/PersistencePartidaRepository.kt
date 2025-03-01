package data.persistence.repository

import data.persistence.partidas.PartidaDao
import data.persistence.partidas.PartidaTable
import data.persistence.suspendTransaction
import domain.mapping.PartidaDaoToPartida
import domain.models.partidas.Partida
import domain.models.partidas.Resultado
import domain.models.partidas.UpdatePartida
import domain.repository.PartidaInterface
import io.ktor.util.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

class PersistencePartidaRepository: PartidaInterface {

    // Obtenemos todas las partidas
    override suspend fun getAllPartidas(): List<Partida> {
         return suspendTransaction {
             PartidaDao.all().map(::PartidaDaoToPartida)
         }
    }


    //Filtro por resultado de la partida
    override suspend fun getPartidasByResultado(resultado: Resultado): List<Partida> {
        return suspendTransaction {
            PartidaDao.find{
                PartidaTable.resultado eq resultado.toString()
            }.map(::PartidaDaoToPartida)
        }
    }

    //Filtro por el nombre de la partida
    override suspend fun getPartidasByNombre(nombrePartida: String): Partida? {
        return suspendTransaction {
            PartidaDao.find{
                PartidaTable.nombrePartida eq nombrePartida
            }.limit(1).map(::PartidaDaoToPartida).firstOrNull()
        }
    }

    // Insertamos una nueva partida, comprobando que el nombre no exista en la BBDD
    override suspend fun postPartida(partida: Partida): Boolean {
        val part = getPartidasByNombre(partida.nombrePartida) //TODO Tiene que ser un atributo unico!!! (Por ahora el atributo unico es el nombre)

        return if (part == null){
            suspendTransaction {
                PartidaDao.new {
                    this.nombrePartida = partida.nombrePartida
                    this.resultado = partida.resultado.toString()
                    this.estadistica = partida.estadistica
                    this.fecha = partida.fecha
                }
            }
            true
        }else
            false
    }

    //Actualizamos los datos de la partida, usando como buscador el nombre de la partida
    override suspend fun updatePartida(partida: UpdatePartida, nombreNuevo: String): Boolean {
        var filas = 0
        try {
            suspendTransaction {
                filas = PartidaTable.update({ PartidaTable.nombrePartida eq nombreNuevo}) { part ->
                    partida.nombrePartida?.let { part[nombrePartida] = it }
                    partida.resultado?.let { part[resultado] = it.toString() }
                    partida.estadistica?.let { part[estadistica] = it }
                    partida.fecha?.let { part[fecha] = it }

                }
            }
        }catch (e: Exception){
            e.printStackTrace()

            false //No es necesario, porque si está dentro del Catch, es porque hay un error
        }
        return filas == 1
    }

    // Borramos la partida usando el nombre de la partida
    override suspend fun deletePartida(nombrePartida: String): Boolean {
        var fila = 0

        try{
            suspendTransaction {
                fila = PartidaTable.deleteWhere { PartidaTable.nombrePartida eq nombrePartida }
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return fila == 1
    }

}