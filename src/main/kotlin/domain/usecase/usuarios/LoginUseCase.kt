package domain.usecase.usuarios

import domain.mapping.toUpdateUsuario
import domain.mapping.toUsuario
import domain.models.usuarios.Usuario
import domain.repository.UsuarioInteface
import domain.security.JwtConfig

class LoginUseCase(val repository: UsuarioInteface) {

    suspend operator fun invoke(dni: String?, password: String?): Usuario?{
        if (dni.isNullOrBlank() || password.isNullOrBlank()) return null

        return try {
            val usuario = repository.login(dni, password) ?: null

            usuario!!.token = JwtConfig.generateToken(usuario.dni)
            val updateUsuario = usuario.toUpdateUsuario()
            val res = repository.updateUsuario(updateUsuario, dni)

            return if (res) {
                updateUsuario.toUsuario()
            }else{
                null
            }
        }catch (e: Exception){
            println("Error al loguear.")
            null
        }
    }
}