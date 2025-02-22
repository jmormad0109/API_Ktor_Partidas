package data.persistence.usuarios

import data.persistence.partidas.PartidaTable.nullable
import org.jetbrains.exposed.dao.id.IntIdTable

object UsuarioTable: IntIdTable("Usuario") {
    val dni = varchar("dni", 20).uniqueIndex()
    val name = varchar("name", 80)
    val password = varchar("password", 255)
    val token = varchar("token", 255).nullable()
}