package domain.usecase.usuarios

import domain.infraestructure.Utils
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario
import domain.repository.UsuarioInteface

class RegisterUseCase(val repository: UsuarioInteface) {
    suspend operator fun invoke(usuario: UpdateUsuario): Usuario?{

        usuario.dni = usuario.dni!!
        usuario.password = usuario.password!!
        usuario.name = usuario.name!!
        usuario.email = usuario.email!!
        usuario.urlImg = usuario.urlImg!!
        usuario.token = usuario.token?: ""

        return if (repository.login(usuario.dni!!, usuario.password!!) != null) {
            null
        } else{
            usuario.apply {
                val isCreate = Utils.createDir(dni!!)
                if (isCreate){
                    if (!urlImg.isNullOrBlank()){
                        urlImg = Utils.createBase64ToImg(urlImg!!, dni!!)
                    }
                }else{
                    throw IllegalStateException("No se pudo crear el directorio para el usuaroi. Puede que ya exista.")
                }
            }
            val reg = repository.register(usuario)
            reg
        }
    }
}