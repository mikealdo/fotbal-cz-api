package cz.mikealdo.fotbalcz

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import cz.mikealdo.fotbalcz.results.ResultsStorageClient
import groovy.transform.CompileStatic

import java.util.concurrent.atomic.AtomicReference

@CompileStatic
class ResultsStorageClientStub extends ResultsStorageClient {

    final AtomicReference<String> savedResultHash = new AtomicReference<>()
    final AtomicReference<String> savedJson = new AtomicReference<>()

    ResultsStorageClientStub(ServiceRestClient serviceRestClient) {
        super(serviceRestClient)
    }

    @Override
    void saveResultsToStorage(String resultHash, String json) {
        savedResultHash.set(resultHash)
        savedJson.set(json)
        super.saveResultsToStorage(resultHash, json)
    }
}
