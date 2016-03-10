package cz.mikealdo.creator;

import cz.mikealdo.football.domain.League;
import cz.mikealdo.football.domain.Match;
import cz.mikealdo.football.domain.RoundDate;
import cz.mikealdo.football.domain.Team;
import cz.mikealdo.football.domain.CompetitionDetails;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class LeagueWithDetailsTest {

    @Test
    public void shouldCreateLeagueWithPopulatedFields() throws Exception {
        League league = new LeagueWithDetails(new League("LEAGUE")).enhanceByDetails(createCompetitionDetails());

        assertEquals(league.getTeams().size(), 2);
        assertEquals(league.getMatches().size(), 2);
        assertEquals(league.getMatches().get(0).getRound(), Integer.valueOf(1));
        assertEquals(league.getMatches().get(0).getDate(), DateTime.parse("20150322"));
        assertEquals(league.getMatches().get(1).getRound(), Integer.valueOf(2));
        assertEquals(league.getMatches().get(1).getDate(), DateTime.parse("20150323"));
    }

    private CompetitionDetails createCompetitionDetails() {
        CompetitionDetails competitionDetails = new CompetitionDetails();
        competitionDetails.setCompetitionName("Name");
        List<Team> teams = new LinkedList<>();
        Team team1 = new Team(0, "First team");
        teams.add(team1);
        Team team2 = new Team(1, "Second team");
        teams.add(team2);
        competitionDetails.setTeams(teams);
        List<Match> matches = new LinkedList<>();
        matches.add(new Match());
        matches.add(new Match());
        competitionDetails.setMatches(matches);
        List<RoundDate> roundDates = new LinkedList<>();
        RoundDate roundDate = new RoundDate(1, DateTime.parse("20150322"));
        roundDates.add(roundDate);
        RoundDate roundDate2 = new RoundDate(2, DateTime.parse("20150323"));
        roundDates.add(roundDate2);
        competitionDetails.setRoundDates(roundDates);
        return competitionDetails;
    }
}