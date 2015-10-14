package cz.mikealdo.fotbalcz.domain;

import java.util.LinkedList;
import java.util.List;

public class FotbalCzLeague {

	private Long id;
	private FotbalCzTeam teamForPair;
	private String name;
	private String description;
	private List<FotbalCzTeam> teams = new LinkedList<>();
	private List<FotbalCzMatch> matches = new LinkedList<>();

	public FotbalCzLeague(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FotbalCzTeam getTeamForPair() {
		return teamForPair;
	}

	public void setTeamForPair(FotbalCzTeam teamForPair) {
		this.teamForPair = teamForPair;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<FotbalCzTeam> getTeams() {
		return teams;
	}

	public void setTeams(List<FotbalCzTeam> teams) {
		this.teams = teams;
	}

	public List<FotbalCzMatch> getMatches() {
		return matches;
	}

	public void setMatches(List<FotbalCzMatch> matches) {
		this.matches = matches;
	}
}
