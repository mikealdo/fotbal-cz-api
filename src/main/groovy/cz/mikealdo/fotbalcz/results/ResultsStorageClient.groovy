package cz.mikealdo.fotbalcz.results

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import cz.mikealdo.config.Collaborators
import groovy.transform.CompileStatic

@CompileStatic
class ResultsStorageClient {

    private final ServiceRestClient serviceRestClient

    ResultsStorageClient(ServiceRestClient serviceRestClient) {
        this.serviceRestClient = serviceRestClient
    }

    void saveResultsToStorage(String resultHash, String json) {
        serviceRestClient.forService(Collaborators.RESULTS_STORAGE_DEPENDENCY_NAME)
                .post()
                .onUrlFromTemplate("/{resultHash}").withVariables(resultHash)
                .body(json)
                .anObject()
                .ofType(String)
    }
}
