package cz.mikealdo.parser;

import static junit.framework.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import cz.mikealdo.football.domain.Arrival;
import cz.mikealdo.fotbalcz.domain.CompetitionDetails;
import cz.mikealdo.fotbalcz.domain.FotbalCzMatch;
import cz.mikealdo.fotbalcz.domain.FotbalCzTeam;
import cz.mikealdo.football.domain.MatchResult;
import cz.mikealdo.fotbalcz.domain.PairingBasis;
import cz.mikealdo.football.domain.RoundDate;

public class MatchStatisticsParserTest {

	MatchStatisticsParser parser;

	private String html;

	@Before
	public void setUp() throws Exception {
		parser = new MatchStatisticsParser() {
			@Override
			protected MatchResult retrieveDetailedMatchResult(NodeList cells) {
				// we don't want to load match result in test		
				return null;
			}
		};
		html = IOUtils.toString(
				this.getClass().getResource("/html/match-statistics.html"),
				"UTF-8"
		);
	}

	@Test
	public void shouldAddFreeDrawToTeamList() throws Exception {
		List<FotbalCzMatch> matches = new LinkedList<>();
		FotbalCzMatch match = new FotbalCzMatch();
		match.setHomeTeam(new FotbalCzTeam(1, "first"));
		match.setVisitorTeam(new FotbalCzTeam(3, "third"));
		matches.add(match);

		List<FotbalCzTeam> fotbalCzTeams = parser.retrieveTeams(matches);

		assertEquals(3, fotbalCzTeams.size());
		assertEquals(new Integer(2), fotbalCzTeams.get(1).getPairingId());
	}

	@Test
	public void shouldAddMultipleFreeDrawsToTeamList() throws Exception {
		List<FotbalCzMatch> matches = generateTwoMatches();

		List<FotbalCzTeam> fotbalCzTeams = parser.retrieveTeams(matches);

		assertEquals(6, fotbalCzTeams.size());
		assertEquals(new Integer(2), fotbalCzTeams.get(1).getPairingId());
		assertEquals(new Integer(5), fotbalCzTeams.get(4).getPairingId());
	}

	private List<FotbalCzMatch> generateTwoMatches() {
		List<FotbalCzMatch> matches = new LinkedList<>();
		FotbalCzMatch match = new FotbalCzMatch();
		match.setHomeTeam(new FotbalCzTeam(1, "first"));
		match.setVisitorTeam(new FotbalCzTeam(3, "third"));
		match.setDate(new DateTime());
		match.setRound(1);
		matches.add(match);
		FotbalCzMatch match2 = new FotbalCzMatch();
		match2.setHomeTeam(new FotbalCzTeam(4, "fourth"));
		match2.setVisitorTeam(new FotbalCzTeam(6, "sixth"));
		match2.setDate(new DateTime());
		match2.setRound(2);
		matches.add(match2);
		return matches;
	}

