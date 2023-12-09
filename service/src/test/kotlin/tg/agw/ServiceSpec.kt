package tg.agw

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication

class ServiceSpec : WordSpec() {
    init {
        "API Gateway Service" should {
            "handle health requests" {
                withTestApplication(Application::adminModuleForTest) {
                    with(handleRequest(HttpMethod.Get, "/_admin/health")) {
                        response.status() shouldBe HttpStatusCode.OK
                    }
                }
            }
        }
    }
}

fun Application.adminModuleForTest() = adminModule(
    Config.Admin("_admin", "mirror", "health")
)
