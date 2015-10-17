package cz.mikealdo.place.extractor

import cz.mikealdo.fotbalcz.domain.FotbalCzLeague

interface PropagationWorker {
    void collectAndPropagate(long pairId, List<FotbalCzLeague> tweets)
}