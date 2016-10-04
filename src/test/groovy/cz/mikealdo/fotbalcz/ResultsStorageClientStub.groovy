package cz.mikealdo.fotbalcz

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import cz.mikealdo.fotbalcz.results.ResultsStorageClient
import groovy.transform.CompileStatic

import java.util.concurrent.atomic.AtomicReference

@CompileStatic
class ResultsStorageClientStub extends ResultsStorageClient {

    AtomicReference<String> savedResultHash = new AtomicReference<>("")
    AtomicReference<String> savedJson = new AtomicReference<>("")

    ResultsStorageClientStub(ServiceRestClient serviceRestClient) {
        super(serviceRestClient)
    }

    @Override
    void saveResultsToStorage(String resultHash, String json) {
        savedResultHash.set(resultHash)
        savedJson.set(json)
    }

    @Override
    String retrieveResults(String resultHash) {
        return savedJson.get() == null ? "" : savedJson.get();
    }
}
