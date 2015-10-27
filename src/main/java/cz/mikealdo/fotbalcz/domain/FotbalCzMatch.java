package cz.mikealdo.fotbalcz.domain;

import org.joda.time.DateTime;

import cz.mikealdo.football.domain.MatchResult;

public class FotbalCzMatch {
	
	DateTime date;
	FotbalCzTeam homeTeam;
	FotbalCzTeam visitorTeam;
	MatchResult result;
	Integer round;

    public FotbalCzMatch() {
    }

    public FotbalCzMatch(DateTime date, FotbalCzTeam homeTeam, FotbalCzTeam visitorTeam, MatchResult result, Integer round) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.visitorTeam = visitorTeam;
        this.result = result;
        this.round = round;
    }

    public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public FotbalCzTeam getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(FotbalCzTeam homeTeam) {
		this.homeTeam = homeTeam;
	}

	public FotbalCzTeam getVisitorTeam() {
		return visitorTeam;
	}

	public void setVisitorTeam(FotbalCzTeam visitorTeam) {
		this.visitorTeam = visitorTeam;
	}

	public MatchResult getResult() {
		return result;
	}

	public void setResult(MatchResult result) {
		this.result = result;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	@Override
	public String toString() {
		return "FotbalCzMatch{" +
				"date=" + date +
				", homeTeam=" + homeTeam +
				", visitorTeam=" + visitorTeam +
				", round=" + round +
				", result='" + result + '\'' +
				'}';
	}

	public void updateResult(MatchResult matchResult) {
		if (matchResult == null) {
			return;
		}
		this.setResult(matchResult);
	}
}
