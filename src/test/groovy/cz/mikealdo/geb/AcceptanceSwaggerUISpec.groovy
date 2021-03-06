package cz.mikealdo.geb

import cz.mikealdo.geb.pages.SwaggerUIHomePage
import spock.lang.Stepwise

@Stepwise
abstract class AcceptanceSwaggerUISpec extends BaseBootGebUISpec {

    def "SwaggerUI home page should be visible"() {
        when:
            to SwaggerUIHomePage
        then:
            at SwaggerUIHomePage
    }

    def "Endpoint microservice-configuration-controller is visible"() {
        when:
            to SwaggerUIHomePage
        then:
            at SwaggerUIHomePage
            waitFor { metricsMvcEndpointText.displayed }
            metricsMvcEndpointText.displayed
        when:
            showMicroservice.click()
        then:
            microserviceJsonText.displayed
    }

    def "Endpoint health-mvc-endpoint is visible"() {
        when:
            to SwaggerUIHomePage
        then:
            at SwaggerUIHomePage
            waitFor { healthMvcEndpointText.displayed }
            healthMvcEndpointText.displayed
            showHealthMVCEndpoints.displayed
    }

    def "Endpoint 'results' is visible"() {
        when:
            to SwaggerUIHomePage
        then:
            at SwaggerUIHomePage
        when:
            waitFor { showResultsEndpoints.displayed }
            showResultsEndpoints.click()
        then:
            waitFor { competitionHashPutText.displayed }
            competitionHashPutText.text() == "/api/{competitionHash}"

    }

}