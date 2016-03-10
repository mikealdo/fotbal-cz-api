package cz.mikealdo.geb.springcloud

import cz.mikealdo.geb.HealthEndpointPageUISpec
import org.springframework.boot.test.IntegrationTest

@IntegrationTest("spring.profiles.active:dev,springCloud")
class SpringCloudHealthEndpointUISpec extends HealthEndpointPageUISpec {
}
