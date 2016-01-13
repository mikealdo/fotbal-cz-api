package cz.mikealdo.place.extractor

//import com.ofg.base.MicroserviceMvcWiremockSpec
//import cz.mikealdo.resultsstorage.place.extractor.ColleratorClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest

@IntegrationTest(["stubrunner.use-microservice-definitions:true", "server.port=0", "management.port=0"])
class DependencyFromServiceSpec {//extends cz.mikealdo.base.MicroserviceMvcWiremockSpec {

    @Autowired
//    ColleratorClient client;

    def "Should not fail on run when downloading stubs per project"() {
        when:
            client.populatePlaces(1, "nothing")
        then:
            noExceptionThrown()

    }
}
