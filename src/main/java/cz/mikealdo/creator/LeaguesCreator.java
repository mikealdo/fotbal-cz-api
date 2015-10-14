package cz.mikealdo.creator;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import cz.mikealdo.fotbalcz.domain.CompetitionDetails;
import cz.mikealdo.fotbalcz.domain.FotbalCzMatch;
import cz.mikealdo.parser.MatchStatisticsParser;
import cz.mikealdo.fotbalcz.domain.FotbalCzLeague;

public class LeaguesCreator {

	MatchStatisticsParser parser = new MatchStatisticsParser();
	
	public FotbalCzLeague createLeague(String competitionHash) {
		CompetitionDetails competitionDetails = parser.createCompetitionDetailsFrom(competitionHash);
		FotbalCzLeague league = new FotbalCzLeague(competitionDetails.getCompetitionName());
		fillMatchesWithNeededInfo(competitionDetails);
		league.setMatches(competitionDetails.getMatches());
		league.setTeams(competitionDetails.getTeams());
		return league;
	}

	public List<FotbalCzLeague> createLeagues(List<String> competitionHashes) {
		return competitionHashes.stream().map(this::createLeague).collect(Collectors.toCollection(LinkedList::new));
	}

	private void fillMatchesWithNeededInfo(CompetitionDetails competitionDetails) {
		int countOfTeams = competitionDetails.getTeams().size();
		int countOfMatchesInRound = countOfTeams / 2;
		int round = 0;
		int matchCount = 0;
		for (FotbalCzMatch match : competitionDetails.getMatches()) {
			match.setDate(competitionDetails.getRoundDates().get(round).getDate());
			match.setRound(competitionDetails.getRoundDates().get(round).getRound());
			matchCount++;
			if (matchCount % countOfMatchesInRound == 0) {
				round++;
			}
		}
	}
}
