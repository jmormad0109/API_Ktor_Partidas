package domain.usecase.usuarios

import domain.infraestructure.Utils
import domain.repository.UsuarioInteface

class DeleteUsuarioUseCase(val repository: UsuarioInteface) {

    var dni: String? = null

    suspend operator fun invoke(): Boolean {

        return if (dni == null){
            false
        }else{
            val usuario = repository.getUsuarioByDni(dni!!)
            usuario?.let { user ->
                user.urlImg?.let { img ->
                    Utils.deleteImage(usuario.dni, img)
                    Utils.deleteDirectory(usuario.dni)
                }
                return repository.deleteUsuario(user.dni)
            }
            false
        }
    }
}