package com.kell.tp.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.kell.tp.domain.Day;
import com.kell.tp.domain.HoursRange;

public class GetDaysHoursQuotationsConfigurationService {

	private static Map<Day, Map<HoursRange, Float>> configuration;
	static {
		
		Map<HoursRange, Float> mo2Fri = new HashMap<>();
		mo2Fri.put(new HoursRange("00:00-00:00"), 20f);
		mo2Fri.put(new HoursRange("00:01-09:00"), 25f);
		mo2Fri.put(new HoursRange("09:01-18:00"), 15f);
		mo2Fri.put(new HoursRange("18:01-23:59"), 20f);
		mo2Fri = Collections.unmodifiableMap(mo2Fri);
		
		Map<HoursRange, Float> sasu = new HashMap<>();
		sasu.put(new HoursRange("00:00-00:00"), 25f);
		sasu.put(new HoursRange("00:01-09:00"), 30f);
		sasu.put(new HoursRange("09:01-18:00"), 20f);
		sasu.put(new HoursRange("18:01-32:59"), 25f);
		sasu = Collections.unmodifiableMap(sasu);
		
		configuration = new HashMap<>();
		
		Day d = Day.MONDAY;
		
		while(Day.SATURDAY != d){
		
			configuration.put(d, mo2Fri);
			
			d = d.next();
		}
		
		configuration.put(Day.SATURDAY, sasu);
		configuration.put(Day.SUNDAY, sasu);
		
		configuration = Collections.unmodifiableMap(configuration);
	}

	public Map<Day, Map<HoursRange, Float>> exec(){

		return configuration;
	}
}
