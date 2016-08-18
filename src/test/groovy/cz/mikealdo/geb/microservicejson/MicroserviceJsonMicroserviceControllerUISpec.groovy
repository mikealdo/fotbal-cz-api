package cz.mikealdo.geb.microservicejson

import cz.mikealdo.geb.MicroserviceControllerUISpec
import groovy.json.JsonSlurper
import org.springframework.boot.test.IntegrationTest
import spock.lang.Ignore

@IntegrationTest("spring.profiles.active:dev")
class MicroserviceJsonMicroserviceControllerUISpec extends MicroserviceControllerUISpec {

    @Override
    protected inputJson() {
        def inputFile = getClass().getResource("/microservice.json")
        return new JsonSlurper().parseText(inputFile.text)
    }
}
