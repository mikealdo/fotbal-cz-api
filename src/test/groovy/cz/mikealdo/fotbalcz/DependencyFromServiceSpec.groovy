package cz.mikealdo.fotbalcz

import cz.mikealdo.base.MicroserviceMvcWiremockSpec
import cz.mikealdo.fotbalcz.results.ResultsStorageClient
import jdk.nashorn.internal.ir.annotations.Ignore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest

@IntegrationTest(["stubrunner.use-microservice-definitions:true", "server.port=0", "management.port=0"])
class DependencyFromServiceSpec extends MicroserviceMvcWiremockSpec {

    @Autowired
    ResultsStorageClient client;

//    def "Should not fail on run when downloading stubs per project"() {
//        when:
//            client.saveResultsToStorage("hash", "nothing")
//        then:
//            noExceptionThrown()
//
//    }
}
