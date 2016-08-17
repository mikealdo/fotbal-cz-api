package cz.mikealdo.fotbalcz.generatejson

import cz.mikealdo.base.MicroserviceMvcWiremockSpec
import cz.mikealdo.fotbalcz.ResultsStorageClientStub
import cz.mikealdo.pages.CompetitionPage
import org.hamcrest.CoreMatchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import spock.lang.Ignore

import static com.jayway.awaitility.Awaitility.await
import static java.util.concurrent.TimeUnit.SECONDS
import static org.hamcrest.core.IsNot.not
import static org.hamcrest.text.IsEmptyString.isEmptyString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/***
 * Neccessary to update ResultsStorageClientStubConfiguration with real CompetitionPage
 */
class RetrieveJsonForGivenTeamsSpec extends MicroserviceMvcWiremockSpec {

    static final String ROOT_PATH = '/api'
    static final MediaType FOTBAL_CZ_API_MICROSERVICE_V1 = new MediaType('application', 'vnd.cz.mikealdo.fotbal-cz-api.v1+json')
    final List<String> hashes = new ArrayList<String>()

    @Autowired
    ResultsStorageClientStub resultsStorageClientStub
    @Autowired
    CompetitionPage competitionPage
    @Value('${acceptance-tests.timeout:10}')
    Integer acceptanceTestTimeout

    @Ignore
    def "should find competition results"() {
        given:
        hashes.add('172c09d6-dd87-47df-a0b3-8efde6ac6842')
        when:
        for (String hash : hashes) {
            MvcResult mvcResult = mockMvc.perform(get("$ROOT_PATH/$hash")
                    .contentType(FOTBAL_CZ_API_MICROSERVICE_V1))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(header().string("correlationId", not(isEmptyString())))
                    .andReturn()

            await().atMost(10, SECONDS).untilAtomic(resultsStorageClientStub.savedJson, CoreMatchers.<String> notNullValue())

            def file = new File('result-jsons/' + hash + '.json')
            file.write(resultsStorageClientStub.savedJson.get())
        }
        then:
        println "Success"
    }

}
