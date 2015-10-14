package cz.mikealdo.football.domain;

import cz.mikealdo.fotbalcz.domain.builder.PlayerBuilder;

public class Player extends SquadMember {

	private final PlayerPosition playerPosition;
	private final Integer dressNumber;
	private boolean captain;
	private boolean inMainLineUp;
	private Integer firstSubstitutionMinute;
	private Integer secondSubstitutionMinute;
	private Integer firstYellowCardInMinute;
	private Integer secondYellowCardInMinute;
	private Integer redCardInMinute;
	
	public Player(PlayerBuilder playerBuilder) {
		super(playerBuilder.getName(), playerBuilder.getFotbalCzId());
		this.playerPosition = playerBuilder.getPlayerPosition();
		this.dressNumber = playerBuilder.getDressNumber();
		this.captain = playerBuilder.isCaptain();
		this.inMainLineUp = playerBuilder.isInMainLineUp();
		this.firstSubstitutionMinute = playerBuilder.getFirstSubstitutionMinute();
		this.secondSubstitutionMinute = playerBuilder.getSecondSubstitutionMinute();
		this.firstYellowCardInMinute = playerBuilder.getFirstYellowCardInMinute();
		this.secondYellowCardInMinute = playerBuilder.getSecondYellowCardInMinute();
		this.redCardInMinute = playerBuilder.getRedCardInMinute();
	}


	public Player(String name, Integer fotbalCzId, String positionCode, Integer dressNumber) {
		super(name, fotbalCzId);
		this.playerPosition = PlayerPosition.getByPositionCode(positionCode);
		this.dressNumber = dressNumber;
	}

	public PlayerPosition getPlayerPosition() {
		return playerPosition;
	}

	public Integer getDressNumber() {
		return dressNumber;
	}

	public boolean isCaptain() {
		return captain;
	}

	public void setCaptain(boolean captain) {
		this.captain = captain;
	}

	public boolean isInMainLineUp() {
		return inMainLineUp;
	}

	public void setInMainLineUp(boolean inMainLineUp) {
		this.inMainLineUp = inMainLineUp;
	}

	public Integer getFirstSubstitutionMinute() {
		return firstSubstitutionMinute;
	}

	public void setFirstSubstitutionMinute(Integer firstSubstitutionMinute) {
		this.firstSubstitutionMinute = firstSubstitutionMinute;
	}

	public Integer getSecondSubstitutionMinute() {
		return secondSubstitutionMinute;
	}

	public void setSecondSubstitutionMinute(Integer secondSubstitutionMinute) {
		this.secondSubstitutionMinute = secondSubstitutionMinute;
	}

	public Integer getFirstYellowCardInMinute() {
		return firstYellowCardInMinute;
	}

	public void setFirstYellowCardInMinute(Integer firstYellowCardInMinute) {
		this.firstYellowCardInMinute = firstYellowCardInMinute;
	}

	public Integer getSecondYellowCardInMinute() {
		return secondYellowCardInMinute;
	}

	public void setSecondYellowCardInMinute(Integer secondYellowCardInMinute) {
		this.secondYellowCardInMinute = secondYellowCardInMinute;
	}

	public Integer getRedCardInMinute() {
		return redCardInMinute;
	}

	public void setRedCardInMinute(Integer redCardInMinute) {
		this.redCardInMinute = redCardInMinute;
	}

	@Override
	public String toString() {
		return "Player{" +
				"name=" + getName() +
				"fotbalCzId=" + getFotbalCzId() +
				"playerPosition=" + playerPosition +
				'}';
	}
}
