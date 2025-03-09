package domain.usecase.usuarios

import domain.infraestructure.Utils
import domain.models.usuarios.Usuario
import domain.repository.UsuarioInteface
import ktor.ApplicationContext


class InsertUsuarioUseCase(val repository: UsuarioInteface) {
    var usuario : Usuario? = null

    suspend operator fun invoke() : Usuario?{
        val us = repository.getUsuarioByDni(usuario!!.dni)

        return if (us != null){
            null
        }else{
            val isCreateDir = Utils.createDir(usuario!!.dni)
            if (isCreateDir){
                val img = usuario!!.urlImg
                if (!img.isNullOrBlank()){
                    usuario!!.urlImg = Utils.createBase64ToImg(img, usuario!!.dni)
                }
            }else{
                throw IllegalStateException("No se pudo crear el directorio del usuario. Puede que ya exista")
            }

            val new = repository.postUsuario(usuario!!)

            new?.let { user ->
                if (!user.urlImg.isNullOrBlank()) {
                    val local = ApplicationContext.context.environment.config.property("ktor.urlPath.baseUrl").getString()
                    val relativePath = ApplicationContext.context.environment.config.property("ktor.urlPath.images").getString()
                    new.urlImg = "$local/$relativePath/${new.dni}/${user.urlImg}"
                }
            }
            return new
        }
    }
}