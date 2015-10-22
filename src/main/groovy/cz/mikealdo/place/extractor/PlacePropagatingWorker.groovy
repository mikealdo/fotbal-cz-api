package cz.mikealdo.place.extractor
import cz.mikealdo.fotbalcz.domain.FotbalCzLeague
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

@TypeChecked
@Slf4j
class PlacePropagatingWorker implements PropagationWorker {
    
    private final FotbalCzLeagueJsonBuilder leaguesJsonBuilder

    @Autowired
    PlacePropagatingWorker(FotbalCzLeagueJsonBuilder leaguesJsonBuilder) {
        this.leaguesJsonBuilder = leaguesJsonBuilder
    }

    @Override
    void collectAndPropagate(String competitionHash, FotbalCzLeague league) {
        String jsonToPropagate = leaguesJsonBuilder.buildLeagueJson(competitionHash, league)
        log.debug("Sent json [$jsonToPropagate] to fotbal-cz-persister")
    }
}
