package cz.mikealdo.twitter.place.extractor

import cz.mikealdo.twitter.place.extractor.PlaceExtractor.PlaceResolutionProbability
import cz.mikealdo.twitter.place.extractor.metrics.MatchProbabilityMetrics
import spock.lang.Specification

import static cz.mikealdo.twitter.place.extractor.TweetParser.parseTweet
import static cz.mikealdo.twitter.tweets.Tweets.TWEET_WITHOUT_A_PLACE
import static cz.mikealdo.twitter.tweets.Tweets.TWEET_WITH_PLACE

class PlaceSectionExtractorSpec extends Specification {

    MatchProbabilityMetrics matchProbabilityMetrics = Mock()
    PlaceSectionExtractor placeSectionExtractor = new PlaceSectionExtractor(matchProbabilityMetrics)


    def 'should return high probability of result'() {
        expect:
            placeSectionExtractor.placeResolutionProbability == PlaceResolutionProbability.HIGH
    }

    def 'should return non null name of origin of place resolution'() {
        expect:
            placeSectionExtractor.origin
    }

    def 'should return extracted place from tweet'() {
        given:
            def tweetWithPlace = parseTweet(TWEET_WITH_PLACE)
        when:
            Optional<Place> extractedPlace = placeSectionExtractor.extractPlaceFrom(tweetWithPlace)
        then:
            extractedPlace.present
            extractedPlace.get().placeDetails.countryCode == 'US'
            extractedPlace.get().placeDetails.name == 'Washington'
    }

    def 'should return empty place is place section is missing'() {
        given:
            def tweetWithoutPlace = parseTweet(TWEET_WITHOUT_A_PLACE)
        when:
            Optional<Place> extractedPlace = placeSectionExtractor.extractPlaceFrom(tweetWithoutPlace)
        then:
            !extractedPlace.present
    }

    def 'should update match probability metrics when tweet contains place section'() {
        given:
            def tweetWithPlace = parseTweet(TWEET_WITH_PLACE)
        when:
            placeSectionExtractor.extractPlaceFrom(tweetWithPlace)
        then:
            1 * matchProbabilityMetrics.update(placeSectionExtractor.placeResolutionProbability)
    }

    def 'should not update match probability metrics when place section is missing'() {
        given:
            def tweetWithoutPlace = parseTweet(TWEET_WITHOUT_A_PLACE)
        when:
            placeSectionExtractor.extractPlaceFrom(tweetWithoutPlace)
        then:
            0 * matchProbabilityMetrics.update(_)
    }

}