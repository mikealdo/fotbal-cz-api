package cz.mikealdo.parser;

import static cz.mikealdo.football.domain.GoalType.*;
import static org.junit.Assert.*;

import org.apache.commons.io.IOUtils;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import cz.mikealdo.football.domain.Goal;
import cz.mikealdo.football.domain.GoalType;
import cz.mikealdo.football.domain.MatchResult;
import cz.mikealdo.football.domain.Player;
import cz.mikealdo.football.domain.PlayerPosition;
import cz.mikealdo.football.domain.Referee;
import cz.mikealdo.football.domain.RefereePosition;
import cz.mikealdo.football.domain.SquadMember;

public class MatchSummaryParserTest {
	
	MatchSummaryParser parser;
	private String html;

	@Before
	public void setUp() throws Exception {
		parser = new MatchSummaryParser();
		html = IOUtils.toString(
				this.getClass().getResource("/html/match-summary.html"),
				"UTF-8"
		);
	}

	@Test
	public void shouldFillMatchSummaryWithRelevantData() throws Exception {
		Document document = parser.getDocumentFor(html);

		MatchResult result = parser.createResultWithDetails(document);

		assertEquals(Long.valueOf(1), result.getHomeGoals());
		assertEquals(Long.valueOf(1), result.getVisitorGoals());
		assertFalse(result.isForfeited());
		assertTrue(result.isResultEntered());
		assertTrue(result.isPenaltyShootout());
		assertEquals(Long.valueOf(4), result.getHomeGoalsInPenaltyShootout());
		assertEquals(Long.valueOf(5), result.getVisitorGoalsInPenaltyShootout());
		assertEquals(Long.valueOf(250), result.getSpectators());
		assertEquals(3, result.getReferees().size());
		assertReferee("ROTH Patrik", "74010731", RefereePosition.R, result.getReferees().get(0));
		assertReferee("VLK Miroslav", "74060723", RefereePosition.AR1, result.getReferees().get(1));
		assertReferee("VLK Martin", "70030945", RefereePosition.AR2, result.getReferees().get(2));
		assertPlayer("Mošnička Lukáš", "88050094", PlayerPosition.GOALKEEPER, 17, result.getHomeLineUp().get(0));
		assertPlayer("Šťovíček Filip", "95081314", PlayerPosition.DEFENDER, 4, result.getHomeLineUp().get(1));
		assertEquals(Integer.valueOf(52), result.getHomeLineUp().get(1).getFirstYellowCardInMinute());
		assertPlayer("Sirotek Marek", "95021305", PlayerPosition.FORWARD, 5, result.getHomeLineUp().get(2));
		assertPlayer("Ségl Michal", "85110468", PlayerPosition.DEFENDER, 6, result.getHomeLineUp().get(3));
		assertPlayer("Repetný Jaroslav", "92040355", PlayerPosition.MIDFIELDER, 7, result.getHomeLineUp().get(4));
		assertPlayer("Chlasták Petr", "84041871", PlayerPosition.FORWARD, 11, result.getHomeLineUp().get(5));
		assertEquals(Integer.valueOf(58), result.getHomeLineUp().get(5).getFirstSubstitutionMinute());
		assertPlayer("Čejda Jakub", "91052209", PlayerPosition.MIDFIELDER, 12, result.getHomeLineUp().get(6));
		assertPlayer("Sůsa Vojtěch", "95101262", PlayerPosition.DEFENDER, 13, result.getHomeLineUp().get(7));
		assertEquals(Integer.valueOf(74), result.getHomeLineUp().get(7).getFirstSubstitutionMinute());
		assertPlayer("Vrbický Lukáš", "90040710", PlayerPosition.DEFENDER, 14, result.getHomeLineUp().get(8));
		assertPlayer("Chlasták Martin", "85100331", PlayerPosition.MIDFIELDER, 16, result.getHomeLineUp().get(9));
		assertTrue(result.getHomeLineUp().get(9).isCaptain());
		assertEquals(Integer.valueOf(15), result.getHomeLineUp().get(9).getFirstYellowCardInMinute());
		assertEquals(Integer.valueOf(20), result.getHomeLineUp().get(9).getSecondYellowCardInMinute());
		assertEquals(Integer.valueOf(20), result.getHomeLineUp().get(9).getRedCardInMinute());
		assertPlayer("Dvořák Michael", "94060591", PlayerPosition.MIDFIELDER, 15, result.getHomeLineUp().get(10));
		assertEquals(Integer.valueOf(70), result.getHomeLineUp().get(10).getRedCardInMinute());
		assertIncomingPlayer("Dvořák Jan", "92110572", PlayerPosition.DEFENDER, 2, result.getHomeLineUp().get(11));
		assertEquals(Integer.valueOf(74), result.getHomeLineUp().get(11).getFirstSubstitutionMinute());
		assertEquals(Integer.valueOf(84), result.getHomeLineUp().get(11).getSecondSubstitutionMinute());
		assertIncomingPlayer("Čížek Jan", "93070032", PlayerPosition.MIDFIELDER, 3, result.getHomeLineUp().get(12));
		assertEquals(Integer.valueOf(84), result.getHomeLineUp().get(12).getFirstSubstitutionMinute());
		assertIncomingPlayer("Brotánek Tomáš", "90120785", PlayerPosition.DEFENDER, 8, result.getHomeLineUp().get(13));
		assertIncomingPlayer("Valenta Miroslav", "97110987", PlayerPosition.MIDFIELDER, 9, result.getHomeLineUp().get(14));
		assertIncomingPlayer("Havel Martin", "81050211", PlayerPosition.FORWARD, 10, result.getHomeLineUp().get(15));
		assertEquals(Integer.valueOf(58), result.getHomeLineUp().get(15).getFirstSubstitutionMinute());
		assertPlayer("Dřevojan Filip", "98030627", PlayerPosition.GOALKEEPER, 1, result.getVisitorLineUp().get(0));
		assertPlayer("Seidel Radek", "95021284", PlayerPosition.DEFENDER, 2, result.getVisitorLineUp().get(1));
		assertEquals(Integer.valueOf(78), result.getVisitorLineUp().get(1).getFirstSubstitutionMinute());
		assertPlayer("Sedláček Milan", "90070067", PlayerPosition.DEFENDER, 3, result.getVisitorLineUp().get(2));
		assertPlayer("Kabíček Pavel", "96091373", PlayerPosition.MIDFIELDER, 4, result.getVisitorLineUp().get(3));
		assertEquals(Integer.valueOf(58), result.getVisitorLineUp().get(3).getFirstSubstitutionMinute());
		assertPlayer("Prchlík Marian", "92080042", PlayerPosition.MIDFIELDER, 6, result.getVisitorLineUp().get(4));
		assertEquals(Integer.valueOf(64), result.getVisitorLineUp().get(4).getFirstYellowCardInMinute());
		assertPlayer("Raček Martin", "91081832", PlayerPosition.DEFENDER, 7, result.getVisitorLineUp().get(5));
		assertEquals(Integer.valueOf(58), result.getHomeLineUp().get(5).getFirstSubstitutionMinute());
		assertPlayer("Růzha Josef", "76041187", PlayerPosition.FORWARD, 8, result.getVisitorLineUp().get(6));
		assertEquals(Integer.valueOf(89), result.getVisitorLineUp().get(6).getFirstYellowCardInMinute());
		assertPlayer("Davídek Michal", "83050740", PlayerPosition.FORWARD, 9, result.getVisitorLineUp().get(7));
		assertPlayer("Mára Karel", "89081318", PlayerPosition.MIDFIELDER, 10, result.getVisitorLineUp().get(8));
		assertEquals(Integer.valueOf(44), result.getVisitorLineUp().get(8).getFirstYellowCardInMinute());
		assertPlayer("Nousek Marcel", "86062116", PlayerPosition.MIDFIELDER, 11, result.getVisitorLineUp().get(9));
		assertPlayer("Kobliha Pavel", "87032230", PlayerPosition.MIDFIELDER, 15, result.getVisitorLineUp().get(10));
		assertEquals(Integer.valueOf(84), result.getVisitorLineUp().get(10).getFirstYellowCardInMinute());
		assertIncomingPlayer("Všetečka Zdeněk", "99080160", PlayerPosition.UNDEFINED, 5, result.getVisitorLineUp().get(11));
		assertIncomingPlayer("Kotva David", "92100959", PlayerPosition.UNDEFINED, 12, result.getVisitorLineUp().get(12));
		assertEquals(Integer.valueOf(58), result.getVisitorLineUp().get(12).getFirstSubstitutionMinute());
		assertIncomingPlayer("Müller Pavel", "86091495", PlayerPosition.UNDEFINED, 13, result.getVisitorLineUp().get(13));
		assertIncomingPlayer("Hedvík Radek", "82101686", PlayerPosition.UNDEFINED, 14, result.getVisitorLineUp().get(14));
		assertEquals(Integer.valueOf(78), result.getVisitorLineUp().get(14).getFirstSubstitutionMinute());
		assertEquals(Integer.valueOf(87), result.getVisitorLineUp().get(14).getFirstYellowCardInMinute());
		assertIncomingPlayer("Rataj Jan", "97110189", PlayerPosition.UNDEFINED, 16, result.getVisitorLineUp().get(15));
		assertIncomingPlayer("Trnobranský Jakub", "88091709", PlayerPosition.UNDEFINED, 17, result.getVisitorLineUp().get(16));
		assertGoal("91052209", GOAL, 32, result.getHomeShooters().get(0));
		assertGoal("76041187", GOAL, 39, result.getVisitorShooters().get(0));
	}

