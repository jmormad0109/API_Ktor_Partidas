package domain.repository

import domain.models.partidas.UpdatePartida
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario

interface UsuarioInteface {

    suspend fun getUsuarioByDni(dni: String): Usuario?
    suspend fun login(dni: String, password: String): Usuario?
    suspend fun register(usuario: UpdateUsuario): Usuario?
    suspend fun updateUsuario(updateUsuario: UpdateUsuario, dni: String): Boolean
}