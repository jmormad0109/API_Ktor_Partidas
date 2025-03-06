package ktor.routing

import domain.mapping.toUpdatePartida
import domain.models.partidas.Partida
import domain.models.partidas.Resultado
import domain.models.partidas.UpdatePartida
import domain.usecase.partidas.UseCaseProviderPartidas
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ktor.validateToken

fun Route.partidasRouting(){

    route("/partida"){
        authenticate("jwt-auth"){
            get() {

                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                val validate = call.validateToken(token!!)
                if (!validate){
                    return@get
                }

                val nombrePartida = call.request.queryParameters["nombrePartida"]
                if(nombrePartida != null){
                    val partida = UseCaseProviderPartidas.getPartidasByNombre(nombrePartida)
                    if (partida == null){
                        call.respond(HttpStatusCode.NotFound, "No se ha encontrado la partida")
                    }else{
                        val upPartida = partida.toUpdatePartida()
                        call.respond(upPartida)
                    }
                    return@get
                }

                val resultado = call.request.queryParameters["resultado"]
                if (resultado != null){
                    try{
                        val resul = resultado.uppercase()
                        val partidas = UseCaseProviderPartidas.getPartidasByResultado(Resultado.valueOf(resultado.uppercase()))
                        call.respond(partidas)
                    }catch (e: IllegalArgumentException){
                        call.respond(HttpStatusCode.BadRequest, "El el resultado no es un valor válido")
                    }
                }else{
                    val partidas = UseCaseProviderPartidas.getAllPartidas()
                    call.respond(partidas)
                }
            }

            get("{nombrePartida}"){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                val validate = call.validateToken(token!!)
                if (!validate){
                    return@get
                }

                val nombrePartida = call.parameters["nombrePartida"]
                if (nombrePartida == null){
                    call.respond(HttpStatusCode.BadRequest, "Debes proporcionar el nombre de la partida que quieres buscar")
                    return@get
                }

                val partida = UseCaseProviderPartidas.getPartidasByNombre(nombrePartida)
                if (partida == null){
                    call.respond(HttpStatusCode.NotFound, "No se ha encontrado la partida. Puede que no exista.")
                    return@get
                }
                call.respond(partida)
            }

            get("{resultado}"){

            }

            post(){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ") //token el header
                val validate = call.validateToken(token!!)  //si llega aqúi, es porque el token se ha verificado antes automaticamente
                if (!validate) {
                    return@post
                }

                try{
                    val partida = call.receive<Partida>()
                    val res = UseCaseProviderPartidas.insertPartida(partida)
                    if (!res){
                        call.respond(HttpStatusCode.Conflict, "No se ha insertado la partida. Puede que ya exista.")
                        return@post
                    }
                    call.respond(HttpStatusCode.Created, "Se ha insertado la nueva partida")
                }catch (e: IllegalStateException){
                    call.respond(HttpStatusCode.BadRequest, "Error en el formato de envío de los datos")
                }catch (e: JsonConvertException){
                    call.respond(HttpStatusCode.BadRequest, "Error en la transformación del JSON")
                }catch (e: Exception){
                    call.respond(HttpStatusCode.BadRequest, "Error en los datos. Faltan datos." + e.message)
                }
            }

            patch("{nombrePartida}"){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ") //token el header
                val validate = call.validateToken(token!!)  //si llega aqúi, es porque el token se ha verificado antes automaticamente
                if (!validate) {
                    return@patch
                }
                try{
                    val nombre = call.parameters["nombrePartida"]
                    nombre?.let {
                        val updatePartida = call.receive<UpdatePartida>()
                        val res = UseCaseProviderPartidas.updatePartida(updatePartida, nombre)
                        if (!res){
                            call.respond(HttpStatusCode.Conflict, "La partida no se ha modificádo. Puede que no exita")
                            return@patch
                        }
                        call.respond(HttpStatusCode.Created, "Se ha actualizado la partida")
                    }?: run{
                        call.respond(HttpStatusCode.BadRequest, "Debes identificar la partida que quieres actualizar" + nombre)
                        return@patch
                    }
                }catch (e: IllegalStateException){
                    call.respond(HttpStatusCode.BadRequest, "Error en el formato de envío de los datos o lectura de los datos")
                }catch (e: JsonConvertException){
                    call.respond(HttpStatusCode.BadRequest, "Error en el formato del Json")
                }
            }

            delete("{nombrePartida}"){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ") //token el header
                val validate = call.validateToken(token!!)  //si llega aqúi, es porque el token se ha verificado antes automaticamente
                if (!validate) {
                    return@delete
                }
                val nombre = call.parameters["nombrePartida"]

                //val existe =
                nombre?.let {
                    val res = UseCaseProviderPartidas.deletePartida(nombre)
                    if (!res){
                        call.respond(HttpStatusCode.NotFound, "Partida no encontrada")
                    }else{
                        call.respond(HttpStatusCode.OK, "Parida eliminada correctamente")
                    }
                }?:run{
                    call.respond(HttpStatusCode.NoContent, "Tienes que identificar el nombre de la partida")
                }

                return@delete
            }
        }
        }
    }