	private void assertReferee(String name, String fotbalCzId, RefereePosition refereePosition, Referee squadMember) {
		assertSquadMember(name, fotbalCzId, squadMember);
		assertEquals(refereePosition, squadMember.getRefereePosition());
	}

	private void assertSquadMember(String name, String fotbalCzId, SquadMember squadMember) {
		assertEquals(name, squadMember.getName());
		assertEquals(Integer.valueOf(fotbalCzId), squadMember.getFotbalCzId());
	}

	private void assertPlayer(String name, String fotbalCzId, PlayerPosition playerPosition, Integer dressNumber, Player squadMember) {
		assertCommonAttributesForPlayer(name, fotbalCzId, playerPosition, dressNumber, squadMember);
		assertTrue(squadMember.isInMainLineUp());
	}

	private void assertCommonAttributesForPlayer(String name, String fotbalCzId, PlayerPosition playerPosition, Integer dressNumber, Player squadMember) {
		assertSquadMember(name, fotbalCzId, squadMember);
		assertEquals(playerPosition, squadMember.getPlayerPosition());
		assertEquals(dressNumber, squadMember.getDressNumber());
	}

	private void assertIncomingPlayer(String name, String fotbalCzId, PlayerPosition playerPosition, Integer dressNumber, Player squadMember) {
		assertCommonAttributesForPlayer(name, fotbalCzId, playerPosition, dressNumber, squadMember);
		assertFalse(squadMember.isInMainLineUp());
	}

	private void assertGoal(String fotbalCzId, GoalType goalType, int minute, Goal goal) {
		assertEquals(Integer.valueOf(fotbalCzId), goal.getPlayerId());
		assertEquals(goalType, goal.getGoalType());
		assertEquals(Integer.valueOf(minute), goal.getMinute());
	}
}