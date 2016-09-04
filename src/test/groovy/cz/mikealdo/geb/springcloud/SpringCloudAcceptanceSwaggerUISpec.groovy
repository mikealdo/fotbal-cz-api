package cz.mikealdo.geb.springcloud

import cz.mikealdo.geb.AcceptanceSwaggerUISpec
import org.junit.Ignore
import org.springframework.boot.test.IntegrationTest

@IntegrationTest("spring.profiles.active:dev,springCloud")
@Ignore
class SpringCloudAcceptanceSwaggerUISpec extends AcceptanceSwaggerUISpec {
}
