package cz.mikealdo.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.mikealdo.football.domain.Goal;
import cz.mikealdo.football.domain.GoalType;
import cz.mikealdo.football.domain.MatchResult;
import cz.mikealdo.football.domain.Player;
import cz.mikealdo.football.domain.Referee;
import cz.mikealdo.fotbalcz.domain.builder.PlayerBuilder;

public class MatchSummaryParser extends FotbalCzHTMLParser {

	public Document getSummaryDOMDocument(final String summaryLink){
		try {
			return getDOMDocument(new URL("https://is.fotbal.cz/" + summaryLink));
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Competition hash is not valid, given HTML is not parseable.");
		}
	}

	MatchResult createMatchResultFor(String linkToSummary) {
		return createResultWithDetails(getSummaryDOMDocument(linkToSummary));
	}

	MatchResult createResultWithDetails(Document summaryDOMDocument) {
		MatchResult matchResult = new MatchResult(retrieveSimpleResult(summaryDOMDocument));
		matchResult = updateMatchResultWithPenalties(matchResult, summaryDOMDocument);
		matchResult.setSpectators(Long.valueOf(getSpanWithTextContent(summaryDOMDocument, "Diváků:").getNextSibling().getNodeValue().trim()));
		matchResult.setReferees(retrieveReferees(summaryDOMDocument));
		matchResult.setHomeLineUp(retrieveHomeLineUp(summaryDOMDocument));
		matchResult.setVisitorLineUp(retrieveVisitorLineUp(summaryDOMDocument));
		matchResult.setHomeShooters(retrieveHomeShooters(summaryDOMDocument));
		matchResult.setVisitorShooters(retrieveVisitorShooters(summaryDOMDocument));
		return matchResult;
	}

	private String retrieveSimpleResult(Document summaryDOMDocument) {
		return getNodeValue(getSpanByClass(summaryDOMDocument, "vysledek-utkani"));
	}

	private List<Goal> retrieveHomeShooters(Document summaryDOMDocument) {
		NodeList rowsWithGoals = getRowsFromTableByCssClass(summaryDOMDocument, "vysledky hraci", "Střelci domácí").item(0).getChildNodes().item(0).getChildNodes().item(1).getChildNodes();
		return retrieveGoals(rowsWithGoals);
	}

	private List<Goal> retrieveVisitorShooters(Document summaryDOMDocument) {
		NodeList rowsWithGoals = getRowsFromTableByCssClass(summaryDOMDocument, "vysledky hraci", "Střelci domácí").item(0).getChildNodes().item(1).getChildNodes().item(1).getChildNodes();
		return retrieveGoals(rowsWithGoals);
	}

	private List<Goal> retrieveGoals(NodeList rowsWithGoals) {
		List<Goal> goals = new LinkedList<>();
		for (int i = 1; i < rowsWithGoals.getLength(); i++) {
			Node row = rowsWithGoals.item(i);
			NodeList cells = row.getChildNodes();
			GoalType goalType = GoalType.getByCode(getNodeValue(cells.item(2)));
			Integer fotbalCzId = parseInteger(getNodeValue(cells.item(3))).get();
			Integer minute = parseInteger(getNodeValue(cells.item(4))).get();
			goals.add(new Goal(goalType, fotbalCzId, minute));
		}
		return goals;
	}

	private List<Player> retrieveHomeLineUp(Document summaryDOMDocument) {
		NodeList rowsWithPlayers = getRowsFromTableByCssClass(summaryDOMDocument, "vysledky hraci", "Hráči domácí").item(0).getChildNodes().item(0).getChildNodes().item(1).getChildNodes();
		return retrievePlayers(rowsWithPlayers);
	}

	private List<Player> retrieveVisitorLineUp(Document summaryDOMDocument) {
		NodeList rowsWithPlayers = getRowsFromTableByCssClass(summaryDOMDocument, "vysledky hraci", "Hráči domácí").item(0).getChildNodes().item(1).getChildNodes().item(1).getChildNodes();
		return retrievePlayers(rowsWithPlayers);
	}

