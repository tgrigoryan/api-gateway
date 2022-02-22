package tg.agw

sealed interface Config {

    object Default {
        const val port = 8080
        const val health = "health"
        const val root = "_admin"
        const val mirror = "mirror"
    }

    data class ApiGateway(val service: Service, val admin: Admin)

    data class Service(val host: String, val port: Int = Default.port)

    data class Admin(
        val root: String = Default.root,
        val mirror: String = Default.mirror,
        val health: String = Default.health
    )
}
