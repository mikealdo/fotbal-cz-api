package cz.mikealdo.football.domain;

public enum RefereePosition {
	R("R"), AR1("AR1"), AR2("AR2"), R4("4R"), DFA("DFA"), TD("TD");
	
	private String positionCode;

	RefereePosition(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public static RefereePosition getByPositionCode(String positionCode) {
		for (RefereePosition refereePosition : values()) {
			if (refereePosition.getPositionCode().equals(positionCode)) {
				return refereePosition;
			}
		}
		throw new IllegalArgumentException("Given position code is not supported");
	}
}
