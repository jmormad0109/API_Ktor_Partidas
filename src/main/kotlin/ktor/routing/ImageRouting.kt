package ktor.routing

import domain.security.JwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ktor.ApplicationContext
import ktor.validateToken
import java.io.File

fun Route.imgRouting(){
    route("/image/{dni}/{image}") {
        authenticate("jwt-auth") {

            get(){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                if (token == null){
                    call.respond(HttpStatusCode.Unauthorized, "No estás autorizado")
                    return@get
                }

                if (!call.validateToken(token)){
                    return@get
                }

                val dniUsuario = JwtConfig.obtenerDniByToken(token)
                if (dniUsuario == null){
                    call.respond(HttpStatusCode.Unauthorized, "Token no valido")
                    return@get
                }

                val nameImg = call.parameters["image"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Tienes que pasar una imagen")

                val path = ApplicationContext.context.environment.config.property("ktor.path.images").getString() + "/$dniUsuario"
                val img = File(path, nameImg)

                if (!img.exists()){
                    return@get call.respond(HttpStatusCode.BadRequest, "Imagen no encontrada")
                }

                call.respond(img)
            }
        }
    }
}