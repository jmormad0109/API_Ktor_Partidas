package data.persistence.partidas

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class PartidaDao (id: EntityID<Int>): IntEntity(id){

    companion object : IntEntityClass<PartidaDao>(PartidaTable)
    var nombrePartida by PartidaTable.nombrePartida
    var resultado by PartidaTable.resultado
    var estadistica by PartidaTable.estadistica
    var fecha by PartidaTable.fecha
    var token by PartidaTable.token



}