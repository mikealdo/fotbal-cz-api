package cz.mikealdo.parser;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import cz.mikealdo.fotbalcz.domain.FotbalCzTeam;
import cz.mikealdo.football.domain.MatchResult;
import cz.mikealdo.football.domain.RoundDate;
import cz.mikealdo.fotbalcz.domain.CompetitionDetails;
import cz.mikealdo.fotbalcz.domain.FotbalCzMatch;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatchStatisticsParser extends FotbalCzHTMLParser {

    public static final String COMPETITION_DETAIL_BASE_URL = "https://is.fotbal.cz/souteze/detail-souteze.aspx?req=";
    private MatchSummaryParser summaryParser;

    @Autowired
    public MatchStatisticsParser(MatchSummaryParser summaryParser) {
        this.summaryParser = summaryParser;
    }

    public CompetitionDetails createCompetitionDetailsFrom(String competitionHash) {
		Document output = getMatchesDOMDocument(competitionHash);
		return createCompetitionDetails(output);
	}

    public Document getMatchesDOMDocument(final String competitionHash) {
        return getDocument(COMPETITION_DETAIL_BASE_URL + competitionHash);
    }

	CompetitionDetails createCompetitionDetails(Document output) {
		CompetitionDetails competitionDetails = new CompetitionDetails();
		competitionDetails.setRoundDates(retrieveRounds(output));
		competitionDetails.setMatches(retrieveMatches(output, competitionDetails.getRoundDates()));
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

    private List<FotbalCzMatch> retrieveMatches(Document output, List<RoundDate> roundDates) {
        Elements tables = output.select("table.soutez-zapasy");
        List<FotbalCzMatch> matches = new LinkedList<>();
		int round = 0;
        for (Element table : tables) {
            Elements rows = table.getElementsByTag("tr");
            for (Element row : rows) {
                FotbalCzMatch match = new FotbalCzMatch();
                Elements cells = row.children();
                match.setDate(parseDateTime(cells.get(0).text()));
                match.setHomeTeam(new FotbalCzTeam(Integer.parseInt(cells.get(1).text()), cells.get(2).text()));
                match.setVisitorTeam(new FotbalCzTeam(Integer.parseInt(cells.get(4).text()), cells.get(5).text()));
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
		return matches;
	}

	protected MatchResult retrieveDetailedMatchResult(Element link) {
		String linkToSummary = link.attr("abs:href");
		return summaryParser.createMatchResultFor(linkToSummary);
	}

}
