package ktor.routing

import domain.mapping.toUpdateUsuario
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario
import domain.usecase.usuarios.UseCaseProviderUsuarios
import io.ktor.http.*
import io.ktor.serialization.*
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
                    call.respond(HttpStatusCode.OK, usuario)
                }else{
                    call.respond(HttpStatusCode.Unauthorized, "Error en la autenticación")
                }
            }catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest, "Formato de solicitud incorrecto")
                return@post
            }
        }
    }

    route("/register"){

        post(){
            try{
                val user = call.receive<UpdateUsuario>()
                val register = UseCaseProviderUsuarios.register(user)

                if (register != null){
                    val updateUsuario = register.toUpdateUsuario()
                    call.respond(HttpStatusCode.Created, updateUsuario)
                }else{
                    call.respond(HttpStatusCode.Conflict, "No se ha podido hacer el registro correctamente")
                }
            }catch (e: IllegalStateException){
                call.respond(HttpStatusCode.BadRequest, "Error en el formato de envio o elctura de los datos")
            }catch (e: JsonConvertException){
                call.respond(HttpStatusCode.BadRequest, "Problemas en la conversión json")
            }
        }
    }
}