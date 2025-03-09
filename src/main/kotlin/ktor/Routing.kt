package ktor

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import ktor.routing.authRouting
import ktor.routing.partidasRouting
import ktor.routing.userRouting
import java.io.File

fun Application.configureRouting() {
    routing {

        authRouting()
        partidasRouting()
        userRouting()



        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")

        staticFiles("/images", File("upload/images"))
        staticFiles("/files", File("upload/files"))
    }




}
