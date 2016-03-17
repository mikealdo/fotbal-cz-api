package cz.mikealdo.detailedleague;

import cz.mikealdo.football.domain.League;
import cz.mikealdo.football.domain.CompetitionDetails;
import org.joda.time.DateTime;

import java.util.Map;

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
        Map<Integer, DateTime> roundDates = competitionDetails.getRoundDates();
        competitionDetails.getMatches().stream().filter(match -> match.getDate() == null).forEach(match -> {
            match.setDate(roundDates.get(match.getRound()));
        });
	}
}
