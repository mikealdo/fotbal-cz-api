package cz.mikealdo.football.domain;

public class SquadMember {
	
	private final String name;
	private final Integer fotbalCzId;

	public SquadMember(String name, Integer fotbalCzId) {
		this.name = name;
		this.fotbalCzId = fotbalCzId;
	}

	public String getName() {
		return name;
	}

	public Integer getFotbalCzId() {
		return fotbalCzId;
	}
}
