package cz.mikealdo.football.domain;

import org.joda.time.DateTime;

public class RoundDate {
	
	private Integer round;
	private DateTime date;

	public RoundDate(int round, DateTime date) {
		this.round = round;
		this.date = date;
	}

	public Integer getRound() {
		return round;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "RoundDate{" +
				"round=" + round +
				", date=" + date +
				'}';
	}
}
