package cz.mikealdo.fotbalcz.generatejson

import cz.mikealdo.base.MicroserviceMvcWiremockSpec
import cz.mikealdo.fotbalcz.ResultsStorageClientStub
import cz.mikealdo.fotbalcz.ResultsStorageClientStubWithFotbalCzConfiguration
import cz.mikealdo.pages.CompetitionPage
import org.hamcrest.CoreMatchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration

import java.util.concurrent.atomic.AtomicReference

import static com.jayway.awaitility.Awaitility.await
import static java.util.concurrent.TimeUnit.SECONDS
import static org.hamcrest.core.IsNot.not
import static org.hamcrest.text.IsEmptyString.isEmptyString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [ResultsStorageClientStubWithFotbalCzConfiguration.class])
class RetrieveJsonForGivenTeamsSpec extends MicroserviceMvcWiremockSpec {

    static final String ROOT_PATH = '/api'
    static final MediaType FOTBAL_CZ_API_MICROSERVICE_V1 = new MediaType('application', 'vnd.fotbal-cz-api.v1+json')
    final List<String> hashes = new ArrayList<String>()

    @Autowired
    ResultsStorageClientStub resultsStorageClientStub
    @Autowired
    CompetitionPage competitionPage
    @Value('${acceptance-tests.timeout:50}')
    Integer acceptanceTestTimeout

    def "A"() {
        given:
            def hash = "3da83071-b450-4852-b349-201990550e50"
        when:
            retrieveAndSaveResultsFor(hash)
        then:
            println "Success"
    }

    def "B"() {
        given:
        def hash = "383c2264-5b8c-4bc6-ad28-5b9d19e3a140"
        when:
        retrieveAndSaveResultsFor(hash)
        then:
        println "Success"
    }

    def "Dorost"() {
        given:
        def hash = "30756af4-4430-465a-b16f-a86ad324fc08"
        when:
        retrieveAndSaveResultsFor(hash)
        then:
        println "Success"
    }

    def "Starsi zaci"() {
        given:
        def hash = "9af3d9a6-3b34-4bdf-aaa8-cab40427a304"
        when:
        retrieveAndSaveResultsFor(hash)
        then:
        println "Success"
    }

    def "Mladsi zaci"() {
        given:
        def hash = "c51b6839-c026-4d21-bc03-a06d07353de8"
        when:
        retrieveAndSaveResultsFor(hash)
        then:
        println "Success"
    }

    def "Pripravka"() {
        given:
        def hash = "298cc6b2-e256-4d8c-aa79-b2f959b32c90"
        when:
        retrieveAndSaveResultsFor(hash)
        then:
        println "Success"
    }

    private void retrieveAndSaveResultsFor(String hash) {
        mockMvc.perform(get("$ROOT_PATH/$hash")
                .contentType(FOTBAL_CZ_API_MICROSERVICE_V1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("correlationId", not(isEmptyString())))
                .andReturn()

        await().atMost(25, SECONDS).untilAtomic(resultsStorageClientStub.savedJson, CoreMatchers.<String> notNullValue())

        def file = new File('result-jsons/' + hash + '.json')
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(resultsStorageClientStub.savedJson.get());
        writer.close();
        resultsStorageClientStub.savedJson = new AtomicReference<>()
        resultsStorageClientStub.savedResultHash = new AtomicReference<>()
    }

}
