package com.kell.tp.domain;

import lombok.Getter;

@Getter
public final class Hour implements Comparable<Hour>, Cloneable{

	public static final Hour MIN = new Hour("00:00");
	public static final Hour MAX = new Hour("23:59");
	
	private final int hours;
	private final int minutes;
	
	public Hour(String hour) {
		String[] hm = hour.split(":");
		hours = Integer.parseInt(hm[0]);
		minutes = Integer.parseInt(hm[1]);
	}
	
	public Hour(int hours, int minutes) {
		
		this.minutes = minutes % 60;
		this.hours = (hours + (minutes / 60)) % 24;
	}
	
	public int toMinutes() {
		return hours * 60 + minutes;
	}
	
	@Override
	public String toString() {
		return String.format("%02d:%02d", hours, minutes);
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public int compareTo(Hour o) {
		
		return ((Integer)toMinutes()).compareTo(o.toMinutes());
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o == null || !(o instanceof Hour)) return false;
		
		return toString().equals(o.toString());
	}

	public Hour next() {
		
		return new Hour(hours, minutes + 1);
	}
	
	@Override
	public Hour clone() {
		
		return new Hour(hours, minutes);
	}
}
