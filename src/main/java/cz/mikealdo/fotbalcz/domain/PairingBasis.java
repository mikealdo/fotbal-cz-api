package cz.mikealdo.fotbalcz.domain;

import java.util.List;

import cz.mikealdo.football.domain.Arrival;

public class PairingBasis {

	String competitionHash;
	String competitionName;
	Integer competitionId;
	String pairingTeamName;
	String clubTeamName;
	String nameToDisplay;
	String competitionDescription;
	Integer firstId;
	List<Arrival> arrivals;

	public String getClubTeamName() {
		return clubTeamName;
	}

	public void setClubTeamName(String clubTeamName) {
		this.clubTeamName = clubTeamName;
	}

	public String getPairingTeamName() {
		return pairingTeamName;
	}

	public void setPairingTeamName(String pairingTeamName) {
		this.pairingTeamName = pairingTeamName;
	}

	public String getCompetitionHash() {
		return competitionHash;
	}

	public void setCompetitionHash(String competitionHash) {
		this.competitionHash = competitionHash;
	}

	public String getCompetitionName() {
		return competitionName;
	}

	public void setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
	}

	public Integer getCompetitionId() {
		return competitionId;
	}

	public void setCompetitionId(Integer competitionId) {
		this.competitionId = competitionId;
	}

	public String getCompetitionDescription() {
		return competitionDescription;
	}

	public void setCompetitionDescription(String competitionDescription) {
		this.competitionDescription = competitionDescription;
	}

	public Integer getFirstId() {
		return firstId;
	}

	public void setFirstId(Integer firstId) {
		this.firstId = firstId;
	}

	public String getNameToDisplay() {
		return nameToDisplay;
	}

	public void setNameToDisplay(String nameToDisplay) {
		this.nameToDisplay = nameToDisplay;
	}

	public List<Arrival> getArrivals() {
		return arrivals;
	}

	public void setArrivals(List<Arrival> arrivals) {
		this.arrivals = arrivals;
	}
}
