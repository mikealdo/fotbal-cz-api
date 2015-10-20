package cz.mikealdo.place.extractor

import cz.mikealdo.place.extractor.metrics.ExtractorMetricsConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ExtractorMetricsConfiguration)
class FotbalCzApiConfiguration {

    @Bean
    PlacesJsonBuilder placesJsonBuilder() {
        return new PlacesJsonBuilder()
    }

    @Bean
    PropagationWorker propagationWorker(PlacesJsonBuilder placesJsonBuilder) {
        return new PlacePropagatingWorker(placesJsonBuilder)
    }

}
