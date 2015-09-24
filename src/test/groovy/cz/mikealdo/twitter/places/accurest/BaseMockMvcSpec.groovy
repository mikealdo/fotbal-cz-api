package cz.mikealdo.twitter.places.accurest

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import cz.mikealdo.twitter.place.PairIdController
import cz.mikealdo.twitter.place.extractor.PropagationWorker
import spock.lang.Specification

abstract class BaseMockMvcSpec extends Specification {

    def setup() {
        RestAssuredMockMvc.standaloneSetup(new PairIdController(createAndStubPropagationWorker()))
    }

    private PropagationWorker createAndStubPropagationWorker() {
        return Mock(PropagationWorker)
    }
}