	@Test
	public void shouldFillMatchSummaryWithRelevantData() throws Exception {
		Document document = parser.getDOMDocument(html);
		PairingBasis pairingBasis = new PairingBasis();
		pairingBasis.setPairingTeamName("SOKOL Sedlec-Prčice");
		List<Arrival> arrivals = new LinkedList<>();
		pairingBasis.setArrivals(arrivals);

		CompetitionDetails competitionDetails = parser.createCompetitionDetails(document);

		List<FotbalCzTeam> teams = competitionDetails.getTeams();
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
		assertTeam("FK HOŘOVICKO B", 14, teams.get(13));
		List<RoundDate> roundDates = competitionDetails.getRoundDates();
		assertEquals(13, roundDates.size());
		assertRound(1, "2015-08-22T17:00", roundDates.get(0));
		assertRound(2, "2015-08-29T17:00", roundDates.get(1));
		assertRound(3, "2015-09-05T17:00", roundDates.get(2));
		assertRound(4, "2015-09-12T17:00", roundDates.get(3));
		assertRound(5, "2015-09-19T16:30", roundDates.get(4));
		assertRound(6, "2015-09-26T16:30", roundDates.get(5));
		assertRound(7, "2015-10-03T16:00", roundDates.get(6));
		assertRound(8, "2015-10-10T16:00", roundDates.get(7));
		assertRound(9, "2015-10-17T15:30", roundDates.get(8));
		assertRound(10, "2015-10-24T14:30", roundDates.get(9));
		assertRound(11, "2015-10-31T14:00", roundDates.get(10));
		assertRound(12, "2015-11-07T14:00", roundDates.get(11));
		assertRound(13, "2015-11-14T13:30", roundDates.get(12));
		List<FotbalCzMatch> matches = competitionDetails.getMatches();
		assertEquals(91, matches.size());
		assertMatch(matches.get(0), 3, 12, 1, "2015-08-22T10:15", 3L, 0L);
		assertMatchWithoutResult(matches.get(1), 1, 14, 1, "2015-08-22T17:00");
		assertMatch(matches.get(2), 2, 13, 1, "2015-08-22T17:00", 1L, 4L);
		assertMatch(matches.get(3), 6, 9, 1, "2015-08-22T17:00", 2L, 5L);
		assertMatch(matches.get(4), 7, 8, 1, "2015-08-23T17:00", 5L, 1L);
		assertMatch(matches.get(5), 5, 10, 1, "2015-08-23T17:00", 1L, 1L);
		assertMatchWithoutResult(matches.get(6), 4, 11, 1, "2015-10-28T14:30");
		assertMatch(matches.get(7), 12, 4, 2, "2015-08-29T17:00", 0L, 1L);
		assertMatch(matches.get(8), 1, 2, 2, "2015-08-29T17:00", 3L, 1L);
		assertMatch(matches.get(9), 13, 3, 2, "2015-08-30T17:00", 4L, 1L);
		assertMatchWithoutResult(matches.get(10), 14, 8, 2, "2015-08-30T17:00");
		assertMatch(matches.get(11), 7, 9, 2, "2015-08-30T17:00", 3L, 1L);
		assertMatch(matches.get(12), 10, 6, 2, "2015-08-30T17:00", 3L, 0L);
		assertMatch(matches.get(13), 11, 5, 2, "2015-08-30T17:00", 7L, 2L);
		assertMatch(matches.get(14), 3, 1, 3, "2015-09-05T10:15", 1L, 2L);
		assertMatch(matches.get(15), 4, 13, 3, "2015-09-05T17:00", 6L, 0L);
		assertMatchWithoutResult(matches.get(16), 2, 14, 3, "2015-09-05T17:00");
		assertMatch(matches.get(17), 6, 11, 3, "2015-09-05T17:00", 2L, 5L);
		assertMatch(matches.get(18), 7, 10, 3, "2015-09-06T17:00", 0L, 5L);
		assertMatch(matches.get(19), 8, 9, 3, "2015-09-06T17:00", 1L, 3L);
		assertMatch(matches.get(20), 5, 12, 3, "2015-09-06T17:00", 1L, 2L);
		assertMatchWithoutResult(matches.get(21), 12, 6, 4, "2015-09-12T17:00");
		assertMatchWithoutResult(matches.get(22), 1, 4, 4, "2015-09-12T17:00");
		assertMatchWithoutResult(matches.get(23), 2, 3, 4, "2015-09-12T17:00");
		assertMatchWithoutResult(matches.get(24), 13, 5, 4, "2015-09-13T17:00");
		assertMatchWithoutResult(matches.get(25), 14, 9, 4, "2015-09-13T17:00");
		assertMatchWithoutResult(matches.get(26), 10, 8, 4, "2015-09-13T17:00");
		assertMatchWithoutResult(matches.get(27), 11, 7, 4, "2015-09-13T17:00");
		assertMatchWithoutResult(matches.get(28), 3, 14, 5, "2015-09-19T10:15");
		assertMatchWithoutResult(matches.get(29), 4, 2, 5, "2015-09-19T16:30");
		assertMatchWithoutResult(matches.get(30), 6, 13, 5, "2015-09-19T16:30");
		assertMatchWithoutResult(matches.get(31), 9, 10, 5, "2015-09-19T16:30");
		assertMatchWithoutResult(matches.get(32), 7, 12, 5, "2015-09-20T16:30");
		assertMatchWithoutResult(matches.get(33), 8, 11, 5, "2015-09-20T16:30");
		assertMatchWithoutResult(matches.get(34), 5, 1, 5, "2015-09-20T16:30");
		assertMatchWithoutResult(matches.get(35), 3, 4, 6, "2015-09-26T10:15");
		assertMatchWithoutResult(matches.get(36), 12, 8, 6, "2015-09-26T16:30");
		assertMatchWithoutResult(matches.get(37), 1, 6, 6, "2015-09-26T16:30");
		assertMatchWithoutResult(matches.get(38), 2, 5, 6, "2015-09-26T16:30");
		assertMatchWithoutResult(matches.get(39), 13, 7, 6, "2015-09-27T16:30");
		assertMatchWithoutResult(matches.get(40), 14, 10, 6, "2015-09-27T16:30");
		assertMatchWithoutResult(matches.get(41), 11, 9, 6, "2015-09-27T16:30");
		assertMatchWithoutResult(matches.get(42), 4, 14, 7, "2015-10-03T16:00");
		assertMatchWithoutResult(matches.get(43), 6, 2, 7, "2015-10-03T16:00");
		assertMatchWithoutResult(matches.get(44), 9, 12, 7, "2015-10-03T16:00");
		assertMatchWithoutResult(matches.get(45), 10, 11, 7, "2015-10-04T16:00");
		assertMatchWithoutResult(matches.get(46), 7, 1, 7, "2015-10-04T16:00");
		assertMatchWithoutResult(matches.get(47), 8, 13, 7, "2015-10-04T16:00");
		assertMatchWithoutResult(matches.get(48), 5, 3, 7, "2015-10-04T16:00");
		assertMatchWithoutResult(matches.get(49), 3, 6, 8, "2015-10-10T10:15");
		assertMatchWithoutResult(matches.get(50), 4, 5, 8, "2015-10-10T16:00");
		assertMatchWithoutResult(matches.get(51), 1, 8, 8, "2015-10-10T16:00");
		assertMatchWithoutResult(matches.get(52), 2, 7, 8, "2015-10-10T16:00");
		assertMatchWithoutResult(matches.get(53), 12, 10, 8, "2015-10-10T16:00");
		assertMatchWithoutResult(matches.get(54), 13, 9, 8, "2015-10-11T16:00");
		assertMatchWithoutResult(matches.get(55), 14, 11, 8, "2015-10-11T16:00");
		assertMatchWithoutResult(matches.get(56), 9, 1, 9, "2015-10-17T15:30");
		assertMatchWithoutResult(matches.get(57), 6, 4, 9, "2015-10-17T15:30");
		assertMatchWithoutResult(matches.get(58), 7, 3, 9, "2015-10-18T15:30");
		assertMatchWithoutResult(matches.get(59), 8, 2, 9, "2015-10-18T15:30");
		assertMatchWithoutResult(matches.get(60), 10, 13, 9, "2015-10-18T15:30");
		assertMatchWithoutResult(matches.get(61), 11, 12, 9, "2015-10-18T15:30");
		assertMatchWithoutResult(matches.get(62), 5, 14, 9, "2015-10-18T15:30");
		assertMatchWithoutResult(matches.get(63), 3, 8, 10, "2015-10-24T10:15");
		assertMatchWithoutResult(matches.get(64), 4, 7, 10, "2015-10-24T14:30");
		assertMatchWithoutResult(matches.get(65), 1, 10, 10, "2015-10-24T14:30");
		assertMatchWithoutResult(matches.get(66), 2, 9, 10, "2015-10-24T14:30");
		assertMatchWithoutResult(matches.get(67), 5, 6, 10, "2015-10-25T14:30");
		assertMatchWithoutResult(matches.get(68), 14, 12, 10, "2015-10-25T14:30");
		assertMatchWithoutResult(matches.get(69), 13, 11, 10, "2015-10-25T14:30");
		assertMatchWithoutResult(matches.get(70), 6, 14, 11, "2015-10-31T14:00");
		assertMatchWithoutResult(matches.get(71), 9, 3, 11, "2015-10-31T14:00");
		assertMatchWithoutResult(matches.get(72), 12, 13, 11, "2015-10-31T14:00");
		assertMatchWithoutResult(matches.get(73), 10, 2, 11, "2015-11-01T14:00");
		assertMatchWithoutResult(matches.get(74), 11, 1, 11, "2015-11-01T14:00");
		assertMatchWithoutResult(matches.get(75), 7, 5, 11, "2015-11-01T14:00");
		assertMatchWithoutResult(matches.get(76), 8, 4, 11, "2015-11-01T14:00");
		assertMatchWithoutResult(matches.get(77), 3, 10, 12, "2015-11-07T10:15");
		assertMatchWithoutResult(matches.get(78), 4, 9, 12, "2015-11-07T14:00");
		assertMatchWithoutResult(matches.get(79), 1, 12, 12, "2015-11-07T14:00");
		assertMatchWithoutResult(matches.get(80), 2, 11, 12, "2015-11-07T14:00");
		assertMatchWithoutResult(matches.get(81), 6, 7, 12, "2015-11-07T14:00");
		assertMatchWithoutResult(matches.get(82), 5, 8, 12, "2015-11-08T14:00");
		assertMatchWithoutResult(matches.get(83), 14, 13, 12, "2015-11-08T14:00");
		assertMatchWithoutResult(matches.get(84), 9, 5, 13, "2015-11-14T13:30");
		assertMatchWithoutResult(matches.get(85), 10, 4, 13, "2015-11-14T13:30");
		assertMatchWithoutResult(matches.get(86), 12, 2, 13, "2015-11-14T13:30");
		assertMatchWithoutResult(matches.get(87), 13, 1, 13, "2015-11-15T13:30");
		assertMatchWithoutResult(matches.get(88), 11, 3, 13, "2015-11-15T13:30");
		assertMatchWithoutResult(matches.get(89), 7, 14, 13, "2015-11-15T13:30");
		assertMatchWithoutResult(matches.get(90), 8, 6, 13, "2015-11-15T13:30");
	}

