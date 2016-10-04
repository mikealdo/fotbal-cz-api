package cz.mikealdo.fotbalcz

import cz.mikealdo.detailedleague.LeagueWithDetails
import cz.mikealdo.football.domain.CompetitionDetails
import cz.mikealdo.football.domain.League
import cz.mikealdo.fotbalcz.api.CompetitionSettings
import cz.mikealdo.fotbalcz.api.FotbalCzLeagueJsonBuilder
import cz.mikealdo.fotbalcz.api.PropagationWorker
import cz.mikealdo.fotbalcz.api.metrics.ResultsMetrics
import cz.mikealdo.pages.CompetitionPage
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import javax.validation.constraints.NotNull
import java.util.concurrent.Callable

import static org.springframework.web.bind.annotation.RequestMethod.GET

@Slf4j
@RestController
@RequestMapping('/api')
@TypeChecked
@Api(value = "competitionHash", description = "Return results for given competition.")
class ResultsController {

    public static final Logger LOG = LoggerFactory.getLogger(ResultsController.class);
    private PropagationWorker propagationWorker
    private FotbalCzLeagueJsonBuilder builder
    private CompetitionPage competitionPage
    private final ResultsMetrics metrics

    @Autowired
    ResultsController(PropagationWorker propagationWorker, FotbalCzLeagueJsonBuilder builder, CompetitionPage competitionPage, ResultsMetrics metrics) {
        this.propagationWorker = propagationWorker
        this.builder = builder
        this.competitionPage = competitionPage
        this.metrics = metrics
    }

    @RequestMapping(
            value = '{competitionHash}',
            method = GET,
            consumes = "application/vnd.fotbal-cz-api.v1+json",
            produces = "application/vnd.fotbal-cz-api.v1+json")
    @ApiOperation(value = "Sync operation to retrieve all results for given competition",
            notes = "This will asynchronously call results-storage to persist current state to not contact fotbal.cz each time.")
    Callable<String> retrieveResultsForCompetitions(@PathVariable @NotNull String competitionHash, @RequestParam(required = false) Integer round) {
        return {
            def results = propagationWorker.retrieveResults(competitionHash)
            if (results.isEmpty()) {
                def settings = new CompetitionSettings(round)
                CompetitionDetails competitionDetails = competitionPage.createCompetitionDetailsFrom(competitionHash)
                def league = new LeagueWithDetails(new League(competitionDetails.getCompetitionName())).enhanceByDetails(competitionDetails);
                metrics.update()
                results = builder.buildLeagueJson(competitionHash, league, settings)
                LOG.info("Json built for hash {}", competitionHash)
                propagationWorker.saveResultsToCache(competitionHash, results)
                LOG.info("Result storage hit with saving into cache.");
            }
            return results
        }
    }

}
