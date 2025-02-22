package domain.usecase.usuarios

import data.persistence.repository.PersistenceUsuarioRepository
import domain.models.usuarios.Usuario

class GetUsuarioByDniUseCase(val repository: PersistenceUsuarioRepository) {

    var dni: String? = null

    suspend operator fun invoke(): Usuario? {
        if (dni?.isNullOrBlank() == true){
            return null
        }else{
            return repository.getUsuarioByDni(dni!!)
        }
    }
}