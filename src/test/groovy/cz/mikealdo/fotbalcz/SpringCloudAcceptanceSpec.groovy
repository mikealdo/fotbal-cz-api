package cz.mikealdo.fotbalcz

import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [ResultsStorageClientStubConfiguration])
@ActiveProfiles('springCloud')
class SpringCloudAcceptanceSpec {// extends AbstractAcceptanceSpec {
 // TODO
}
