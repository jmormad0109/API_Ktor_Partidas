package data.persistence.usuarios

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UsuarioDao(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<UsuarioDao>(UsuarioTable)
    var name by UsuarioTable.name
    var dni by UsuarioTable.dni
    var email by UsuarioTable.email
    var password by UsuarioTable.password
    var token by UsuarioTable.token
}