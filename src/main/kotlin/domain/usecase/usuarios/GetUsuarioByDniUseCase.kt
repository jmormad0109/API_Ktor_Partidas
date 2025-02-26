package domain.usecase.usuarios

import domain.models.usuarios.Usuario
import domain.repository.UsuarioInteface

class GetUsuarioByDniUseCase(val repository: UsuarioInteface) {

    var dni: String? = null

    suspend operator fun invoke(): Usuario? {
        if (dni?.isNullOrBlank() == true){
            return null
        }else{
            return repository.getUsuarioByDni(dni!!)
        }
    }
}