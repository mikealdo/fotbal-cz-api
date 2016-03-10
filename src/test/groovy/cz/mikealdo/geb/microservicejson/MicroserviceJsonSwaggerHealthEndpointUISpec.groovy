package cz.mikealdo.geb.microservicejson

import cz.mikealdo.geb.SwaggerHealthEndpointUISpec
import org.springframework.boot.test.IntegrationTest

@IntegrationTest("spring.profiles.active:dev")
class MicroserviceJsonSwaggerHealthEndpointUISpec extends SwaggerHealthEndpointUISpec {
}
