package cz.mikealdo.pages;

import cz.mikealdo.football.domain.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MatchesStatisticsPage extends FotbalCzHTMLPage {

    public static final String COMPETITION_DETAIL_BASE_URL = "https://is.fotbal.cz/souteze/detail-souteze.aspx?req=";
    private MatchSummaryPage summaryPage;

    public MatchesStatisticsPage(MatchSummaryPage summaryPage) {
        this.summaryPage = summaryPage;
    }

    public CompetitionDetails createCompetitionDetailsFrom(String competitionHash) {
		Document output = getMatchesDOMDocument(competitionHash);
		return createCompetitionDetails(output);
	}

    public Document getMatchesDOMDocument(final String competitionHash) {
        return getDocument(COMPETITION_DETAIL_BASE_URL + competitionHash);
    }

	public CompetitionDetails createCompetitionDetails(Document input) {
		CompetitionDetails competitionDetails = new CompetitionDetails();
        competitionDetails.setCompetitionName("Name"); // TODO retrieve competition name from right place
		competitionDetails.setRoundDates(retrieveRounds(input));
		competitionDetails.setMatches(retrieveMatches(input, competitionDetails.getRoundDates()));
		competitionDetails.setTeams(retrieveTeams(competitionDetails.getMatches()));
		return competitionDetails;
	}

	private List<RoundDate> retrieveRounds(Document output) {
		List<RoundDate> rounds = new LinkedList<>();
        Element competitionTable = output.select("table.soutez-kola").first();
        Elements rows = competitionTable.getElementsByTag("tr");
        for (Element row : rows) {
			Element cell = row.child(0);
            if (cell.hasClass("kolo")) {
                Elements h2 = cell.select("h2.nadpis");
                String roundText = h2.text();
                Integer round = Integer.parseInt(roundText.split("\\.")[0]);
                String date = h2.select("span").text().trim();
                rounds.add(new RoundDate(round, parseDateTime(date)));
            }
		}
		return rounds;
	}

	List<Team> retrieveTeams(List<Match> matches) {
		List<Team> teams = new LinkedList<>();
		for (Match match : matches) {
			if (!teams.contains(match.getHomeTeam())) {
				teams.add(match.getHomeTeam());
			}
			if (!teams.contains(match.getVisitorTeam())) {
				teams.add(match.getVisitorTeam());
			}
		}
		List<Team> sortedTeams = teams.stream().sorted(Comparator.comparingInt(Team::getPairingId)).collect(Collectors.toList());
		sortedTeams.addAll(findFreeDraws(sortedTeams));
		return sortedTeams.stream().sorted(Comparator.comparingInt(Team::getPairingId)).collect(Collectors.toList());
	}

	private List<Team> findFreeDraws(List<Team> sortedTeams) {
		List<Team> freeDraws = new LinkedList<>();
		int lastPairingId = 0;
		for (Team team : sortedTeams) {
			int currentPairingId = team.getPairingId();
			if (currentPairingId > lastPairingId+1) {
				freeDraws.add(new Team(currentPairingId-1, "volný los"));
			}
			lastPairingId = currentPairingId;
		}
		return freeDraws;
	}

    private List<Match> retrieveMatches(Document output, List<RoundDate> roundDates) {
        Elements tables = output.select("table.soutez-zapasy");
        List<Match> matches = new LinkedList<>();
		int round = 0;
        for (Element table : tables) {
            Elements rows = table.getElementsByTag("tr");
            for (Element row : rows) {
                Match match = new Match();
                Elements cells = row.children();
                match.setDate(parseDateTime(cells.get(0).text()));
                match.setHomeTeam(new Team(Integer.parseInt(cells.get(1).text()), cells.get(2).text()));
                match.setVisitorTeam(new Team(Integer.parseInt(cells.get(4).text()), cells.get(5).text()));
                if (cells.get(9).child(0).hasClass("uzavren")) {
                    String simpleResult = cells.get(6).text();
                    match.setResult(new MatchResult(simpleResult));
                    match.updateResult(retrieveDetailedMatchResult(cells.get(9).child(0)));
                }
                match.setRound(roundDates.get(round).getRound());
                matches.add(match);
            }
            round++;
        }
        Stream<Match> sorted = matches.stream().sorted((o1, o2) -> {
            int firstPairingId = o1.getHomeTeam().getPairingId();
            int secondPairingId = o2.getHomeTeam().getPairingId();
            int result = o1.getRound() > o2.getRound() ? 1 : o1.getRound().equals(o2.getRound()) ? 0 : -1;
            if (result == 0) {
                result = firstPairingId > secondPairingId ? 1 : -1;
            }
            return result;
        });
        return sorted.collect(Collectors.toList());
	}

	protected MatchResult retrieveDetailedMatchResult(Element link) {
		String linkToSummary = link.attr("abs:href");
		return summaryPage.createMatchResultFor(linkToSummary);
	}
}
