package cz.mikealdo.twitter.place

import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import cz.mikealdo.creator.LeaguesCreator
import cz.mikealdo.fotbalcz.domain.FotbalCzLeague
import cz.mikealdo.twitter.config.Versions
import cz.mikealdo.twitter.place.extractor.PropagationWorker
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.constraints.NotNull
import java.util.concurrent.Callable

import static org.springframework.web.bind.annotation.RequestMethod.PUT

@Slf4j
@RestController
@RequestMapping('/api')
@TypeChecked
@Api(value = "competitionHash", description = "Return results for given competition.")
class ResultsController {

    private PropagationWorker propagationWorker;

    @Autowired ResultsController(PropagationWorker propagationWorker) {
        this.propagationWorker = propagationWorker;
    }

    @RequestMapping(
            value = '{competitionHash}',
            method = PUT,
            consumes = Versions.FOTBAL_CZ_API_JSON_VERSION_1,
            produces = Versions.FOTBAL_CZ_API_JSON_VERSION_1)
    @ApiOperation(value = "Async collecting and propagating of tweets for a given pairId",
            notes = "This will asynchronously call tweet collecting, place extracting and their propagation to Collerators")
    Callable<FotbalCzLeague> getPlacesFromTweets(@PathVariable @NotNull String competitionHash) {
        return {
            LeaguesCreator creator = new LeaguesCreator();
            creator.createLeague(competitionHash);
        }
    }

}
