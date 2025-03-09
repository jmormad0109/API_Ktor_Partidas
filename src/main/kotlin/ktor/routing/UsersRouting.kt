package ktor.routing

import domain.mapping.toUpdateUsuario
import domain.models.usuarios.UpdateUsuario
import domain.models.usuarios.Usuario
import domain.usecase.usuarios.UseCaseProviderUsuarios
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRouting(){

    route("/usuario"){

        authenticate("jwt-auth"){

            get(){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                if (token == null){
                    call.respond(HttpStatusCode.Unauthorized, "No estás autorizado")
                    return@get
                }


                val usuarioDni = call.request.queryParameters["dni"]

                if (usuarioDni != null){
                    val usuario = UseCaseProviderUsuarios.getUsuarioByDni(usuarioDni)
                    if (usuario == null){
                        call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
                    }else{
                        val upUsuario = usuario.toUpdateUsuario()
                        call.respond(upUsuario)
                    }
                    return@get
                }

                val usuarios = UseCaseProviderUsuarios.getAllUsuarios()
                call.respond(usuarios)
            }

            get("{usuarioDni}"){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                if (token == null){
                    call.respond(HttpStatusCode.Unauthorized, "No estás autorizado")
                    return@get
                }

                val usuarioDni = call.parameters["usuarioDni"]
                if (usuarioDni == null){
                    call.respond(HttpStatusCode.BadRequest, "No se ha proporcionado un dni")
                    return@get
                }

                val usuario = UseCaseProviderUsuarios.getUsuarioByDni(usuarioDni)
                if (usuario == null){
                    call.respond(HttpStatusCode.NotFound, "No se ha encontrado al usuario con dni: $usuarioDni")
                    return@get
                }
                call.respond(usuario)
            }

            post(){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                if (token == null){
                    call.respond(HttpStatusCode.Unauthorized, "No estás autorizado")
                    return@post
                }
                try {
                    val usuario = call.receive<Usuario>()
                    val new = UseCaseProviderUsuarios.insertUsuario(usuario)

                    if (new == null){
                        call.respond(HttpStatusCode.Conflict, "No se ha podido insertar el usuario. Puede que ya exista")
                        return@post
                    }

                    call.respond(HttpStatusCode.Created, usuario)
                } catch (e : IllegalStateException){
                    call.respond(HttpStatusCode.BadRequest, "Error en el formato de envío de datos o lectura del cuerpo.")
                } catch (e: JsonConvertException){
                    e.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest," Problemas en la conversión json")
                } catch (e: Exception){
                    call.respond(HttpStatusCode.BadRequest, "Error en los datos. Probablemente falten." + e.message)
                }
            }


            patch("{usuarioDni}"){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                if (token == null){
                    call.respond(HttpStatusCode.Unauthorized, "No estás autorizado")
                    return@patch
                }

                try{
                    val dni = call.parameters["usuarioDni"]
                    dni?.let {
                        val updateUsuario = call.receive<UpdateUsuario>()
                        val update = UseCaseProviderUsuarios.updateUsuario(updateUsuario,dni)

                        if (update == null){
                            call.respond(HttpStatusCode.Conflict, "NO se ha podido modificar el usuairo. Puede que no exista")
                            return@patch
                        }

                        call.respond(HttpStatusCode.Created, update)
                    }?: run {
                        call.respond(HttpStatusCode.BadRequest, "Debes identificar el usuario")
                    }
                } catch (e: IllegalStateException){
                    e.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest,"Error en el formado de envío de los datos o lectura del cuerpo." + e.message)
                } catch (e: JsonConvertException){
                    call.respond(HttpStatusCode.BadRequest,"Error en el formado de json")
                }
            }


            delete("{usuarioDni}"){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                if (token == null){
                    call.respond(HttpStatusCode.Unauthorized, "No estás autorizado")
                    return@delete
                }

                val dni = call.parameters["usuarioDni"]

                dni?.let {
                    val res = UseCaseProviderUsuarios.deleteUsuario(dni)
                    if (!res){
                        call.respond(HttpStatusCode.NotFound, "No se ha encontrado al usuario con dni: $dni")
                    }else{
                        call.respond(HttpStatusCode.NoContent)
                    }
                }?:run{
                    call.respond(HttpStatusCode.NoContent, "Debes identificar el usuario a borrar")
                }

                return@delete
            }
        }
    }
}