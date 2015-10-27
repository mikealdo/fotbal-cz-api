package cz.mikealdo.fotbalcz.domain;


import org.junit.Test;

import cz.mikealdo.football.domain.MatchResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MatchResultTest {

	@Test(expected = IllegalArgumentException.class)
	public void shouldNotParseWrongResultString() throws Exception {
		new MatchResult("2:1:2");
	}

	@Test
	public void shouldParseResultThroughConstructor() throws Exception {
		MatchResult result = new MatchResult("2:1");

		assertEquals(Long.valueOf(2L), result.getHomeGoals());
		assertEquals(Long.valueOf(1L), result.getVisitorGoals());
		assertTrue(result.isResultEntered());
	}

	@Test
	public void shouldParseResultThroughConstructorWithBlankChars() throws Exception {
		MatchResult result = new MatchResult("2 : 1");

		assertEquals(Long.valueOf(2L), result.getHomeGoals());
		assertEquals(Long.valueOf(1L), result.getVisitorGoals());
		assertTrue(result.isResultEntered());
	}

	@Test
	public void shouldReturnTrueForDraw() throws Exception {
		MatchResult result = new MatchResult("1:1");

		assertTrue(result.isDraw());
	}

	@Test
	public void shouldReturnFalseForNotDraw() throws Exception {
		MatchResult result = new MatchResult("2:1");

		assertFalse(result.isDraw());
	}

	@Test
	public void shouldCreateResultWithPenaltiesGiven() throws Exception {
		MatchResult result = new MatchResult("1:1", "(PK:4:5)");
		
		assertEquals(Long.valueOf(1L), result.getHomeGoals());
		assertEquals(Long.valueOf(1L), result.getVisitorGoals());
		assertTrue(result.isResultEntered());
		assertTrue(result.isDraw());
		assertTrue(result.isPenaltyShootout());
		assertEquals(Long.valueOf(4L), result.getHomeGoalsInPenaltyShootout());
		assertEquals(Long.valueOf(5L), result.getVisitorGoalsInPenaltyShootout());
		
	}
}