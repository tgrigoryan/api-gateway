package tg.agw

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

import io.ktor.routing.routing
import io.ktor.routing.get
import io.ktor.http.ContentType.Application.Json
import io.ktor.response.respondText

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        systemModule()
    }.start(wait = true)
}

fun Application.systemModule() {
    routing {
        get("/health") {
            call.respondText("""{"status":"healthy"}""", Json)
        }
    }
}
