package domain.usecase.usuarios

import data.persistence.repository.PersistenceUsuarioRepository
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario

class RegisterUseCase(val repository: PersistenceUsuarioRepository) {
    suspend operator fun invoke(usuario: UpdateUsuario): Usuario?{

        usuario.dni = usuario.dni!!
        usuario.password = usuario.password!!
        usuario.name = usuario.name!!
        usuario.email = usuario.email!!
        usuario.token = usuario.token?: ""

        if (repository.login(usuario.dni!!, usuario.password!!)){
            return null
        }else{
            return repository.register(usuario)
        }
    }
}