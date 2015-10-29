package cz.mikealdo.fotbalcz.api
import cz.mikealdo.fotbalcz.domain.FotbalCzLeague
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

@TypeChecked
@Slf4j
class FotbalCzLeaguePropagatingWorker implements PropagationWorker {
    
    private final FotbalCzLeagueJsonBuilder leaguesJsonBuilder

    @Autowired
    FotbalCzLeaguePropagatingWorker(FotbalCzLeagueJsonBuilder leaguesJsonBuilder) {
        this.leaguesJsonBuilder = leaguesJsonBuilder
    }

    @Override
    void collectAndPropagate(String competitionHash, FotbalCzLeague league) {
        String jsonToPropagate = leaguesJsonBuilder.buildLeagueJson(competitionHash, league)
        log.debug("Sent json [$jsonToPropagate] to fotbal-cz-persister")
    }
}
