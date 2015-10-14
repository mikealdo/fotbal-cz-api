package cz.mikealdo.football.domain;

import org.joda.time.LocalTime;

public class Arrival {
	
	Integer round;
	LocalTime time;

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Arrival{" +
				"round=" + round +
				", time=" + time +
				'}';
	}
}
