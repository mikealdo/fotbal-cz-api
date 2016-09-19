package cz.mikealdo.pages;

import cz.mikealdo.football.domain.*;
import cz.mikealdo.football.domain.builder.PlayerBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class MatchSummaryPage extends FotbalCzHTMLPage {

	MatchResult createMatchResultFor(String linkToSummary) {
        return createResultWithDetails(getDocument(linkToSummary));
	}

    MatchResult createResultWithDetails(Document summaryDOMDocument) {
		MatchResult matchResult = new MatchResult(retrieveSimpleResult(summaryDOMDocument));
		matchResult = updateMatchResultWithPenalties(matchResult, summaryDOMDocument);
		matchResult.setSpectators(retrieveSpectators(summaryDOMDocument));
		matchResult.setReferees(retrieveReferees(summaryDOMDocument));
		matchResult.setHomeLineUp(retrieveHomeLineUp(summaryDOMDocument));
		matchResult.setVisitorLineUp(retrieveVisitorLineUp(summaryDOMDocument));
		matchResult.setHomeShooters(retrieveHomeShooters(summaryDOMDocument));
		matchResult.setVisitorShooters(retrieveVisitorShooters(summaryDOMDocument));
		return matchResult;
	}

    private Long retrieveSpectators(Document summaryDOMDocument) {
        String spectatorsText = getSpanWithTextContent(summaryDOMDocument, "Diváků:").parent().ownText().trim();
        if (spectatorsText.isEmpty()) {
            return 0L;
        }
        return Long.valueOf(spectatorsText);
    }

    private String retrieveSimpleResult(Document summaryDOMDocument) {
		return getSpanByClass(summaryDOMDocument, "vysledek-utkani").ownText();
	}

	private List<Goal> retrieveHomeShooters(Document summaryDOMDocument) {
		Elements rowsWithGoals = getRowsFromTableByCssClass(summaryDOMDocument, "vysledky hraci", "Střelci domácí").first().child(0).children().get(1).children().first().children();
		return retrieveGoals(rowsWithGoals);
	}

	private List<Goal> retrieveVisitorShooters(Document summaryDOMDocument) {
		Elements rowsWithGoals = getRowsFromTableByCssClass(summaryDOMDocument, "vysledky hraci", "Střelci domácí").first().child(1).children().get(1).children().first().children();
		return retrieveGoals(rowsWithGoals);
	}

	private List<Goal> retrieveGoals(Elements rowsWithGoals) {
		rowsWithGoals.remove(0);
		List<Goal> goals = new LinkedList<>();
		for (Element row: rowsWithGoals) {
			GoalType goalType = GoalType.getByCode((row.child(2).text()));
			Integer fotbalCzId = parseInteger(row.child(3).text()).get();
			String minute = row.child(4).text();
			goals.add(new Goal(goalType, fotbalCzId, minute));
		}
		return goals;
	}

	private List<Player> retrieveHomeLineUp(Document summaryDOMDocument) {
		Elements rowsWithPlayers = getRowsFromTableByCssClass(summaryDOMDocument, "vysledky hraci", "Hráči domácí").first().child(0).children().get(1).child(0).children();
		return retrievePlayers(rowsWithPlayers);
	}

	private List<Player> retrieveVisitorLineUp(Document summaryDOMDocument) {
		Elements rowsWithPlayers = getRowsFromTableByCssClass(summaryDOMDocument, "vysledky hraci", "Hráči domácí").first().child(1).children().get(1).child(0).children();
		return retrievePlayers(rowsWithPlayers);
	}

	private List<Player> retrievePlayers(Elements rowsWithPlayers) {
		rowsWithPlayers.remove(0); // header
		List<Player> homeLineUp = new LinkedList<>();
		int i = 1;
		for (Element row : rowsWithPlayers) {
			Integer dressNumber = parseInteger(row.child(0).text()).get();
			Element cellWithNameAndPosition = row.child(1).child(0);
			String name = cellWithNameAndPosition.ownText().trim();
			String positionCode = retrievePositionCode(cellWithNameAndPosition);
			Integer fotbalCzId = parseInteger(row.child(2).text()).get();

			boolean isCaptain = row.hasClass("kapitanTrue");
			Optional<Integer> firstSubstitution = parseInteger(row.child(3).text());
			Optional<Integer> secondSubstitution = parseInteger(row.child(4).text());
			Optional<Integer> firstYellowCardInMinute = parseInteger(row.child(5).text());
			Optional<Integer> secondYellowCardInMinute = parseInteger(row.child(6).text());
			Optional<Integer> redCardInMinute = parseInteger(row.child(7).text());
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
			i++;
		}
		return homeLineUp;
	}

	private String retrievePositionCode(Element cellWithNameAndPosition) {
		String positionCode = cellWithNameAndPosition.child(0).text();
		if (positionCode.equals("(K)")) {
			positionCode = cellWithNameAndPosition.child(1).text();
		}
		return positionCode;
	}

	private List<Referee> retrieveReferees(Document summaryDOMDocument) {
		List<Referee> referees = new LinkedList<>();
		Elements rows = getRowsFromTableByCssClass(summaryDOMDocument, "vysledky").get(0).children();
		for (int j = 1; j < 6; j++) {
			Optional<Referee> referee = createRefereeFrom(rows.get(j));
			if (referee.isPresent()) {
				referees.add(referee.get());
			}
		}
		return referees;
	}

	private Optional<Referee> createRefereeFrom(Element row) {
		String positionCode = row.child(0).text();
		if (isNotEmpty(row.child(1).text()) && isNotEmpty(row.child(2).text())) {
			String name = row.child(1).text();
			int fotbalCzId = parseInteger(row.child(2).text()).get();
			return Optional.of(new Referee(name, fotbalCzId, positionCode));
		}
		return Optional.empty();
	}

	private MatchResult updateMatchResultWithPenalties(MatchResult matchResult, Document summaryDOMDocument) {
		if (matchResult.isDraw()) {
			Element span = getSpanByClass(summaryDOMDocument, "penalta");
			if (span != null) {
				String penaltiesResult = span.text();
				if (penaltiesResult != null && !penaltiesResult.equals("")) {
					matchResult.updateResultWithPenalties(penaltiesResult);
				}
			}
		}
		return matchResult;
	}
}
