package cz.mikealdo.twitter.place.extractor

import cz.mikealdo.twitter.place.model.Tweet

interface PropagationWorker {
    void collectAndPropagate(long pairId, List<Tweet> tweets)
}