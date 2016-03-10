package cz.mikealdo.fotbalcz.api

import cz.mikealdo.fotbalcz.api.metrics.ExtractorMetricsConfiguration
import cz.mikealdo.fotbalcz.results.ResultsStorageClient
import cz.mikealdo.pages.CompetitionPage
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
    ResultsStorageClient resultsStorageClient() {
        return new ResultsStorageClient()
    }

    @Bean
    CompetitionPage competitionPage() {
        return new CompetitionPage()
    }

    @Bean
    PropagationWorker propagationWorker(ResultsStorageClient resultsStorageClient) {
        return new FotbalCzLeaguePropagatingWorker(resultsStorageClient)
    }

}
