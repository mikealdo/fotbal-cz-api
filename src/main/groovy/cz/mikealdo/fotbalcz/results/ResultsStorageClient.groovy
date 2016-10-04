package cz.mikealdo.fotbalcz.results

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import cz.mikealdo.config.Collaborators
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

@Slf4j
@CompileStatic
class ResultsStorageClient {

    private final ServiceRestClient serviceRestClient

    @Autowired
    ResultsStorageClient(ServiceRestClient serviceRestClient) {
        this.serviceRestClient = serviceRestClient
    }

    void saveResultsToStorage(String resultHash, String json) {
        serviceRestClient.forService(Collaborators.RESULTS_STORAGE_DEPENDENCY_NAME)
                .put()
                .onUrlFromTemplate("/api/{resultHash}").withVariables(resultHash)
                .body(json)
                .anObject()
                .ofType(String)
    }

    String retrieveResults(String resultHash) {
        return serviceRestClient.forService(Collaborators.RESULTS_STORAGE_DEPENDENCY_NAME)
                .get()
                .onUrlFromTemplate("/api/{resultHash}").withVariables(resultHash)
                .anObject()
                .ofType(String)
    }
}
