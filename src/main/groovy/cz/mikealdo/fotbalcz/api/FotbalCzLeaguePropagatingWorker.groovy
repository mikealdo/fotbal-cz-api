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
    void collectAndPropagate(String competitionHash, String json) {
        client.saveResultsToStorage(competitionHash, json)
        log.debug("Sent json [$json] to results-storage")
    }
}
