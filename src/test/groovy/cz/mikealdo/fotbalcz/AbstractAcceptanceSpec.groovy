package cz.mikealdo.fotbalcz

import cz.mikealdo.base.MicroserviceMvcWiremockSpec
import cz.mikealdo.pages.CompetitionPage
import cz.mikealdo.pages.CompetitionPageStub
import org.hamcrest.CoreMatchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult

import static com.jayway.awaitility.Awaitility.await
import static com.ofg.infrastructure.base.dsl.Matchers.equalsReferenceJson
import static java.util.concurrent.TimeUnit.SECONDS
import static org.hamcrest.core.IsNot.not
import static org.hamcrest.text.IsEmptyString.isEmptyString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

abstract class AbstractAcceptanceSpec extends MicroserviceMvcWiremockSpec {

    static final String ROOT_PATH = '/api'
    static final String COMPETITION_HASH = '172c09d6-dd87-47df-a0b3-8efde6ac6842'
    static final MediaType FOTBAL_CZ_API_MICROSERVICE_V1 = new MediaType('application', 'vnd.cz.mikealdo.fotbal-cz-api.v1+json')

    @Autowired ResultsStorageClientStub resultsStorageClientStub
    @Autowired CompetitionPageStub competitionPage
    @Value('${acceptance-tests.timeout:5}') Integer acceptanceTestTimeout

    def "should find competition results" () {
        given: 'a competition hash'
        when: "trying to retrieve results from fotbal.cz"
            MvcResult mvcResult = mockMvc.perform(get("$ROOT_PATH/$COMPETITION_HASH")
                    .contentType(FOTBAL_CZ_API_MICROSERVICE_V1))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(header().string("correlationId", not(isEmptyString())))
                    .andReturn()
        then:
            await().atMost(acceptanceTestTimeout, SECONDS).untilAtomic(resultsStorageClientStub.savedResultHash, CoreMatchers.<String>equalTo(COMPETITION_HASH))
            await().atMost(acceptanceTestTimeout, SECONDS).untilAtomic(resultsStorageClientStub.savedJson, equalsReferenceJson('''
                                                                        [{
                                                                            JSON_HERE
                                                                        }]
                                                                        '''))

    }

}
