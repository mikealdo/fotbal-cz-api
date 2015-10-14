package cz.mikealdo.fotbalcz.domain;

public class FotbalCzTeam {
	
	private Integer pairingId;
	private String teamNameToDisplay;
	private String nameInClub;
	private String description;
	private String pairingTeamName;

	public FotbalCzTeam(Integer pairingId, String teamNameToDisplay) {
		this.pairingId = pairingId;
		this.teamNameToDisplay = teamNameToDisplay;
	}

	public Integer getPairingId() {
		return pairingId;
	}

	public String getNameInClub() {
		return nameInClub;
	}

	public String getDescription() {
		return description;
	}

	public String getPairingTeamName() {
		return pairingTeamName;
	}

	public String getTeamNameToDisplay() {
		return teamNameToDisplay;
	}

	@Override
	public String toString() {
		return "FotbalCzTeam{" +
				"pairingId=" + pairingId +
				", nameInClub='" + nameInClub + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FotbalCzTeam that = (FotbalCzTeam) o;

		if (!pairingId.equals(that.pairingId)) return false;
		return teamNameToDisplay.equals(that.teamNameToDisplay);

	}

	@Override
	public int hashCode() {
		int result = pairingId.hashCode();
		result = 31 * result + teamNameToDisplay.hashCode();
		return result;
	}
}
