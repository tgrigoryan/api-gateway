package tg.agw

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

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
