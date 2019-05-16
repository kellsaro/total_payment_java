package com.kell.tp.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HoursRange implements Cloneable{
	
	private final Hour min;
	private final Hour max;

	public HoursRange(String hoursRange) {
		String[] hours = hoursRange.split("-");
		min = new Hour(hours[0]);
		max = new Hour(hours[1]);
	}

	public List<HoursRange> splitByMaxHours(List<Hour> maxHours) {
		
		List<HoursRange> result = new ArrayList<>();
		
		if(maxHours == null || maxHours.isEmpty()) {
			result.add(this);
			return result;
		}
		
		List<Hour> mHours = maxHours.subList(0, maxHours.size());
		mHours.sort((a, b) -> a.compareTo(b));
		
		HoursRange range = clone();
		
		HoursRange r1;
		
		for(Hour maxHour: mHours) {
			
			if(range != null && range.contains(maxHour)) {
				
				r1 = new HoursRange(range.min, maxHour);
				result.add(r1);
				
				if(!Hour.MAX.equals(maxHour) && maxHour.next().compareTo(range.max) < 0) {
					
					range = new HoursRange(maxHour.next(), range.max);
				}
				else {
					range = null;
				}
			}
		}
		
		if (range != null) result.add(range);
		
		return result;
	}
	
	public int toMinutes() {
		return max.toMinutes() - min.toMinutes();
	}

	public boolean contains(HoursRange hoursRange) {
		if(hoursRange == null) return false;
		
		return min.compareTo(hoursRange.min) <= 0 && max.compareTo(hoursRange.max) >= 0;
	}
	
	public boolean contains(Hour hour) {
		if(hour == null) return false;
		
		return min.compareTo(hour) <= 0 && max.compareTo(hour) >= 0;
	}
	
	@Override
	public String toString() {
		return String.format("%s-%s", min, max);
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == null || !(other instanceof HoursRange)) return false;
		
		HoursRange hr = (HoursRange) other;
		
		return min.equals(hr.min) && max.equals(hr.max);
	}
	
	@Override
	public HoursRange clone() {
		return new HoursRange(toString());
	}
}
