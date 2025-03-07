package ktor.routing

import domain.mapping.toPartidaSinDni
import domain.mapping.toUpdatePartida
import domain.models.partidas.Partida
import domain.models.partidas.PartidaSinDni
import domain.models.partidas.Resultado
import domain.models.partidas.UpdatePartida
import domain.security.JwtConfig
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

                val nombrePartida = call.request.queryParameters["nombrePartida"]
                if(nombrePartida != null){
                    val partida = UseCaseProviderPartidas.getPartidasByNombre(nombrePartida, dniUsuario)
                    if (partida == null){
                        call.respond(HttpStatusCode.NotFound, "No se ha encontrado la partida")
                    }else{
                        //val upPartida = partida.toUpdatePartida()
                        call.respond(partida)
                    }
                    return@get
                }

                val resultado = call.request.queryParameters["resultado"]
                if (resultado != null){
                    try{
                        val resul = Resultado.valueOf(resultado.uppercase())
                        val partidas = UseCaseProviderPartidas.getPartidasByResultado(resul, dniUsuario)
                        call.respond(partidas)
                    }catch (e: IllegalArgumentException){
                        call.respond(HttpStatusCode.BadRequest, "El el resultado no es un valor válido")
                    }
                }else{
                    val partidas = UseCaseProviderPartidas.getAllPartidas(dniUsuario)
                    call.respond(partidas)
                }
            }

            get("{nombrePartida}"){
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

                val nombrePartida = call.parameters["nombrePartida"]
                if (nombrePartida == null){
                    call.respond(HttpStatusCode.BadRequest, "Debes proporcionar el nombre de la partida que quieres buscar")
                    return@get
                }

                val partida = UseCaseProviderPartidas.getPartidasByNombre(nombrePartida, dniUsuario)
                if (partida == null){
                    call.respond(HttpStatusCode.NotFound, "No se ha encontrado la partida. Puede que no exista.")
                    return@get
                }
                call.respond(partida)
            }

            get("{resultado}"){

            }

            post(){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                if (token == null){
                    call.respond(HttpStatusCode.Unauthorized, "No estás autorizado")
                    return@post
                }

                if (!call.validateToken(token)){
                    return@post
                }

                val dniUsuario = JwtConfig.obtenerDniByToken(token)
                if (dniUsuario == null){
                    call.respond(HttpStatusCode.Unauthorized, "Token no valido")
                    return@post
                }

                try{
                    val partida = call.receive<PartidaSinDni>()
                    val res = UseCaseProviderPartidas.insertPartida(partida, dniUsuario)
                    if (res == null){
                        call.respond(HttpStatusCode.Conflict, "No se ha insertado la partida. Puede que ya exista.")
                        return@post
                    }
                    call.respond(HttpStatusCode.Created, partida)
                }catch (e: IllegalStateException){
                    call.respond(HttpStatusCode.BadRequest, "Error en el formato de envío de los datos")
                }catch (e: JsonConvertException){
                    call.respond(HttpStatusCode.BadRequest, "Error en la transformación del JSON")
                }catch (e: Exception){
                    call.respond(HttpStatusCode.BadRequest, "Error en los datos. Faltan datos." + e.message)
                }
            }

            patch("{nombrePartida}"){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                if (token == null){
                    call.respond(HttpStatusCode.Unauthorized, "No estás autorizado")
                    return@patch
                }

                if (!call.validateToken(token)){
                    return@patch
                }

                val dniUsuario = JwtConfig.obtenerDniByToken(token)
                if (dniUsuario == null){
                    call.respond(HttpStatusCode.Unauthorized, "Token no valido")
                    return@patch
                }
                try{
                    val nombre = call.parameters["nombrePartida"]
                    nombre?.let {
                        val updatePartida = call.receive<UpdatePartida>()
                        //val updatePartida = partida.toUpdatePartida()
                        val res = UseCaseProviderPartidas.updatePartida(updatePartida, nombre, dniUsuario)
                        if (res == null){
                            call.respond(HttpStatusCode.Conflict, "La partida no se ha modificádo. Puede que no exita")
                            return@patch
                        }
                        call.respond(HttpStatusCode.Created, res.toPartidaSinDni())
                    }?: run{
                        call.respond(HttpStatusCode.BadRequest, "Debes identificar la partida que quieres actualizar" + nombre)
                        return@patch
                    }
                }catch (e: IllegalStateException){
                    call.respond(HttpStatusCode.BadRequest, "Error en el formato de envío de los datos o lectura de los datos")
                }catch (e: JsonConvertException){
                    call.respond(HttpStatusCode.BadRequest, "Error en el formato del Json")
                }catch (e: Exception){
                    e.printStackTrace()
                    call.respond(HttpStatusCode.BadRequest, "Algo falla " + e.message)
                }
            }

            delete("{nombrePartida}"){
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                if (token == null){
                    call.respond(HttpStatusCode.Unauthorized, "No estás autorizado")
                    return@delete
                }

                if (!call.validateToken(token)){
                    return@delete
                }

                val dniUsuario = JwtConfig.obtenerDniByToken(token)
                if (dniUsuario == null){
                    call.respond(HttpStatusCode.Unauthorized, "Token no valido")
                    return@delete
                }
                val nombre = call.parameters["nombrePartida"]

                nombre?.let {
                    val res = UseCaseProviderPartidas.deletePartida(nombre, dniUsuario)
                    if (res == null){
                        call.respond(HttpStatusCode.NotFound, "Partida no encontrada")
                    }else{
                        call.respond(res.toPartidaSinDni())
                        //call.respond(HttpStatusCode.OK, "Parida eliminada correctamente")
                    }
                }?:run{
                    call.respond(HttpStatusCode.NoContent, "Tienes que identificar el nombre de la partida")
                }

                return@delete
            }
        }
        }
    }
