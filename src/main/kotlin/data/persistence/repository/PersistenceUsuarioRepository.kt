package data.persistence.repository

import data.persistence.suspendTransaction
import data.persistence.usuarios.UsuarioDao
import data.persistence.usuarios.UsuarioTable
import data.security.PasswordHash
import domain.mapping.UsuarioDaoToUsuario
import domain.mapping.toUsuario
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario
import domain.repository.UsuarioInteface
import org.jetbrains.exposed.sql.update

class PersistenceUsuarioRepository: UsuarioInteface {
    override suspend fun getUsuarioByDni(dni: String): Usuario? {
        return suspendTransaction {
            UsuarioDao.find{
                UsuarioTable.dni eq dni
            }.limit(1).map{it.toUsuario()}.firstOrNull()
        }
    }

    override suspend fun login(dni: String, password: String): Usuario? {
        val usuario = getUsuarioByDni(dni)?: return null

        return try{
            val posibleHash = PasswordHash.hash(password)
            if (posibleHash == usuario.password){
                usuario
            }else {
                null
            }
        }catch (e: Exception){
            println("Error en la autenticación: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun register(usuario: UpdateUsuario): Usuario? {
        return try{
            suspendTransaction {
                UsuarioDao.new {
                    this.name = usuario.name!!
                    this.dni = usuario.dni!!
                    this.password = usuario.password!!
                    this.email = usuario.email!!
                    this.token = usuario.token!!
                }
            }.toUsuario()
        }catch (e: Exception){
            println("Error en el registro del usuario: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun updateUsuario(updateUsuario: UpdateUsuario, dni: String): Boolean {
        var num = 0
        try {
            suspendTransaction {
                num = UsuarioTable.update({UsuarioTable.dni eq dni}) {
                    user ->
                        updateUsuario.name?.let{ user[name] = it }
                        updateUsuario.email?.let { user[email] = it }
                        updateUsuario.password?.let { user[password] = it }
                        updateUsuario.token?.let { user[token] = it }
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
            false
        }
        return num == 1
    }
}