package cz.mikealdo.fotbalcz

import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import cz.mikealdo.creator.LeaguesCreator
import cz.mikealdo.fotbalcz.api.CompetitionSettings
import cz.mikealdo.fotbalcz.domain.FotbalCzLeague
import cz.mikealdo.parser.MatchStatisticsParser
import cz.mikealdo.fotbalcz.api.FotbalCzLeagueJsonBuilder
import cz.mikealdo.fotbalcz.api.PropagationWorker
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Required
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.validation.constraints.NotNull
import java.util.concurrent.Callable

import static cz.mikealdo.config.Versions.*
import static org.springframework.web.bind.annotation.RequestMethod.GET

@Slf4j
@RestController
@RequestMapping('/api')
@TypeChecked
@Api(value = "competitionHash", description = "Return results for given competition.")
class ResultsController {

    private PropagationWorker propagationWorker
    private MatchStatisticsParser parser
    private FotbalCzLeagueJsonBuilder builder

    @Autowired
    ResultsController(PropagationWorker propagationWorker, MatchStatisticsParser parser, FotbalCzLeagueJsonBuilder builder) {
        this.propagationWorker = propagationWorker
        this.parser = parser
        this.builder = builder
    }

    @RequestMapping(
            value = '{competitionHash}',
            method = GET,
            consumes = FOTBAL_CZ_API_JSON_VERSION_1,
            produces = FOTBAL_CZ_API_JSON_VERSION_1)
    @ApiOperation(value = "Sync operation to retrieve all results for given competition",
            notes = "This will asynchronously call results-storage to persist current state to not contact fotbal.cz each time.")
    Callable<FotbalCzLeague> getPlacesFromTweets(@PathVariable @NotNull String competitionHash, @RequestParam(required = false) Integer round) {
        return {
            def settings = new CompetitionSettings(round)
            LeaguesCreator creator = new LeaguesCreator(parser);
            def league = creator.createLeague(competitionHash, settings);
            return builder.buildLeagueJson(competitionHash, league, settings)
        }
    }

}
