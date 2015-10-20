package cz.mikealdo.place.extractor.metrics

import com.codahale.metrics.Meter
import com.codahale.metrics.MetricRegistry

class MatchProbabilityMetrics {

    private final Map<String, Meter> probabilityMeters = [:]

    MatchProbabilityMetrics(MetricRegistry metricRegistry) {
        registerProbabilityMetrics(metricRegistry)
    }

    void update(String probability) {
        probabilityMeters[probability].mark()
    }

    private void registerProbabilityMetrics(MetricRegistry metricRegistry) {
//        PlaceResolutionProbability.values().each { probability ->
//            probabilityMeters[probability] = metricRegistry.meter("twitter.places.analyzed.probability.$probability")
//        }
    }
}
