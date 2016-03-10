package cz.mikealdo.pages;

import cz.mikealdo.football.domain.CompetitionDetails;
import org.mockito.Mockito;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompetitionPageStub extends CompetitionPage {

    @Override
    public CompetitionDetails createCompetitionDetailsFrom(String competitionHash) {
        String html = new HtmlProvider().getMatchStatisticsHTML();;
        MatchesStatisticsPage matchesStatisticsPage = new MatchesStatisticsPage(Mockito.mock(MatchSummaryPage.class));
        return matchesStatisticsPage.createCompetitionDetails(matchesStatisticsPage.getDocumentFor(html));
    }
}
