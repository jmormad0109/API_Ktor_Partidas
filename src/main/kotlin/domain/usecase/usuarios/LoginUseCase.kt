package domain.usecase.usuarios

import domain.mapping.toUpdateUsuario
import domain.mapping.toUsuario
import domain.models.usuarios.Usuario
import domain.repository.UsuarioInteface
import domain.security.JwtConfig
import ktor.ApplicationContext

class LoginUseCase(val repository: UsuarioInteface) {

    suspend operator fun invoke(dni: String?, password: String?): Usuario?{
        if (dni.isNullOrBlank() || password.isNullOrBlank()) return null

        return try{
            val us = repository.login(dni, password) ?: null

            us!!.token = JwtConfig.generateToken(us.dni)
            val updateUsuario = us.toUpdateUsuario()
            if (!updateUsuario.urlImg.isNullOrBlank()){
                val local = ApplicationContext.context.environment.config.property("ktor.urlPath.baseUrl").getString()
                val relativePath = ApplicationContext.context.environment.config.property("ktor.urlPath.images").getString()
                updateUsuario.urlImg = "$local/$relativePath/${updateUsuario.dni}/${updateUsuario.urlImg}"
            }
            val res = repository.updateUsuario(updateUsuario, dni)
            return if (res != null){
                updateUsuario.toUsuario()
            }else{
                null
            }
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}