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
                val validate = call.validateToken(token!!)
                if (!validate)
                    return@get

                val dni = call.parameters["dni"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Necesitamos el DNI")
                val nameImg = call.parameters["image"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Tienes que pasar una imagen")

                val path = ApplicationContext.context.environment.config.property("ktor.path.images").getString() + "/$dni"
                val img = File(path, nameImg)

                if (!img.exists()){
                    return@get call.respond(HttpStatusCode.BadRequest, "Imagen no encontrada")
                }

                call.respond(img)
            }
        }
    }
}