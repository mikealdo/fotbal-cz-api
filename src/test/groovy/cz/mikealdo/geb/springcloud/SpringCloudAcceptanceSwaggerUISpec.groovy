package cz.mikealdo.geb.springcloud

import cz.mikealdo.geb.AcceptanceSwaggerUISpec
import org.springframework.boot.test.IntegrationTest

@IntegrationTest("spring.profiles.active:dev,springCloud")
class SpringCloudAcceptanceSwaggerUISpec extends AcceptanceSwaggerUISpec {
}
