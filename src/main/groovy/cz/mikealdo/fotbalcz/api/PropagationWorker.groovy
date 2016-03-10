package cz.mikealdo.fotbalcz.api

import cz.mikealdo.football.domain.League


interface PropagationWorker {
    void collectAndPropagate(String competitionHash, String json)
}