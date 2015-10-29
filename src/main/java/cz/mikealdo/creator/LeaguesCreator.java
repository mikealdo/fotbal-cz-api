package cz.mikealdo.creator;

import cz.mikealdo.fotbalcz.api.CompetitionSettings;
import cz.mikealdo.fotbalcz.domain.CompetitionDetails;
import cz.mikealdo.fotbalcz.domain.FotbalCzMatch;
import cz.mikealdo.parser.MatchStatisticsParser;
import cz.mikealdo.fotbalcz.domain.FotbalCzLeague;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LeaguesCreator {

	private MatchStatisticsParser parser;

    @Autowired
    public LeaguesCreator(MatchStatisticsParser parser) {
        this.parser = parser;
    }

    public FotbalCzLeague createLeague(String competitionHash, CompetitionSettings settings) {
		CompetitionDetails competitionDetails = parser.createCompetitionDetailsFrom(competitionHash);
		FotbalCzLeague league = new FotbalCzLeague(competitionDetails.getCompetitionName());
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
