package com.example.ktor

import domain.models.partidas.Resultado
import domain.usecase.UseCaseProvider
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {

        //EndPoint de ejemplo
        get("/") {
            call.respondText("Hello World!")
        }


        get("/partida") {
            val nombrePartida = call.request.queryParameters["nombre"]

            if (nombrePartida != null){
                val partida = UseCaseProvider.getPartidasByNombre(nombrePartida)
                if (partida == null){
                    call.respond(HttpStatusCode.NotFound, "Empleado no encontrado")
                }else{
                    call.respond(partida)
                }

                return@get
            }

            val resultado = call.request.queryParameters["resultado"]

            if (resultado != null){
                try{
                    val resul = resultado.uppercase()
                    val partidas = UseCaseProvider.getPartidasByResultado(Resultado.valueOf(resultado.uppercase()))
                    call.respond(partidas)
                }catch (e: IllegalArgumentException){
                    call.respond(HttpStatusCode.BadRequest, "El resultado no es valido")
                }
            }else{
                val partidas = UseCaseProvider.getAllPartidas()
                call.respond(partidas)
            }
        }


        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}
