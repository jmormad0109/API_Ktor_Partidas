package data.persistence.repository

import data.persistence.suspendTransaction
import data.persistence.usuarios.UsuarioDao
import data.persistence.usuarios.UsuarioTable
import data.security.PasswordHash
import domain.mapping.toUsuario
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario
import domain.repository.UsuarioInteface
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update

class PersistenceUsuarioRepository: UsuarioInteface {


    override suspend fun getAllUsuarios(): List<Usuario> {
        return suspendTransaction {
            UsuarioDao.all().map { it.toUsuario() }
        }
    }

    override suspend fun getUsuarioByDni(dni: String): Usuario? {
        return suspendTransaction {
            UsuarioDao.find{
                UsuarioTable.dni eq dni
            }.limit(1).map{it.toUsuario()}.firstOrNull()
        }
    }

    override suspend fun postUsuario(usuario: Usuario): Usuario? {
        return suspendTransaction {
            UsuarioDao.new {
                this.name = usuario.name
                this.dni = usuario.dni
                this.email = usuario.email
                this.password = usuario.password
                this.urlImg = usuario.urlImg
                this.token = usuario.token
            }
        }.toUsuario()
    }


    override suspend fun deleteUsuario(dni: String): Boolean = suspendTransaction {
        val num = UsuarioTable.deleteWhere { UsuarioTable.dni eq dni }
        num == 1
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
                    this.password = PasswordHash.hash(usuario.password!!)
                    this.email = usuario.email!!
                    this.urlImg = usuario.urlImg!!
                    this.token = usuario.token!!
                }
            }.toUsuario()
        }catch (e: Exception){
            println("Error en el registro del usuario: ${e.localizedMessage}")
            null
        }
    }

    override suspend fun updateUsuario(updateUsuario: UpdateUsuario, dni: String): Usuario? {
        var num = 0
        try {
            suspendTransaction {
                num = UsuarioTable.update({UsuarioTable.dni eq dni}) {
                    user ->
                        updateUsuario.name?.let{ user[name] = it }
                        updateUsuario.email?.let { user[email] = it }
                        updateUsuario.password?.let { user[password] = it }
                        updateUsuario.urlImg?.let { user[urlImg] = it }
                        updateUsuario.token?.let { user[token] = it }
                }
            }
            return  getUsuarioByDni(dni)
        }catch (e: Exception){
            e.printStackTrace()
            return null
        }
    }
}