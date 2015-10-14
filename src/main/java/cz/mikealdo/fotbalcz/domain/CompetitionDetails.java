package cz.mikealdo.fotbalcz.domain;

import java.util.List;

import cz.mikealdo.football.domain.Arrival;
import cz.mikealdo.football.domain.RoundDate;

public class CompetitionDetails {

	String competitionName;
	List<FotbalCzMatch> matches;
	List<FotbalCzTeam> teams;
	List<RoundDate> roundDates;
	List<Arrival> arrivals;

	public List<FotbalCzMatch> getMatches() {
		return matches;
	}

	public void setMatches(List<FotbalCzMatch> matches) {
		this.matches = matches;
	}

	public List<FotbalCzTeam> getTeams() {
		return teams;
	}

	public void setTeams(List<FotbalCzTeam> teams) {
		this.teams = teams;
	}

	public List<RoundDate> getRoundDates() {
		return roundDates;
	}

	public void setRoundDates(List<RoundDate> roundDates) {
		this.roundDates = roundDates;
	}

	public List<Arrival> getArrivals() {
		return arrivals;
	}

	public void setArrivals(List<Arrival> arrivals) {
		this.arrivals = arrivals;
	}

	public String getCompetitionName() {
		return competitionName;
	}

	public void setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
	}
}
