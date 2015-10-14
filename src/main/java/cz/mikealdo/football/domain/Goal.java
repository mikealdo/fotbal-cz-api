package cz.mikealdo.football.domain;

public class Goal {

	private GoalType goalType = GoalType.GOAL;
	private Integer playerId;
	private Integer minute;

	public Goal(GoalType goalType, Integer playerId, Integer minute) {
		this.goalType = goalType;
		this.playerId = playerId;
		this.minute = minute;
	}

	public GoalType getGoalType() {
		return goalType;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public Integer getMinute() {
		return minute;
	}
}
