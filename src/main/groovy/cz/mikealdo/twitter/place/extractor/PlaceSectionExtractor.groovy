package cz.mikealdo.twitter.place.extractor

import cz.mikealdo.twitter.place.extractor.Place.PlaceDetails
import cz.mikealdo.twitter.place.extractor.PlaceExtractor.PlaceResolutionProbability
import cz.mikealdo.twitter.place.extractor.metrics.MatchProbabilityMetrics
import cz.mikealdo.twitter.place.model.Tweet
import groovy.transform.PackageScope

@PackageScope
class PlaceSectionExtractor implements PlaceExtractor {

    public static final String PLACE_EXTRACTION_NAME = 'twitter_place_section'

    private final MatchProbabilityMetrics metrics

    PlaceSectionExtractor(MatchProbabilityMetrics matchProbabilityMetrics) {
        metrics = matchProbabilityMetrics
    }

    @Override
    Optional<Place> extractPlaceFrom(Tweet parsedTweet) {
        if(parsedTweet.place == null) {
            return Optional.empty()
        } else {
            metrics.update(placeResolutionProbability)
            return extractFromParsedPlace(parsedTweet.place)
        }
    }

    private Optional<Place> extractFromParsedPlace(place) {
        PlaceDetails placeDetails = new PlaceDetails(place.name, place.country_code)
        return Optional.of(new Place(placeDetails, origin, placeResolutionProbability))
    }

    @Override
    String getOrigin() {
        return PLACE_EXTRACTION_NAME
    }

    @Override
    PlaceResolutionProbability getPlaceResolutionProbability() {
        return PlaceResolutionProbability.HIGH
    }
}
