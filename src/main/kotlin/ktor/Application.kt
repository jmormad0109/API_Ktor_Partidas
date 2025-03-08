package ktor

import com.example.ktor.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureContext(this)
    configureSerialization()
    configureSecurity()
    configureDatabase()
    configureRouting()
}
