package cz.mikealdo.football.domain;

public enum PlayerPosition {
	GOALKEEPER("B"), DEFENDER("O"), MIDFIELDER("Z"), FORWARD("Ú"), UNDEFINED("N");
	
	private String positionCode;

	PlayerPosition(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public static PlayerPosition getByPositionCode(String positionCode) {
		for (PlayerPosition playerPosition : values()) {
			if (playerPosition.getPositionCode().equals(positionCode)) {
				return playerPosition;
			}
		}
		throw new IllegalArgumentException("Given position code '"+positionCode+"' is not supported");
	}
}
