package cz.mikealdo.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cz.mikealdo.fotbalcz.domain.FotbalCzTeam;
import cz.mikealdo.football.domain.MatchResult;
import cz.mikealdo.football.domain.RoundDate;
import cz.mikealdo.fotbalcz.domain.CompetitionDetails;
import cz.mikealdo.fotbalcz.domain.FotbalCzMatch;

public class MatchStatisticsParser extends FotbalCzHTMLParser {

	public CompetitionDetails createCompetitionDetailsFrom(String competitionHash) {
		Document output = getMatchesDOMDocument(competitionHash);
		return createCompetitionDetails(output);
	}

	CompetitionDetails createCompetitionDetails(Document output) {
		CompetitionDetails competitionDetails = new CompetitionDetails();
		competitionDetails.setRoundDates(retrieveRounds(output));
		competitionDetails.setMatches(retrieveMatches(output, competitionDetails.getRoundDates()));
//		competitionDetails.setRoundDates(updateRoundDatesByTeamMatch(competitionDetails.getRoundDates(), competitionDetails.getMatches(), teamName));
		competitionDetails.setTeams(retrieveTeams(competitionDetails.getMatches()));
//		competitionDetails.setArrivals(pairingBasis.getArrivals());
		return competitionDetails;
	}

	List<RoundDate> updateRoundDatesByTeamMatch(List<RoundDate> roundDates, List<FotbalCzMatch> matches, String teamName) {
		for (FotbalCzMatch match : matches) {
			if (isMatchForGivenTeamName(teamName, match)) {
				for (RoundDate roundDate : roundDates) {
					if (roundDate.getRound().equals(match.getRound())) {
						roundDate.setDate(match.getDate());
						break;
					}
				}
			} 
		}
		return roundDates;
	}

	private boolean isMatchForGivenTeamName(String teamNameHash, FotbalCzMatch match) {
		return match.getHomeTeam().getTeamNameToDisplay().equals(teamNameHash) || match.getVisitorTeam().getTeamNameToDisplay().equals(teamNameHash);
	}

	private List<RoundDate> retrieveRounds(Document output) {
		List<RoundDate> rounds = new LinkedList<>();
		Node competitionTable = getCompetitionTable(output);
		NodeList rows = competitionTable.getChildNodes();
		for (int i = 0; i < rows.getLength(); i++) {
			Node row = rows.item(i);
			Node cell = row.getFirstChild();
			if (isCellWithRound(cell)) {
				String roundText = cell.getFirstChild().getFirstChild().getFirstChild().getNodeValue();
				Integer round = Integer.parseInt(roundText.split("\\.")[0]);
				String date = cell.getFirstChild().getFirstChild().getChildNodes().item(1).getFirstChild().getNodeValue();
				rounds.add(new RoundDate(round, parseDateTime(date)));
			}
		}
		return rounds;
	}

	List<FotbalCzTeam> retrieveTeams(List<FotbalCzMatch> matches) {
		List<FotbalCzTeam> teams = new LinkedList<>();
		for (FotbalCzMatch match : matches) {
			if (!teams.contains(match.getHomeTeam())) {
				teams.add(match.getHomeTeam());
			}
			if (!teams.contains(match.getVisitorTeam())) {
				teams.add(match.getVisitorTeam());
			}
		}
		List<FotbalCzTeam> sortedTeams = teams.stream().sorted(Comparator.comparingInt(FotbalCzTeam::getPairingId)).collect(Collectors.toList());
		sortedTeams.addAll(findFreeDraws(sortedTeams));
		return sortedTeams.stream().sorted(Comparator.comparingInt(FotbalCzTeam::getPairingId)).collect(Collectors.toList());
	}

	private List<FotbalCzTeam> findFreeDraws(List<FotbalCzTeam> sortedTeams) {
		List<FotbalCzTeam> freeDraws = new LinkedList<>();
		int lastPairingId = 0;
		for (FotbalCzTeam team : sortedTeams) {
			int currentPairingId = team.getPairingId();
			if (currentPairingId > lastPairingId+1) {
				freeDraws.add(new FotbalCzTeam(currentPairingId-1, "volný los"));
			}
			lastPairingId = currentPairingId;
		}
		return freeDraws;
	}

	private Node getCompetitionTable(Document output) {
		NodeList allTables = output.getElementsByTagName("table");
		for (int i = 0; i < allTables.getLength(); i++) {
			Node table = allTables.item(i);
			if (isTableCompetitionTable(table)) {
				return table;
			}
		}
		throw new IllegalArgumentException("Competition table is not in page");
	}

	private List<FotbalCzMatch> retrieveMatches(Document output, List<RoundDate> roundDates) {
		NodeList allTables = output.getElementsByTagName("table");
		List<FotbalCzMatch> matches = new LinkedList<>();
		int round = 0;
		for (int i = 0; i < allTables.getLength(); i++) {
			Node table = allTables.item(i);
			if (isTableWithMatches(table)) {
				NodeList rows = table.getChildNodes();
				for (int j = 0; j < rows.getLength(); j++) {
					Node row = rows.item(j);
					FotbalCzMatch match = new FotbalCzMatch();
					NodeList cells = row.getChildNodes();
					match.setDate(parseDateTime(getNodeValue(cells.item(0))));
					match.setHomeTeam(new FotbalCzTeam(Integer.parseInt(getNodeValue(cells.item(1))), getNodeValue(cells.item(2))));
					match.setVisitorTeam(new FotbalCzTeam(Integer.parseInt(getNodeValue(cells.item(4))), getNodeValue(cells.item(5))));
					String summaryState = getNodeValue(cells.item(9).getFirstChild());
					if (summaryState.contains("zápis uzavřen")) {
						String simpleResult = getNodeValue(cells.item(6));
						match.setResult(new MatchResult(simpleResult));
						match.updateResult(retrieveDetailedMatchResult(cells));
					}
					match.setRound(roundDates.get(round).getRound());
					matches.add(match);
				}
				round++;
			}
		}
		return matches;
	}

	protected MatchResult retrieveDetailedMatchResult(NodeList cells) {
		String linkToSummary = cells.item(9).getFirstChild().getAttributes().getNamedItem("href").getNodeValue().replace("../", "/");
		return new MatchSummaryParser().createMatchResultFor(linkToSummary);
	}

	private Document getMatchesDOMDocument(final String competitionHash){
		try {
			return getDOMDocument(new URL("https://is.fotbal.cz/souteze/detail-souteze.aspx?req=" + competitionHash));
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Competition hash is not valid, given HTML is not parseable.");
		}
	}


	private boolean isTableCompetitionTable(Node table) {
		return table.getAttributes().getNamedItem("class").getNodeValue().equals("soutez-kola");
	}

	private boolean isTableWithMatches(Node table) {
		return table.getAttributes().getNamedItem("class").getNodeValue().equals("soutez-zapasy");
	}

	private boolean isCellWithRound(Node cell) {
		Node classAttribute = cell.getAttributes().getNamedItem("class");
		return classAttribute != null && classAttribute.getNodeValue().equals("kolo");
	}
	
}
