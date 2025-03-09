package domain.usecase.usuarios

import data.persistence.repository.PersistenceUsuarioRepository
import domain.models.usuarios.Usuario
import domain.repository.UsuarioInteface
import ktor.ApplicationContext

class GetAllUsuariosUseCase(val repository: UsuarioInteface) {


    suspend operator fun invoke(): List<Usuario>{
        val listaUsuarios = repository.getAllUsuarios()
        return listaUsuarios.map {
            user ->
                if (!user.urlImg.isNullOrBlank()){
                    val local = ApplicationContext.context.environment.config.property("ktor.urlPath.baseUrl").getString()
                    val relativePath = ApplicationContext.context.environment.config.property("ktor.urlPath.images").getString()
                    user.urlImg = "$local/$relativePath/${user.dni}/${user.urlImg}"
                }
            user
        }
    }
}