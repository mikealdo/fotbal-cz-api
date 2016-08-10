package cz.mikealdo.detailedleague;

import cz.mikealdo.football.domain.CompetitionDetails;
import cz.mikealdo.football.domain.League;
import org.joda.time.DateTime;

import java.util.Map;

public class LeagueWithDetails {

    private League league;

    public LeagueWithDetails(League league) {
        this.league = league;
    }

    public League enhanceByDetails(CompetitionDetails competitionDetails) {
        fillMatchesWithRoundDates(competitionDetails);
        league.setMatches(competitionDetails.getMatches());
		league.setTeams(competitionDetails.getTeams());
        league.setName(competitionDetails.getCompetitionName());
        league.setDescription(competitionDetails.getCompetitionDescription());
        return league;
	}

    private void fillMatchesWithRoundDates(CompetitionDetails competitionDetails) {
        Map<Integer, DateTime> roundDates = competitionDetails.getRoundDates();
        competitionDetails.getMatches().stream().filter(match -> match.getDate() == null).forEach(match -> {
            match.setDate(roundDates.get(match.getRound()));
        });
	}
}
