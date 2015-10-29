package cz.mikealdo.fotbalcz.api

import cz.mikealdo.fotbalcz.domain.FotbalCzLeague

interface PropagationWorker {
    void collectAndPropagate(String competitionHash, FotbalCzLeague league)
}