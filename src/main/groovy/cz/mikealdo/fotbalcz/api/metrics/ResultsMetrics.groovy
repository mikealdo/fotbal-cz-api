package cz.mikealdo.fotbalcz.api.metrics

import com.codahale.metrics.Meter
import com.codahale.metrics.MetricRegistry

class ResultsMetrics {

    private Meter resultsMeters

    ResultsMetrics(MetricRegistry metricRegistry) {
        registerProbabilityMetrics(metricRegistry)
    }

    void update() {
        resultsMeters.mark()
    }

    private void registerProbabilityMetrics(MetricRegistry metricRegistry) {
        resultsMeters = metricRegistry.meter("fotbal-cz-api.results.hit")
    }
}
