package ktor.routing

import domain.mapping.toUpdateUsuario
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario
import domain.usecase.usuarios.UseCaseProviderUsuarios
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRouting(){

    route("/auth"){

        post(){
            try {
                val loginRequest = call.receive<UpdateUsuario>()
                val login: Usuario? = UseCaseProviderUsuarios.login(loginRequest.dni, loginRequest.password)

                if (login != null){
                    val usuario = login.toUpdateUsuario()
                    usuario.token = login!!.token
                    call.respond(HttpStatusCode.OK, "Usuario logueado correctamente")
                }else{
                    call.respond(HttpStatusCode.Unauthorized, "Problemas en la autenticación")
                }
            }catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest, "Formato de solicitud incorrecto")
                return@post
            }
        }
    }

    route("/register"){

        post(){

        }
    }
}