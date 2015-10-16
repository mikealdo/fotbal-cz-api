package cz.mikealdo.football.domain;

public enum GoalType {
	GOAL("Branka"), PENALTY("Pokutový kop"), OWN("Vlastní");

	private String code;

	GoalType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public static GoalType getByCode(String code) {
		for (GoalType goalType : values()) {
			if (goalType.getCode().equals(code)) {
				return goalType;
			}
		}
		throw new IllegalArgumentException("Given goal code '"+code+"' is not supported");
	}
}
