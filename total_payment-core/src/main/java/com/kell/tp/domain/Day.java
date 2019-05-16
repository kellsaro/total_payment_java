package com.kell.tp.domain;

import java.util.Optional;

public enum Day {

	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

	public Day next() {
		switch (this) {
		case MONDAY:
			return TUESDAY;
		case TUESDAY:
			return WEDNESDAY;
		case WEDNESDAY:
			return THURSDAY;
		case THURSDAY:
			return FRIDAY;
		case FRIDAY:
			return SATURDAY;
		case SATURDAY:
			return SUNDAY;
		default:
			return MONDAY;
		}
	}

	public static Optional<Day> parseFrom(String day) {

		switch (day) {
		case "MO":
			return Optional.of(MONDAY);
		case "TU":
			return Optional.of(TUESDAY);
		case "WE":
			return Optional.of(WEDNESDAY);
		case "TH":
			return Optional.of(THURSDAY);
		case "FR":
			return Optional.of(FRIDAY);
		case "SA":
			return Optional.of(SATURDAY);
		case "SU":
			return Optional.of(SUNDAY);
		}

		return Optional.empty();
	}
}
