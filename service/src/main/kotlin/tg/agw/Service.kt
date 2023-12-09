package tg.agw

import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun main() {
    val config = Config.ApiGateway(
        Config.Service("0.0.0.0"),
        Config.Admin()
    )

    embeddedServer(Netty, port = config.service.port, host = config.service.host) {
        adminModule(config.admin)
    }.start(wait = true)
}

fun Application.adminModule(adminConfig: Config.Admin) {
    routing {
        route("/${adminConfig.root}") {
            get(adminConfig.health) {
                call.respondText("""{"status":"healthy"}""", Json)
            }
            get(adminConfig.mirror) {
                call.respondText(status = HttpStatusCode.ServiceUnavailable) { "Not implemented" }
            }
        }
    }
}