	private void assertMatchWithoutResult(FotbalCzMatch match, Integer homePairingId, Integer visitorPairingId, Integer round, String date) {
		assertCommonMatchDetails(match, homePairingId, visitorPairingId, round, date);
	}

	private void assertMatch(FotbalCzMatch match, Integer homePairingId, Integer visitorPairingId, Integer round, String date, Long homeGoals, Long visitorGoals) {
		assertCommonMatchDetails(match, homePairingId, visitorPairingId, round, date);
		assertEquals(homeGoals, match.getResult().getHomeGoals());
		assertEquals(visitorGoals, match.getResult().getVisitorGoals());
	}

	private void assertCommonMatchDetails(FotbalCzMatch match, Integer homePairingId, Integer visitorPairingId, Integer round, String date) {
		assertEquals(homePairingId, match.getHomeTeam().getPairingId());
		assertEquals(visitorPairingId, match.getVisitorTeam().getPairingId());
		assertEquals(round, match.getRound());
		assertEquals(DateTime.parse(date), match.getDate());
	}

	private void assertRound(Integer expectedRound, String expectedDate, RoundDate roundDate) {
		assertEquals(expectedRound, roundDate.getRound());
		assertEquals(DateTime.parse(expectedDate), roundDate.getDate());
	}

	private void assertTeam(String expectedName, Integer expectedPairingId, FotbalCzTeam fotbalCzTeam) {
		assertEquals(expectedName, fotbalCzTeam.getTeamNameToDisplay());
		assertEquals(expectedPairingId, fotbalCzTeam.getPairingId());
	}
}