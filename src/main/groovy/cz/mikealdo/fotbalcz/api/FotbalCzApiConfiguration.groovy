package cz.mikealdo.fotbalcz.api

import cz.mikealdo.fotbalcz.api.metrics.ExtractorMetricsConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ExtractorMetricsConfiguration)
class FotbalCzApiConfiguration {

    @Bean
    FotbalCzLeagueJsonBuilder fotbalCzLeagueJsonBuilder() {
        return new FotbalCzLeagueJsonBuilder()
    }

    @Bean
    PropagationWorker propagationWorker(FotbalCzLeagueJsonBuilder placesJsonBuilder) {
        return new FotbalCzLeaguePropagatingWorker(placesJsonBuilder)
    }

}
