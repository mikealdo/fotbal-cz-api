package cz.mikealdo.accurest

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import cz.mikealdo.place.ResultsController
import cz.mikealdo.place.extractor.PropagationWorker
import spock.lang.Specification

abstract class BaseMockMvcSpec extends Specification {

    def setup() {
        RestAssuredMockMvc.standaloneSetup(new ResultsController(createAndStubPropagationWorker()))
    }

    private PropagationWorker createAndStubPropagationWorker() {
        return Mock(PropagationWorker)
    }
}
