package ktor

import io.ktor.server.application.*

object ApplicationContext {
    lateinit var context: Application
}

fun Application.configureContext(context: Application){
    ApplicationContext.context = context
}