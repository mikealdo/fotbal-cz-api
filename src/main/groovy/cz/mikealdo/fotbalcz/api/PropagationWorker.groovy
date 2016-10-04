package cz.mikealdo.fotbalcz.api

interface PropagationWorker {
    void saveResultsToCache(String competitionHash, String json)
    String retrieveResults(String competitionHash)
}