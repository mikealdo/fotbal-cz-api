package cz.mikealdo.creator;

import cz.mikealdo.football.domain.League;
import cz.mikealdo.football.domain.Match;
import cz.mikealdo.football.domain.CompetitionDetails;

public class LeagueWithDetails {

    private League league;

    public LeagueWithDetails(League league) {
        this.league = league;
    }

    public League enhanceByDetails(CompetitionDetails competitionDetails) {
		fillMatchesWithNeededInfo(competitionDetails);
		league.setMatches(competitionDetails.getMatches());
		league.setTeams(competitionDetails.getTeams());
		return league;
	}

	private void fillMatchesWithNeededInfo(CompetitionDetails competitionDetails) {
		int countOfTeams = competitionDetails.getTeams().size();
		int countOfMatchesInRound = countOfTeams / 2;
		int round = 0;
		int matchCount = 0;
		for (Match match : competitionDetails.getMatches()) {
			match.setDate(competitionDetails.getRoundDates().get(round).getDate());
			match.setRound(competitionDetails.getRoundDates().get(round).getRound());
			matchCount++;
			if (matchCount % countOfMatchesInRound == 0) {
				round++;
			}
		}
	}
}
