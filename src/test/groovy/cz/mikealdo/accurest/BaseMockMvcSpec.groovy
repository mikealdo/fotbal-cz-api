package cz.mikealdo.accurest

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import cz.mikealdo.fotbalcz.ResultsController
import cz.mikealdo.fotbalcz.api.FotbalCzLeagueJsonBuilder
import cz.mikealdo.fotbalcz.api.PropagationWorker
import cz.mikealdo.pages.CompetitionPage
import spock.lang.Specification

abstract class BaseMockMvcSpec extends Specification {

    FotbalCzLeagueJsonBuilder builder = new FotbalCzLeagueJsonBuilder()

    def setup() {
        RestAssuredMockMvc.standaloneSetup(new ResultsController(createAndStubPropagationWorker(), builder, createAndStupCompetitionPage()))
    }

    private CompetitionPage createAndStupCompetitionPage() {
        return Mock(CompetitionPage)
    }

    private PropagationWorker createAndStubPropagationWorker() {
        return Mock(PropagationWorker)
    }
}
