package domain.repository

import domain.models.partidas.UpdatePartida
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario

interface UsuarioInteface {

    suspend fun getUsuarioByDni(dni: String): Usuario?
    suspend fun login(dni: String, password: String): Boolean
    suspend fun register(usuario: UpdateUsuario): Usuario?
}