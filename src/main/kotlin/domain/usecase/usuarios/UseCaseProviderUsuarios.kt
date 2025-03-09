package domain.usecase.usuarios

import data.persistence.repository.PersistenceUsuarioRepository
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario

object UseCaseProviderUsuarios {

    private val repository = PersistenceUsuarioRepository()

    private val getUsuarioByNameUseCase = GetUsuarioByDniUseCase(repository)
    private val getAllUsuariosUseCase = GetAllUsuariosUseCase(repository)
    private val insertUsuarioUseCase = InsertUsuarioUseCase(repository)
    private val updateUsuarioUseCase = UpdateUsuarioUseCase(repository)
    private val deleteUsuariosUseCase = DeleteUsuarioUseCase(repository)
    private val loginUseCase = LoginUseCase(repository)
    private val registerUseCase = RegisterUseCase(repository)

    suspend fun getAllUsuarios() = getAllUsuariosUseCase()


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

    suspend fun insertUsuario(usuario: Usuario?): Usuario? {
        if (usuario == null){
            return null
        }
        insertUsuarioUseCase.usuario = usuario
        val new = insertUsuarioUseCase()
        if (new==null){
            return null
        }
        return new
    }

    suspend fun updateUsuario(updateUsuario: UpdateUsuario?, dni: String?): Usuario?{
        if (updateUsuario == null){
            return null
        }

        updateUsuarioUseCase.updateUsuario = updateUsuario
        updateUsuarioUseCase.dni = dni
        return updateUsuarioUseCase()
    }

    suspend fun deleteUsuario(dni: String?): Boolean {
        deleteUsuariosUseCase.dni = dni
        return deleteUsuariosUseCase()
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