package cz.mikealdo.pages;

import cz.mikealdo.football.domain.CompetitionDetails;
import cz.mikealdo.football.domain.Match;
import cz.mikealdo.football.domain.MatchResult;
import cz.mikealdo.football.domain.PairedTeam;
import org.joda.time.DateTime;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MatchesStatisticsPageTest {

    @Mock
    private MatchSummaryPage summaryPage;
    @InjectMocks
	private MatchesStatisticsPage statisticsPage;
    private String html = new HtmlProvider().getMatchStatisticsHTML();

    @Before
	public void setUp() throws Exception {
		when(summaryPage.createMatchResultFor(anyString())).thenReturn(null);
    }

	@Test
	public void shouldAddFreeDrawToTeamList() throws Exception {
		List<Match> matches = new LinkedList<>();
		Match match = new Match();
        match.setHomeTeam(new PairedTeam(1, "first"));
        match.setVisitorTeam(new PairedTeam(3, "third"));
        matches.add(match);

        List<PairedTeam> teams = statisticsPage.retrieveTeams(matches);

        assertEquals(3, teams.size());
        assertEquals(new Integer(2), teams.get(1).getPairingId());
    }

	@Test
	public void shouldAddMultipleFreeDrawsToTeamList() throws Exception {
		List<Match> matches = generateTwoMatches();

        List<PairedTeam> teams = statisticsPage.retrieveTeams(matches);

        assertEquals(6, teams.size());
        assertEquals(new Integer(2), teams.get(1).getPairingId());
        assertEquals(new Integer(5), teams.get(4).getPairingId());
    }

	private List<Match> generateTwoMatches() {
		List<Match> matches = new LinkedList<>();
		Match match = new Match();
        match.setHomeTeam(new PairedTeam(1, "first"));
        match.setVisitorTeam(new PairedTeam(3, "third"));
        match.setDate(new DateTime());
		match.setRound(1);
		matches.add(match);
		Match match2 = new Match();
        match2.setHomeTeam(new PairedTeam(4, "fourth"));
        match2.setVisitorTeam(new PairedTeam(6, "sixth"));
        match2.setDate(new DateTime());
		match2.setRound(2);
		matches.add(match2);
		return matches;
	}

	@Test
	public void shouldFillMatchSummaryWithRelevantData() throws Exception {
		Document document = statisticsPage.getDocumentFor(html);
        when(summaryPage.createMatchResultFor(anyString())).thenReturn(new MatchResult("NOT_VALID"));

		CompetitionDetails competitionDetails = statisticsPage.createCompetitionDetails(document);

        List<PairedTeam> teams = competitionDetails.getTeams();
        assertEquals(14, teams.size());
		assertTeam("AFK Loděnice", 1, teams.get(0));
		assertTeam("FK Dobříč", 2, teams.get(1));
		assertTeam("FK Komárov", 3, teams.get(2));
		assertTeam("TJ Tochovice", 4, teams.get(3));
		assertTeam("TATRAN SEDLČANY B", 5, teams.get(4));
		assertTeam("SK Nový Knín", 6, teams.get(5));
		assertTeam("Petrovice", 7, teams.get(6));
		assertTeam("SK SPARTAK Příbram B", 8, teams.get(7));
		assertTeam("SK Březnice", 9, teams.get(8));
		assertTeam("SOKOL Sedlec-Prčice", 10, teams.get(9));
		assertTeam("UNION CERHOVICE", 11, teams.get(10));
		assertTeam("Spartak TOS Žebrák", 12, teams.get(11));
		assertTeam("TJ Daleké Dušníky", 13, teams.get(12));
		assertTeam("VOLNO - VOLNÝ LOS", 14, teams.get(13));
        Map<Integer, DateTime> roundDates = competitionDetails.getRoundDates();
		assertEquals(26, roundDates.size());
		assertRound(1, "2015-08-22T17:00", roundDates);
		assertRound(2, "2015-08-29T17:00", roundDates);
		assertRound(3, "2015-09-05T17:00", roundDates);
		assertRound(4, "2015-09-12T17:00", roundDates);
		assertRound(5, "2015-09-19T16:30", roundDates);
		assertRound(6, "2015-09-26T16:30", roundDates);
		assertRound(7, "2015-10-03T16:00", roundDates);
		assertRound(8, "2015-10-10T16:00", roundDates);
		assertRound(9, "2015-10-17T15:30", roundDates);
		assertRound(10, "2015-10-24T14:30", roundDates);
		assertRound(11, "2015-10-31T14:00", roundDates);
		assertRound(12, "2015-11-07T14:00", roundDates);
		assertRound(13, "2015-11-14T13:30", roundDates);
		List<Match> matches = competitionDetails.getMatches();
		assertEquals(183, matches.size());
//        assertTrue(matches.contains(new Match(1, 3, 12))); // TODO what does it mean?
        assertMatchWithoutResult(matches.get(0), 1, 14, 1, "2015-08-22T17:00");
		assertMatch(matches.get(1), 2, 13, 1, "2015-08-22T17:00", 1L, 4L);
        assertMatch(matches.get(2), 3, 12, 1, "2015-08-22T10:15", 3L, 0L);
        assertMatchWithoutResult(matches.get(3), 4, 11, 1, "2015-10-28T14:30");
        assertMatch(matches.get(4), 5, 10, 1, "2015-08-23T17:00", 1L, 1L);
        assertMatch(matches.get(5), 6, 9, 1, "2015-08-22T17:00", 2L, 5L);
        assertMatch(matches.get(6), 7, 8, 1, "2015-08-23T17:00", 5L, 1L);
        assertMatch(matches.get(7), 1, 2, 2, "2015-08-29T17:00", 3L, 1L);
        assertMatch(matches.get(8), 7, 9, 2, "2015-08-30T17:00", 3L, 1L);
        assertMatch(matches.get(9), 10, 6, 2, "2015-08-30T17:00", 3L, 0L);
        assertMatch(matches.get(10), 11, 5, 2, "2015-08-30T17:00", 7L, 2L);
        assertMatch(matches.get(11), 12, 4, 2, "2015-08-29T17:00", 0L, 1L);
        assertMatch(matches.get(12), 13, 3, 2, "2015-08-30T17:00", 4L, 1L);
        assertMatchWithoutResult(matches.get(13), 14, 8, 2, "2015-08-30T17:00");
        assertMatchWithoutResult(matches.get(14), 2, 14, 3, "2015-09-05T17:00");
        assertMatch(matches.get(15), 3, 1, 3, "2015-09-05T10:15", 1L, 2L);
        assertMatch(matches.get(16), 4, 13, 3, "2015-09-05T17:00", 6L, 0L);
        assertMatch(matches.get(17), 5, 12, 3, "2015-09-06T17:00", 1L, 2L);
        assertMatch(matches.get(18), 6, 11, 3, "2015-09-05T17:00", 2L, 5L);
        assertMatch(matches.get(19), 7, 10, 3, "2015-09-06T17:00", 0L, 5L);
        assertMatch(matches.get(20), 8, 9, 3, "2015-09-06T17:00", 1L, 3L);
        assertMatchWithoutResult(matches.get(21), 1, 4, 4, "2015-09-12T17:00");
        assertMatchWithoutResult(matches.get(22), 2, 3, 4, "2015-09-12T17:00");
        assertMatchWithoutResult(matches.get(23), 10, 8, 4, "2015-09-13T17:00");
        assertMatchWithoutResult(matches.get(24), 11, 7, 4, "2015-09-13T17:00");
        assertMatch(matches.get(25), 12, 6, 4, "2015-09-12T17:00", 1L, 2L);
        assertMatchWithoutResult(matches.get(26), 13, 5, 4, "2015-09-13T17:00");
        assertMatchWithoutResult(matches.get(27), 14, 9, 4, "2015-09-13T17:00");
		assertMatchWithoutResult(matches.get(28), 3, 14, 5, "2015-09-19T10:15");
		assertMatchWithoutResult(matches.get(29), 4, 2, 5, "2015-09-19T16:30");
        assertMatchWithoutResult(matches.get(30), 5, 1, 5, "2015-09-20T16:30");
        assertMatchWithoutResult(matches.get(31), 6, 13, 5, "2015-09-19T16:30");
        assertMatchWithoutResult(matches.get(32), 7, 12, 5, "2015-09-20T16:30");
        assertMatchWithoutResult(matches.get(33), 8, 11, 5, "2015-09-20T16:30");
        assertMatchWithoutResult(matches.get(34), 9, 10, 5, "2015-09-19T16:30");
        assertMatchWithoutResult(matches.get(35), 1, 6, 6, "2015-09-26T16:30");
        assertMatchWithoutResult(matches.get(36), 2, 5, 6, "2015-09-26T16:30");
        assertMatchWithoutResult(matches.get(37), 3, 4, 6, "2015-09-26T10:15");
        assertMatchWithoutResult(matches.get(38), 11, 9, 6, "2015-09-27T16:30");
        assertMatchWithoutResult(matches.get(39), 12, 8, 6, "2015-09-26T16:30");
        assertMatchWithoutResult(matches.get(40), 13, 7, 6, "2015-09-27T16:30");
        assertMatchWithoutResult(matches.get(41), 14, 10, 6, "2015-09-27T16:30");
		assertMatchWithoutResult(matches.get(42), 4, 14, 7, "2015-10-03T16:00");
        assertMatchWithoutResult(matches.get(43), 5, 3, 7, "2015-10-04T16:00");
        assertMatchWithoutResult(matches.get(44), 6, 2, 7, "2015-10-03T16:00");
        assertMatchWithoutResult(matches.get(45), 7, 1, 7, "2015-10-04T16:00");
        assertMatchWithoutResult(matches.get(46), 8, 13, 7, "2015-10-04T16:00");
        assertMatchWithoutResult(matches.get(47), 9, 12, 7, "2015-10-03T16:00");
        assertMatchWithoutResult(matches.get(48), 10, 11, 7, "2015-10-04T16:00");
        assertMatchWithoutResult(matches.get(49), 1, 8, 8, "2015-10-10T16:00");
        assertMatchWithoutResult(matches.get(50), 2, 7, 8, "2015-10-10T16:00");
        assertMatchWithoutResult(matches.get(51), 3, 6, 8, "2015-10-10T10:15");
        assertMatchWithoutResult(matches.get(52), 4, 5, 8, "2015-10-10T16:00");
		assertMatchWithoutResult(matches.get(53), 12, 10, 8, "2015-10-10T16:00");
		assertMatchWithoutResult(matches.get(54), 13, 9, 8, "2015-10-11T16:00");
		assertMatchWithoutResult(matches.get(55), 14, 11, 8, "2015-10-11T16:00");
        assertMatchWithoutResult(matches.get(56), 5, 14, 9, "2015-10-18T15:30");
        assertMatchWithoutResult(matches.get(57), 6, 4, 9, "2015-10-17T15:30");
        assertMatchWithoutResult(matches.get(58), 7, 3, 9, "2015-10-18T15:30");
        assertMatchWithoutResult(matches.get(59), 8, 2, 9, "2015-10-18T15:30");
        assertMatchWithoutResult(matches.get(60), 9, 1, 9, "2015-10-17T15:30");
        assertMatchWithoutResult(matches.get(61), 10, 13, 9, "2015-10-18T15:30");
        assertMatchWithoutResult(matches.get(62), 11, 12, 9, "2015-10-18T15:30");
        assertMatchWithoutResult(matches.get(63), 1, 10, 10, "2015-10-24T14:30");
        assertMatchWithoutResult(matches.get(64), 2, 9, 10, "2015-10-24T14:30");
        assertMatchWithoutResult(matches.get(65), 3, 8, 10, "2015-10-24T10:15");
        assertMatchWithoutResult(matches.get(66), 4, 7, 10, "2015-10-24T14:30");
		assertMatchWithoutResult(matches.get(67), 5, 6, 10, "2015-10-25T14:30");
        assertMatchWithoutResult(matches.get(68), 13, 11, 10, "2015-10-25T14:30");
        assertMatchWithoutResult(matches.get(69), 14, 12, 10, "2015-10-25T14:30");
		assertMatchWithoutResult(matches.get(70), 6, 14, 11, "2015-10-31T14:00");
        assertMatchWithoutResult(matches.get(71), 7, 5, 11, "2015-11-01T14:00");
        assertMatchWithoutResult(matches.get(72), 8, 4, 11, "2015-11-01T14:00");
        assertMatchWithoutResult(matches.get(73), 9, 3, 11, "2015-10-31T14:00");
        assertMatchWithoutResult(matches.get(74), 10, 2, 11, "2015-11-01T14:00");
        assertMatchWithoutResult(matches.get(75), 11, 1, 11, "2015-11-01T10:30");
        assertMatchWithoutResult(matches.get(76), 12, 13, 11, "2015-10-31T14:00");
        assertMatchWithoutResult(matches.get(77), 1, 12, 12, "2015-11-07T14:00");
        assertMatchWithoutResult(matches.get(78), 2, 11, 12, "2015-11-07T14:00");
        assertMatchWithoutResult(matches.get(79), 3, 10, 12, "2015-11-07T10:15");
        assertMatchWithoutResult(matches.get(80), 4, 9, 12, "2015-11-07T14:00");
        assertMatchWithoutResult(matches.get(81), 5, 8, 12, "2015-11-08T14:00");
        assertMatchWithoutResult(matches.get(82), 6, 7, 12, "2015-11-07T14:00");
        assertMatchWithoutResult(matches.get(83), 14, 13, 12, "2015-11-08T14:00");
        assertMatchWithoutResult(matches.get(84), 7, 14, 13, "2015-11-15T13:30");
        assertMatchWithoutResult(matches.get(85), 8, 6, 13, "2015-11-15T13:30");
        assertMatchWithoutResult(matches.get(86), 9, 5, 13, "2015-11-14T13:30");
        assertMatchWithoutResult(matches.get(87), 10, 4, 13, "2015-11-14T13:30");
        assertMatchWithoutResult(matches.get(88), 11, 3, 13, "2015-11-15T13:30");
        assertMatchWithoutResult(matches.get(89), 12, 2, 13, "2015-11-14T13:30");
		assertMatchWithoutResult(matches.get(90), 13, 1, 13, "2015-11-21T13:00");
	}

	private void assertMatchWithoutResult(Match match, Integer homePairingId, Integer visitorPairingId, Integer round, String date) {
		assertCommonMatchDetails(match, homePairingId, visitorPairingId, round, date);
	}

	private void assertMatch(Match match, Integer homePairingId, Integer visitorPairingId, Integer round, String date, Long homeGoals, Long visitorGoals) {
		assertCommonMatchDetails(match, homePairingId, visitorPairingId, round, date);
        Optional<MatchResult> result = match.getResult();
        assertEquals(homeGoals, result.get().getHomeGoals());
        assertEquals(visitorGoals, result.get().getVisitorGoals());
    }

	private void assertCommonMatchDetails(Match match, Integer homePairingId, Integer visitorPairingId, Integer round, String date) {
		assertEquals(homePairingId, match.getHomeTeam().getPairingId());
		assertEquals(visitorPairingId, match.getVisitorTeam().getPairingId());
		assertEquals(round, match.getRound());
		assertEquals(DateTime.parse(date), match.getDate());
	}

	private void assertRound(Integer expectedRound, String expectedDate, Map<Integer, DateTime> roundDates) {
		assertEquals(DateTime.parse(expectedDate), roundDates.get(expectedRound));
	}

    private void assertTeam(String expectedName, Integer expectedPairingId, PairedTeam team) {
        assertEquals(expectedName, team.getPairingTeamName());
        assertEquals(expectedPairingId, team.getPairingId());
    }
}