package cz.mikealdo.detailedleague;

import cz.mikealdo.football.domain.CompetitionDetails;
import cz.mikealdo.football.domain.League;
import cz.mikealdo.football.domain.Match;
import cz.mikealdo.football.domain.PairedTeam;
import cz.mikealdo.pages.CompetitionPageStub;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class LeagueWithDetailsTest {

    @Test
    public void shouldCreateLeagueWithRealData() throws Exception {
        CompetitionPageStub competitionPageStub = new CompetitionPageStub();
        CompetitionDetails competitionDetails = competitionPageStub.createCompetitionDetailsFrom("hash");

        League league = new LeagueWithDetails(new League("LEAGUE")).enhanceByDetails(competitionDetails);

        assertEquals(league.getTeams().size(), 14);
        assertEquals(league.getMatches().size(), 183);
    }

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
        List<PairedTeam> teams = new LinkedList<>();
        PairedTeam team1 = new PairedTeam(0, "First team");
        teams.add(team1);
        PairedTeam team2 = new PairedTeam(1, "Second team");
        teams.add(team2);
        competitionDetails.setTeams(teams);
        List<Match> matches = new LinkedList<>();
        matches.add(new Match(null, new PairedTeam(2, null), new PairedTeam(3, null), null, 1));
        matches.add(new Match(null, new PairedTeam(3, null), new PairedTeam(2, null), null, 2));
        competitionDetails.setMatches(matches);

        Map<Integer, DateTime> roundDates = new HashMap<>();
        roundDates.put(1, DateTime.parse("20150322"));
        roundDates.put(2, DateTime.parse("20150323"));
        competitionDetails.setRoundDates(roundDates);
        return competitionDetails;
    }
}