	private List<Player> retrievePlayers(NodeList rowsWithPlayers) {
		List<Player> homeLineUp = new LinkedList<>();
		for (int i = 1; i < rowsWithPlayers.getLength(); i++) {
			Node row = rowsWithPlayers.item(i);
			NodeList cells = row.getChildNodes();
			Integer dressNumber = parseInteger(getNodeValue(cells.item(0))).get();
			Node cellWithNameAndPosition = cells.item(1).getFirstChild();
			String name = getNodeValue(cellWithNameAndPosition).trim();
			String positionCode = retrievePositionCode(cellWithNameAndPosition);
			Integer fotbalCzId = parseInteger(getNodeValue(cells.item(2))).get();

			boolean isCaptain = row.getAttributes().getNamedItem("class").getNodeValue().contains("kapitanTrue");
			Optional<Integer> firstSubstitution = parseInteger(getNodeValue(cells.item(3)));
			Optional<Integer> secondSubstitution = parseInteger(getNodeValue(cells.item(4)));
			Optional<Integer> firstYellowCardInMinute = parseInteger(getNodeValue(cells.item(5)));
			Optional<Integer> secondYellowCardInMinute = parseInteger(getNodeValue(cells.item(6)));
			Optional<Integer> redCardInMinute = parseInteger(getNodeValue(cells.item(7)));
			boolean isInMainLineUp = (i <= 11);
			PlayerBuilder playerBuilder = new PlayerBuilder(name, fotbalCzId, positionCode, dressNumber)
					.captain(isCaptain)
					.inMainLineUp(isInMainLineUp)
					.firstSubstitutionMinute(firstSubstitution)
					.secondSubstitutionMinute(secondSubstitution)
					.firstYellowCardInMinute(firstYellowCardInMinute)
					.secondYellowCardInMinute(secondYellowCardInMinute)
					.redCardInMinute(redCardInMinute);
			homeLineUp.add(playerBuilder.build());
		}
		return homeLineUp;
	}

	private String retrievePositionCode(Node cellWithNameAndPosition) {
		String positionCode = getNodeValue(cellWithNameAndPosition.getChildNodes().item(1));
		if (positionCode.equals("(K)")) {
			positionCode = getNodeValue(cellWithNameAndPosition.getChildNodes().item(3));
		}
		return positionCode;
	}

	private List<Referee> retrieveReferees(Document summaryDOMDocument) {
		List<Referee> referees = new LinkedList<>();
		NodeList rows = getRowsFromTableByCssClass(summaryDOMDocument, "vysledky");
		for (int j = 1; j < 6; j++) {
			Optional<Referee> referee = createRefereeFrom(rows.item(j));
			if (referee.isPresent()) {
				referees.add(referee.get());
			}
		}
		return referees;
	}

	private Optional<Referee> createRefereeFrom(Node secondRow) {
		String positionCode = secondRow.getChildNodes().item(0).getFirstChild().getNodeValue();
		if (secondRow.getChildNodes().item(1).getFirstChild() != null && secondRow.getChildNodes().item(2).getFirstChild() != null) {
			String name = secondRow.getChildNodes().item(1).getFirstChild().getNodeValue();
			int fotbalCzId = parseInteger(secondRow.getChildNodes().item(2).getFirstChild().getNodeValue()).get();
			return Optional.of(new Referee(name, fotbalCzId, positionCode));
		}
		return Optional.empty();
	}

	private MatchResult updateMatchResultWithPenalties(MatchResult matchResult, Document summaryDOMDocument) {
		if (matchResult.isDraw()) {
			Node span = getSpanByClass(summaryDOMDocument, "penalta");
			if (span != null) {
				String penaltiesResult = span.getFirstChild().getNodeValue();
				if (penaltiesResult != null && !penaltiesResult.equals("")) {
					matchResult.updateResultWithPenalties(penaltiesResult);
				}
			}
		}
		return matchResult;
	}
}
