package cz.mikealdo.place.extractor

import cz.mikealdo.place.extractor.metrics.ExtractorMetricsConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ExtractorMetricsConfiguration)
class FotbalCzApiConfiguration {

    @Bean
    FotbalCzLeagueJsonBuilder placesJsonBuilder() {
        return new FotbalCzLeagueJsonBuilder()
    }

    @Bean
    PropagationWorker propagationWorker(FotbalCzLeagueJsonBuilder placesJsonBuilder) {
        return new PlacePropagatingWorker(placesJsonBuilder)
    }

}
