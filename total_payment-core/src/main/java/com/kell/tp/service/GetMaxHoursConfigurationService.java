package com.kell.tp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kell.tp.domain.Day;
import com.kell.tp.domain.Hour;
import com.kell.tp.domain.HoursRange;

public class GetMaxHoursConfigurationService {
	
	private final GetDaysHoursQuotationsConfigurationService getDaysHoursQuotationsConfigurationService;
	
	public GetMaxHoursConfigurationService() {
		getDaysHoursQuotationsConfigurationService = new GetDaysHoursQuotationsConfigurationService();
	}

	public List<Hour> exec(Day day){
		
		Map<Day, Map<HoursRange, Float>> configuration = getDaysHoursQuotationsConfigurationService.exec();
		
		Map<HoursRange, Float> hoursRangeToQuotationMap = configuration.get(day);
		
		List<Hour> result = new ArrayList<>();
		result.add(new Hour("00:00"));
		result.add(new Hour("23:59"));
		
		if(hoursRangeToQuotationMap != null) {
			for(HoursRange range: hoursRangeToQuotationMap.keySet()) {
				if(!result.contains(range.getMax())) {
					result.add(range.getMax());
				}
			}
		}
		
		result.sort((a, b)-> a.compareTo(b));
		
		return result;
	}
}
