package cz.mikealdo.geb.microservicejson

import cz.mikealdo.geb.HealthEndpointPageUISpec
import org.springframework.boot.test.IntegrationTest

@IntegrationTest("spring.profiles.active:dev")
class MicroserviceJsonHealthEndpointUISpec extends HealthEndpointPageUISpec {
}
