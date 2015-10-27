package cz.mikealdo.fotbalcz

import org.hamcrest.CoreMatchers
import com.ofg.base.MicroserviceMvcWiremockSpec
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MvcResult

import static java.util.concurrent.TimeUnit.SECONDS
import static org.hamcrest.core.IsNot.not
import static org.hamcrest.text.IsEmptyString.isEmptyString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration//(classes = ColleratorClientStubConfiguration)
class AcceptanceSpec extends MicroserviceMvcWiremockSpec {

//    @Autowired ColleratorClientStub colleratorClientStub
    @Value('${acceptance-tests.timeout:8}') Integer acceptanceTestTimeout

    static final String ROOT_PATH = '/api'
    static final String COMPETITION_HASH = '172c09d6-dd87-47df-a0b3-8efde6ac6842'
    static final MediaType FOTBAL_CZ_API_MICROSERVICE_V1 = new MediaType('application', 'vnd.cz.mikealdo.fotbal-cz-api.v1+json')

    def "should find competition results" () {
        given: 'a competition hash'
            String competitionHash = '172c09d6-dd87-47df-a0b3-8efde6ac6842'
        when: "trying to retrieve results from fotbal.cz"
            MvcResult mvcResult = mockMvc.perform(put("$ROOT_PATH/$COMPETITION_HASH").
                    contentType(FOTBAL_CZ_API_MICROSERVICE_V1)).
                    andExpect(request().asyncStarted()).
                    andReturn();
        and:
            mvcResult.getAsyncResult(SECONDS.toMillis(2))
        and:
            mockMvc.perform(asyncDispatch(mvcResult)).
                    andDo(print()).
                    andExpect(status().isOk()).
                    andExpect(header().string("correlationId", not(isEmptyString())))
        then: "user's location (place) will be extracted from that section"
//            await().atMost(acceptanceTestTimeout, SECONDS).untilAtomic(colleratorClientStub.savedPairId, CoreMatchers.<Long>equalTo(PAIR_ID))
//            await().atMost(acceptanceTestTimeout, SECONDS).untilAtomic(colleratorClientStub.savedPlaces, equalsReferenceJson('''
//                                                                        [{
//                                                                            "pair_id" : 1,
//                                                                            "tweet_id" : "492967299297845248",
//                                                                            "place" :
//                                                                            {
//                                                                                "name":"Washington",
//                                                                                "country_code": "US"
//                                                                            },
//                                                                            "probability" : "2",
//                                                                            "origin" : "twitter_place_section"
//                                                                        }]
//                                                                        '''))
    }
//N
//    def "should find a place by verifying tweet's coordinates"() {
//        given: 'a tweet with a coordinates section filled in'
//            String tweet = TWEET_WITH_COORDINATES
//            stubInteraction(wireMockGet('/?lat=-75.14310264&lon=40.05701649'), aResponse().withBody(CITY_FOUND))
//        when: 'trying to retrieve place from the tweet'
//            MvcResult mvcResult = mockMvc.perform(put("$ROOT_PATH/$PAIR_ID").
//                    contentType(TWITTER_PLACES_ANALYZER_MICROSERVICE_V1).
//                    content("[$tweet]")).
//                    andExpect(request().asyncStarted()).
//                    andReturn();
//        and:
//            mvcResult.getAsyncResult(SECONDS.toMillis(2))
//        and:
//            mockMvc.perform(asyncDispatch(mvcResult)).
//                    andDo(print()).
//                    andExpect(status().isOk()).
//                    andExpect(header().string("correlationId", not(isEmptyString())))
//        then: "user's location (place) will be extracted from that section"
//            await().atMost(acceptanceTestTimeout, SECONDS).untilAtomic(colleratorClientStub.savedPairId, CoreMatchers.<Long>equalTo(PAIR_ID))
//            await().atMost(acceptanceTestTimeout, SECONDS).untilAtomic(colleratorClientStub.savedPlaces, equalsReferenceJson('''
//                                                                            [{
//                                                                                    "pair_id" : 1,
//                                                                                    "tweet_id" : "492961315070439424",
//                                                                                    "place" :
//                                                                                    {
//                                                                                        "name":"Tappahannock",
//                                                                                        "country_code": "US"
//                                                                                    },
//                                                                                    "probability" : "2",
//                                                                                    "origin" : "twitter_coordinates_section"
//                                                                                }]
//                                                                            '''))
//        and:
//            wireMock.verifyThat(getRequestedFor(urlMatching('.*')).withHeader(CORRELATION_ID_HEADER, matching(/^(?!\s*$).+/)))
//    }
//
//    // http://api.openweathermap.org/data/2.5/weather?q=London
//    def "should try to find a place by cross referencing tweet's user mention with a city index if coordinates/places is not available"() {
//        given: 'a tweet with a user mention section filled in (without coordinates)'
//        when: 'trying to retrieve place from the tweet by cross referencing user mention with a city index'
//        then: "user's location (place) will be the matching city"
//    }
//
//    def "should try to find a place by cross referencing tweet's hashtags with a city index if no user mention matches"() {
//        given: 'a tweet with a hashtag section filled in (without coordinates/places and user mention)'
//        when: 'trying to retrieve place from the tweet by cross referencing hash tag with a city index'
//        then: "user's location (place) will be the matching city"
//    }
//
//    def "should try to find a place by cross referencing tweet's content with a city index if no user mention matches"() {
//        given: 'a tweet without geolocation, places, user mention and hashtag sections'
//        when: 'trying to retrieve place from the tweet by cross referencing tweet content with a city index'
//        then: "user's location (place) will be the first matching city"
//    }

}
