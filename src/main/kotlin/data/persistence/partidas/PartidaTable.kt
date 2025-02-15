package data.persistence.partidas

import org.jetbrains.exposed.dao.id.IntIdTable

object PartidaTable: IntIdTable("Partida") {
    val nombrePartida = varchar("nombre", 50).uniqueIndex()
    val resultado = varchar("resultado", 20)
    val estadistica = varchar("estadistica", 5)
    val fecha = varchar("fecha", 12)
    val token = varchar("token", 255).nullable()
}