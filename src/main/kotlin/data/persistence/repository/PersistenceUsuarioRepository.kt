package data.persistence.repository

import data.persistence.suspendTransaction
import data.persistence.usuarios.UsuarioDao
import data.persistence.usuarios.UsuarioTable
import data.security.PasswordHash
import domain.mapping.UsuarioDaoToUsuario
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario
import domain.repository.UsuarioInteface

class PersistenceUsuarioRepository: UsuarioInteface {
    override suspend fun getUsuarioByDni(dni: String): Usuario? {
        return suspendTransaction {
            UsuarioDao.find{
                UsuarioTable.dni eq dni
            }.limit(1).map(::UsuarioDaoToUsuario).firstOrNull()
        }
    }

    override suspend fun login(dni: String, password: String): Boolean {
        val usuario = getUsuarioByDni(dni)?: return false

        return try{
            val posibleHash = PasswordHash.hash(password)
            posibleHash == usuario.password
        }catch (e: Exception){
            println("Error en la autenticación: ${e.localizedMessage}")
            false
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
            }.let {
                UsuarioDaoToUsuario(it)
            }
        }catch (e: Exception){
            println("Error en el registro del usuario: ${e.localizedMessage}")
            null
        }
    }
}