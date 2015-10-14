package cz.mikealdo.fotbalcz.domain.builder;

import java.util.Optional;

import cz.mikealdo.football.domain.Player;
import cz.mikealdo.football.domain.PlayerPosition;

public class PlayerBuilder {

	private final String name;
	private final Integer fotbalCzId;
	private PlayerPosition playerPosition;
	private Integer dressNumber;
	private boolean captain;
	private boolean inMainLineUp;
	private Integer firstSubstitutionMinute;
	private Integer secondSubstitutionMinute;
	private Integer firstYellowCardInMinute;
	private Integer secondYellowCardInMinute;
	private Integer redCardInMinute;

	public PlayerBuilder(String name, Integer fotbalCzId, String positionCode, Integer dressNumber) {
		this.name = name;
		this.fotbalCzId = fotbalCzId;
		this.playerPosition = PlayerPosition.getByPositionCode(positionCode);
		this.dressNumber = dressNumber;
	}

	public PlayerBuilder captain(boolean isCaptain) {
		this.captain = isCaptain;
		return this;
	}

	public PlayerBuilder inMainLineUp(boolean inMainLineUp) {
		this.inMainLineUp = inMainLineUp;
		return this;
	}	
	
	public PlayerBuilder firstSubstitutionMinute(Optional<Integer> substitutionMinute) {
		if (substitutionMinute.isPresent())
			this.firstSubstitutionMinute = substitutionMinute.get();
		return this;
	}

	public PlayerBuilder secondSubstitutionMinute(Optional<Integer> substitutionMinute) {
		if (substitutionMinute.isPresent())
			this.secondSubstitutionMinute = substitutionMinute.get();
		return this;
	}

	public PlayerBuilder firstYellowCardInMinute(Optional<Integer> firstYellowCardInMinute) {
		if (firstYellowCardInMinute.isPresent())
			this.firstYellowCardInMinute = firstYellowCardInMinute.get();
		return this;
	}


	public PlayerBuilder secondYellowCardInMinute(Optional<Integer> secondYellowCardInMinute) {
		if (secondYellowCardInMinute.isPresent())
			this.secondYellowCardInMinute = secondYellowCardInMinute.get();
		return this;
	}

	public PlayerBuilder redCardInMinute(Optional<Integer> redCardInMinute) {
		if (redCardInMinute.isPresent())
			this.redCardInMinute = redCardInMinute.get();
		return this;
	}
	
	public Player build() {
		return new Player(this);
	}

	public String getName() {
		return name;
	}

	public Integer getFotbalCzId() {
		return fotbalCzId;
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

	public boolean isInMainLineUp() {
		return inMainLineUp;
	}

	public Integer getFirstSubstitutionMinute() {
		return firstSubstitutionMinute;
	}

	public Integer getSecondSubstitutionMinute() {
		return secondSubstitutionMinute;
	}

	public Integer getFirstYellowCardInMinute() {
		return firstYellowCardInMinute;
	}

	public Integer getSecondYellowCardInMinute() {
		return secondYellowCardInMinute;
	}

	public Integer getRedCardInMinute() {
		return redCardInMinute;
	}
}
