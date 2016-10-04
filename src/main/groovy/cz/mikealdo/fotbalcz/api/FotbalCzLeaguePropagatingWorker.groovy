package cz.mikealdo.fotbalcz.api

import cz.mikealdo.fotbalcz.results.ResultsStorageClient
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

@TypeChecked
@Slf4j
class FotbalCzLeaguePropagatingWorker implements PropagationWorker {
    
    private final ResultsStorageClient client

    @Autowired
    FotbalCzLeaguePropagatingWorker(ResultsStorageClient client) {
        this.client = client
    }

    @Override
    void saveResultsToCache(String competitionHash, String json) {
        client.saveResultsToStorage(competitionHash, json)
        log.info("Sent json [$json] to results-storage")
    }

    @Override
    String retrieveResults(String competitionHash) {
        log.info("Retrieve results from results-storage")
        return client.retrieveResults(competitionHash)
    }
}
