package domain.infraestructure

import ktor.ApplicationContext
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO

class Utils {
    companion object{
        fun createBase64ToImg(img: String, dni: String) : String?{
            val groupExtension = listOf("jpg", "jpeg", "gif")
            /*
            primer grupo. sacamos el tipo de la imagen.
            segiundo grupo. sacamos el cuerpo.
             */
            val regex = "data:(image/[^;]+);base64,(.+)".toRegex()  //expresión regular. dos grupos.
            val result = regex.find(img)

            return if (result != null) {
                //todo ya tengo que crear la imagen
                val type = result.groupValues[1]
                var ext: String = type.split("/")[1]  //nos quedamos sólo con la extensión.
                val body = result.groupValues[2]
                if (ext !in groupExtension)
                    return null
                try {
                    if (ext =="jpg")
                        ext = "jpeg"//porque ImageIO.write, en caso de jpg, falla.

                    val imgBytes = Base64.getDecoder().decode(body)  //decodificamos y convertimos a ByteArray
                    val inputStream = ByteArrayInputStream(imgBytes) //Convertimos el array de bytes a flujo de datos
                    val bufferImage: BufferedImage = ImageIO.read(inputStream)  //A partir del flujo de entrada (imagen en bytes), convertimos a imagen.
                    val path : String = ApplicationContext.context.environment.config.property("ktor.path.images").getString() + "/$dni"
                    val dir = File(path)
                    if (dir.isDirectory){
                        val nFile: String = path+"/"
                        val nameFile = dni+"_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}.$ext"
                        val fileImag = File(nFile+nameFile)  //creamos el fichero con el nombre y donde queremos.
                        //ya podemos crear el fichero.a
                        ImageIO.write(bufferImage, ext, fileImag)
                        // val local = ApplicationContext.context.environment.config.property("ktor.urlPath.baseUrl").getString()
                        // val relativePath = ApplicationContext.context.environment.config.property("ktor.path.images").getString()
                        // val urlImage = "$local/$relativePath/$nameFile"
                        // return urlImage  //se ha creado la imagen y por tanto devolvemos la ubicación y su nombre
                        return nameFile
                    }else{
                        return null  //no existe el directorio, por tanto al haber un error se devuelve null
                    }
                }catch (e : Exception) {
                    e.printStackTrace()
                    return null  //ha producido alguna excepción y por tanto devuelve null
                }
            } else null  // no se ha creado la imagen, por tanto retornamos null

        }

        fun getNameFileBase64(img: String){

        }

        //todo
        fun deleteImage(dni: String, name: String):Boolean{
            try{
                val path = "${ApplicationContext.context.environment.config.property("ktor.path.images").getString()}/$dni"
                val img = File(path, name)
                return if (img.exists()){
                    img.delete()
                    true
                }
                else
                    false

            }catch (e: Exception){
                e.printStackTrace()
                return false
            }
        }

        fun createDir(dni: String) :Boolean{
            try{
                val path = ApplicationContext.context.environment.config.property("ktor.path.images").getString()
                val dir = File(path, dni)
                return if (!dir.exists()){
                    val created = dir.mkdirs()
                    if (created)
                        true
                    else
                        false
                }
                else
                    false
            }catch (e:Exception){
                e.printStackTrace()
                return false
            }
        }

        fun deleteDirectory(dni: String) : Boolean{
            try{
                val path = ApplicationContext.context.environment.config.property("ktor.path.images").getString()+"/$dni"
                val dir = File(path)
                if (dir.exists()){
                    return dir.deleteRecursively()
                }

            }catch (e:Exception){
                e.printStackTrace()
                return false
            }
            return false
        }
    }
}