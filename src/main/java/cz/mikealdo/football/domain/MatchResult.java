package cz.mikealdo.football.domain;

import java.util.List;
import java.util.Objects;

public class MatchResult {
	
	private Long homeGoals;
	private Long visitorGoals;
	private boolean forfeited;
	private boolean resultEntered;
	private boolean penaltyShootout;
	private Long homeGoalsInPenaltyShootout;
	private Long visitorGoalsInPenaltyShootout;
	private Long spectators;
	private List<Referee> referees;
	
	private List<Player> homeLineUp;
	private List<Player> visitorLineUp;
	private List<Goal> homeShooters;
	private List<Goal> visitorShooters;
	
	
	public MatchResult(String simpleResult) {
		simpleResult = simpleResult.trim();
		simpleResult = simpleResult.replaceAll(" ", "");
		String[] goals = simpleResult.split(":");
		if (goals.length != 2) {
			throw new IllegalArgumentException("Match result string is not parseable");
		}
		this.homeGoals = Long.valueOf(goals[0]);
		this.visitorGoals = Long.valueOf(goals[1]);
		this.resultEntered = true;
	}

	public MatchResult(String simpleResult, String penaltiesResult) {
		this(simpleResult);
		updateResultWithPenalties(penaltiesResult);
	}

	public void updateResultWithPenalties(String penaltiesResult) {
		String[] penaltyShootoutGoals = penaltiesResult.split(":");
		if (penaltyShootoutGoals.length != 3 && !penaltyShootoutGoals[0].equals("(PK")) {
			throw new IllegalArgumentException("Result is not parseable");
		}
		this.homeGoalsInPenaltyShootout = Long.valueOf(penaltyShootoutGoals[1]);
		this.visitorGoalsInPenaltyShootout = Long.valueOf(penaltyShootoutGoals[2].replace(")",""));
		this.penaltyShootout = true;
	}

	public Long getHomeGoals() {
		return homeGoals;
	}

	public Long getVisitorGoals() {
		return visitorGoals;
	}

	public boolean isForfeited() {
		return forfeited;
	}

	public boolean isResultEntered() {
		return resultEntered;
	}

	public boolean isPenaltyShootout() {
		return penaltyShootout;
	}

	public Long getHomeGoalsInPenaltyShootout() {
		return homeGoalsInPenaltyShootout;
	}

	public Long getVisitorGoalsInPenaltyShootout() {
		return visitorGoalsInPenaltyShootout;
	}

	public boolean isDraw() {
		return Objects.equals(this.homeGoals, this.visitorGoals);
	}

	public void setHomeGoals(Long homeGoals) {
		this.homeGoals = homeGoals;
	}

	public void setVisitorGoals(Long visitorGoals) {
		this.visitorGoals = visitorGoals;
	}

	public void setForfeited(boolean forfeited) {
		this.forfeited = forfeited;
	}

	public void setResultEntered(boolean resultEntered) {
		this.resultEntered = resultEntered;
	}

	public void setPenaltyShootout(boolean penaltyShootout) {
		this.penaltyShootout = penaltyShootout;
	}

	public void setHomeGoalsInPenaltyShootout(Long homeGoalsInPenaltyShootout) {
		this.homeGoalsInPenaltyShootout = homeGoalsInPenaltyShootout;
	}

	public void setVisitorGoalsInPenaltyShootout(Long visitorGoalsInPenaltyShootout) {
		this.visitorGoalsInPenaltyShootout = visitorGoalsInPenaltyShootout;
	}

	public Long getSpectators() {
		return spectators;
	}

	public void setSpectators(Long spectators) {
		this.spectators = spectators;
	}

	public List<Referee> getReferees() {
		return referees;
	}

	public void setReferees(List<Referee> referees) {
		this.referees = referees;
	}

	public List<Player> getHomeLineUp() {
		return homeLineUp;
	}

	public void setHomeLineUp(List<Player> homeLineUp) {
		this.homeLineUp = homeLineUp;
	}

	public List<Player> getVisitorLineUp() {
		return visitorLineUp;
	}

	public void setVisitorLineUp(List<Player> visitorLineUp) {
		this.visitorLineUp = visitorLineUp;
	}

	public List<Goal> getHomeShooters() {
		return homeShooters;
	}

	public void setHomeShooters(List<Goal> homeShooters) {
		this.homeShooters = homeShooters;
	}

	public List<Goal> getVisitorShooters() {
		return visitorShooters;
	}

	public void setVisitorShooters(List<Goal> visitorShooters) {
		this.visitorShooters = visitorShooters;
	}

    public String prettyPrintSimpleResult() {
        return homeGoals+":"+visitorGoals+((penaltyShootout)?"p":"")+((forfeited)?"k":"");
    }
}
