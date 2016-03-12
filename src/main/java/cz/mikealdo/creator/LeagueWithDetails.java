package cz.mikealdo.creator;

import cz.mikealdo.football.domain.League;
import cz.mikealdo.football.domain.Match;
import cz.mikealdo.football.domain.CompetitionDetails;
import cz.mikealdo.football.domain.RoundDate;

import java.util.List;

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
        List<RoundDate> roundDates = competitionDetails.getRoundDates();
        for (Match match : competitionDetails.getMatches()) {
            if (match.getDate() == null) {
                match.setDate(roundDates.get(match.getRound()).getDate());
            }
		}
	}
}
