package cz.mikealdo.fotbalcz

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import cz.mikealdo.pages.CompetitionPage
import cz.mikealdo.pages.CompetitionPageStub
import groovy.transform.CompileStatic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@CompileStatic
@Configuration
class ResultsStorageClientStubWithFotbalCzConfiguration {

    @Bean @Primary
    ResultsStorageClientStub resultsStorageClientStub(ServiceRestClient serviceRestClient) {
        return new ResultsStorageClientStub(serviceRestClient)
    }

    @Bean @Primary
    CompetitionPage competitionPageStub() {
        return new CompetitionPage()
    }
}
