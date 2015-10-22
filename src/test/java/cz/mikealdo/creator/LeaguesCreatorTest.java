package cz.mikealdo.creator;

import cz.mikealdo.football.domain.RoundDate;
import cz.mikealdo.fotbalcz.domain.CompetitionDetails;
import cz.mikealdo.fotbalcz.domain.FotbalCzLeague;
import cz.mikealdo.fotbalcz.domain.FotbalCzMatch;
import cz.mikealdo.fotbalcz.domain.FotbalCzTeam;
import cz.mikealdo.parser.HtmlProvider;
import cz.mikealdo.parser.MatchStatisticsParser;
import cz.mikealdo.parser.MatchSummaryParser;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.matches;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LeaguesCreatorTest {

    @Mock
    private MatchSummaryParser summaryParser;
    @Mock
    private MatchStatisticsParser statisticsParser;
    @InjectMocks
    private LeaguesCreator creator;

    @Before
    public void setUp() throws Exception {
        CompetitionDetails competitionDetails = createCompetitionDetails();
        when(statisticsParser.createCompetitionDetailsFrom(anyString())).thenReturn(competitionDetails);
    }

    private CompetitionDetails createCompetitionDetails() {
        CompetitionDetails competitionDetails = new CompetitionDetails();
        competitionDetails.setCompetitionName("Name");
        List<FotbalCzTeam> teams = new LinkedList<>();
        FotbalCzTeam team1 = new FotbalCzTeam(0, "First team");
        teams.add(team1);
        FotbalCzTeam team2 = new FotbalCzTeam(1, "Second team");
        teams.add(team2);
        competitionDetails.setTeams(teams);
        List<FotbalCzMatch> matches = new LinkedList<>();
        matches.add(new FotbalCzMatch());
        matches.add(new FotbalCzMatch());
        competitionDetails.setMatches(matches);
        List<RoundDate> roundDates = new LinkedList<>();
        RoundDate roundDate = new RoundDate(1, DateTime.parse("20150322"));
        roundDates.add(roundDate);
        RoundDate roundDate2 = new RoundDate(2, DateTime.parse("20150323"));
        roundDates.add(roundDate2);
        competitionDetails.setRoundDates(roundDates);
        return competitionDetails;
    }

    @Test
    public void shouldCreateLeagueWithPopulatedFields() throws Exception {
        FotbalCzLeague league = creator.createLeague("hash");

        assertEquals(league.getTeams().size(), 2);
        assertEquals(league.getMatches().size(), 2);
        assertEquals(league.getMatches().get(0).getRound(), Integer.valueOf(1));
        assertEquals(league.getMatches().get(0).getDate(), DateTime.parse("20150322"));
        assertEquals(league.getMatches().get(1).getRound(), Integer.valueOf(2));
        assertEquals(league.getMatches().get(1).getDate(), DateTime.parse("20150323"));
    }
}