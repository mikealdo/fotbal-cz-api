package cz.mikealdo.football.domain;

public class Referee extends SquadMember {

	private RefereePosition refereePosition;
	
	public Referee(String name, Integer fotbalCzId) {
		super(name, fotbalCzId);
	}

	public Referee(String name, Integer fotbalCzId, String positionCode) {
		super(name, fotbalCzId);
		this.refereePosition = RefereePosition.getByPositionCode(positionCode);
	}

	public RefereePosition getRefereePosition() {
		return refereePosition;
	}

	@Override
	public String toString() {
		return "Referee{" +
				"name=" + getName() +
				"fotbalCzId=" + getFotbalCzId() +
				"refereePosition=" + refereePosition +
				'}';
	}
}
