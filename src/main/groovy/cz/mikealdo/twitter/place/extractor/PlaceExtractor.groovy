package cz.mikealdo.twitter.place.extractor

import cz.mikealdo.twitter.place.model.Tweet
import groovy.transform.PackageScope

@PackageScope
interface PlaceExtractor {

    Optional<Place> extractPlaceFrom(Tweet parsedTweet)

    String getOrigin()

    PlaceResolutionProbability getPlaceResolutionProbability()

    enum PlaceResolutionProbability {
        LOW, MEDIUM, HIGH
    }

}