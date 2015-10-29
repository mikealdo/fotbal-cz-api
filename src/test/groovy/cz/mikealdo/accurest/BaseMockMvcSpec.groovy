package cz.mikealdo.accurest

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import cz.mikealdo.parser.MatchStatisticsParser
import cz.mikealdo.parser.MatchSummaryParser
import cz.mikealdo.fotbalcz.ResultsController
import cz.mikealdo.fotbalcz.api.FotbalCzLeagueJsonBuilder
import cz.mikealdo.fotbalcz.api.PropagationWorker
import spock.lang.Specification

abstract class BaseMockMvcSpec extends Specification {

    MatchSummaryParser summaryParser = new MatchSummaryParser()
    MatchStatisticsParser parser = new MatchStatisticsParser(summaryParser)
    FotbalCzLeagueJsonBuilder builder = new FotbalCzLeagueJsonBuilder()

    def setup() {
        RestAssuredMockMvc.standaloneSetup(new ResultsController(createAndStubPropagationWorker(), parser, builder))
    }

    private PropagationWorker createAndStubPropagationWorker() {
        return Mock(PropagationWorker)
    }
}
