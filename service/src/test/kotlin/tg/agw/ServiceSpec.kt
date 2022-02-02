package tg.agw

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.*

class ServiceSpec: WordSpec() {
    init {
        "API Gateway Service" should {
            "handle health requests" {
                withTestApplication(Application::systemModule) {
                    with(handleRequest(HttpMethod.Get, "/health")) {
                        response.status() shouldBe  HttpStatusCode.OK
                    }
                }
            }
        }
    }
}
