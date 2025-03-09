package domain.repository

import domain.models.partidas.UpdatePartida
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario

interface UsuarioInteface {

    suspend fun getAllUsuarios(): List<Usuario>
    suspend fun getUsuarioByDni(dni: String): Usuario?
    suspend fun postUsuario(usuario: Usuario): Usuario?
    suspend fun deleteUsuario(dni: String): Boolean
    suspend fun login(dni: String, password: String): Usuario?
    suspend fun register(usuario: UpdateUsuario): Usuario?
    suspend fun updateUsuario(updateUsuario: UpdateUsuario, dni: String): Usuario?
}