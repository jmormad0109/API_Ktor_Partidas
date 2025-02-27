package domain.usecase.usuarios

import data.persistence.repository.PersistenceUsuarioRepository
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario

object UseCaseProviderUsuarios {

    private val repository = PersistenceUsuarioRepository()

    private val getUsuarioByNameUseCase = GetUsuarioByDniUseCase(repository)
    private val loginUseCase = LoginUseCase(repository)
    private val registerUseCase = RegisterUseCase(repository)

    suspend fun getUsuarioByDni(dni: String): Usuario?{
        if (dni.isNullOrBlank()){
            return null
        }
        getUsuarioByNameUseCase.dni = dni
        val usuario = getUsuarioByNameUseCase()

        if (usuario == null){
            return null
        }else{
            return usuario
        }
    }

    suspend fun login(dni: String?, password: String?): Usuario? = loginUseCase(dni, password)

    suspend fun register(usuario: UpdateUsuario): Usuario? {
        if (usuario.dni.isNullOrBlank() || usuario.name.isNullOrBlank() || usuario.password.isNullOrBlank()){
            return null
        }else{
            return registerUseCase(usuario)
        }
    }
}