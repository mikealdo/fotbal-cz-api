package cz.mikealdo.pages;

import cz.mikealdo.football.domain.CompetitionDetails;
import org.springframework.stereotype.Component;

@Component
public class CompetitionPage {

    public CompetitionDetails createCompetitionDetailsFrom(String competitionHash) {
        return new MatchesStatisticsPage(new MatchSummaryPage()).createCompetitionDetailsFrom(competitionHash);
    }
}
