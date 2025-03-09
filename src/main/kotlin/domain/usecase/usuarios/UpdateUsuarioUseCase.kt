package domain.usecase.usuarios

import domain.infraestructure.Utils
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario
import domain.repository.UsuarioInteface

class UpdateUsuarioUseCase(val repository: UsuarioInteface) {
    var updateUsuario: UpdateUsuario? = null
    var dni: String? = null

    suspend operator fun invoke(): Usuario? {
        return if (updateUsuario == null || dni == null){
            null
        }else{
            try {
                updateUsuario?.urlImg?.let { newImg ->
                    val usuario = repository.getUsuarioByDni(dni!!)
                    usuario?.let { user ->
                        user.urlImg?.let { oldImg ->
                            Utils.deleteImage(user.dni, oldImg)
                        }
                    }
                    val newImgUrl = Utils.createBase64ToImg(newImg, dni!!)
                    updateUsuario!!.urlImg = newImgUrl
                }
                val usuario = repository.updateUsuario(updateUsuario!!, dni!!)
                usuario
            }catch (e: Exception){
                e.printStackTrace()
                null
            }
        }
    }
}