package ktor

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import ktor.routing.authRouting
import ktor.routing.partidasRouting

fun Application.configureRouting() {
    routing {

        authRouting()
        partidasRouting()



        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }


        /*get("/partida") {
            val nombrePartida = call.request.queryParameters["nombre"]

            if (nombrePartida != null){
                val partida = UseCaseProviderPartidas.getPartidasByNombre(nombrePartida)
                if (partida == null){
                    call.respond(HttpStatusCode.NotFound, "Partida no encontrada")
                }else{
                    call.respond(partida)
                }

                return@get
            }

            val resultado = call.request.queryParameters["resultado"]

            if (resultado != null){
                try{
                    val resul = resultado.uppercase()
                    val partidas = UseCaseProviderPartidas.getPartidasByResultado(Resultado.valueOf(resultado.uppercase()))
                    call.respond(partidas)

                    return@get
                }catch (e: IllegalArgumentException){
                    call.respond(HttpStatusCode.BadRequest, "El resultado no es valido")
                }
            }else{
                val partidas = UseCaseProviderPartidas.getAllPartidas()
                call.respond(partidas)
                return@get
            }
        }

        get("/partida/{nombrePartida}"){
            val nombrePartida = call.parameters["nombrePartida"]

            if (nombrePartida == null){
                call.respond(HttpStatusCode.BadRequest, "Debes pasar el nombre de la partida que quieres buscar")
                return@get
            }

            val partida = UseCaseProviderPartidas.getPartidasByNombre(nombrePartida)
            if (partida == null){
                call.respond(HttpStatusCode.NotFound, "Partida no encontrada")
                return@get
            }
            call.respond(partida)
        }

        get("/partida/{resultado}"){

        }

        post("/partida"){
            try{
                val part = call.receive<Partida>()
                val res = UseCaseProviderPartidas.insertPartida(part)
                if (!res){
                    call.respond(HttpStatusCode.Conflict, "No se pudo insertar la partida. Puede que ya exista")
                    return@post
                }
                call.respond(HttpStatusCode.Created, "Se ha insertado la nueva partida")
            }catch (e: IllegalStateException){
                call.respond(HttpStatusCode.BadRequest, "Error en el formato de la petición")
            }catch (e: JsonConvertException){
                call.respond(HttpStatusCode.BadRequest, "Problema en la conversión json")
            }catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest, "Error en los datos de la petición  ..." + e.message + e.printStackTrace())
            }
        }

        patch("/partida/{nombrePartida}"){
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

        delete("/partida/{nombrePartida}"){
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
    }*/


}
