package cz.mikealdo.fotbalcz

import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import cz.mikealdo.detailedleague.LeagueWithDetails
import cz.mikealdo.football.domain.CompetitionDetails
import cz.mikealdo.football.domain.League
import cz.mikealdo.fotbalcz.api.CompetitionSettings
import cz.mikealdo.fotbalcz.api.FotbalCzLeagueJsonBuilder
import cz.mikealdo.fotbalcz.api.PropagationWorker
import cz.mikealdo.pages.CompetitionPage
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.validation.constraints.NotNull
import java.util.concurrent.Callable

import static cz.mikealdo.config.Versions.FOTBAL_CZ_API_JSON_VERSION_1
import static org.springframework.web.bind.annotation.RequestMethod.GET

@Slf4j
@RestController
@RequestMapping('/api')
@TypeChecked
@Api(value = "competitionHash", description = "Return results for given competition.")
class ResultsController {

    private PropagationWorker propagationWorker
    private FotbalCzLeagueJsonBuilder builder
    private CompetitionPage competitionPage

    @Autowired
    ResultsController(PropagationWorker propagationWorker, FotbalCzLeagueJsonBuilder builder, CompetitionPage competitionPage) {
        this.propagationWorker = propagationWorker
        this.builder = builder
        this.competitionPage = competitionPage
    }

    @RequestMapping(
            value = '{competitionHash}',
            method = GET,
            consumes = FOTBAL_CZ_API_JSON_VERSION_1,
            produces = FOTBAL_CZ_API_JSON_VERSION_1)
    @ApiOperation(value = "Sync operation to retrieve all results for given competition",
            notes = "This will asynchronously call results-storage to persist current state to not contact fotbal.cz each time.")
    Callable<League> retrieveResultsForCompetitions(@PathVariable @NotNull String competitionHash, @RequestParam(required = false) Integer round) {
        return {
            def settings = new CompetitionSettings(round)
            CompetitionDetails competitionDetails = competitionPage.createCompetitionDetailsFrom(competitionHash)
            def league = new LeagueWithDetails(new League(competitionDetails.getCompetitionName())).enhanceByDetails(competitionDetails);

            def json = builder.buildLeagueJson(competitionHash, league, settings)
            propagationWorker.collectAndPropagate(competitionHash, json)
            return json
        }
    }

}
