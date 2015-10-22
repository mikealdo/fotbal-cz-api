package cz.mikealdo.accurest

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import cz.mikealdo.parser.MatchStatisticsParser
import cz.mikealdo.parser.MatchSummaryParser
import cz.mikealdo.place.ResultsController
import cz.mikealdo.place.extractor.PropagationWorker
import spock.lang.Specification

abstract class BaseMockMvcSpec extends Specification {

    MatchSummaryParser summaryParser = new MatchSummaryParser()
    MatchStatisticsParser parser = new MatchStatisticsParser(summaryParser)

    def setup() {
        RestAssuredMockMvc.standaloneSetup(new ResultsController(createAndStubPropagationWorker(), parser))
    }

    private PropagationWorker createAndStubPropagationWorker() {
        return Mock(PropagationWorker)
    }
}
