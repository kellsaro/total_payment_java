package com.kell.tp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.kell.tp.domain.Day;
import com.kell.tp.domain.Hour;
import com.kell.tp.domain.HoursRange;

public class TotalPaymentService {

	private final Map<Day, Map<HoursRange, Float>> quotationsByDay;
	private final GetDaysHoursQuotationsConfigurationService getDaysHoursQuotationsConfigurationService;
	private final GetMaxHoursConfigurationService getMaxHoursConfigurationService;

	public TotalPaymentService() {

		getDaysHoursQuotationsConfigurationService = new GetDaysHoursQuotationsConfigurationService();
		quotationsByDay = getDaysHoursQuotationsConfigurationService.exec();
		
		getMaxHoursConfigurationService = new GetMaxHoursConfigurationService();
	}

	/**
	 * Returns the payment for the worked schedule string.
	 * 
	 * @return float with the calculated payment
	 */
	public float exec(String workedSchedule) {

		List<Map<String, String>> daysHoursList = toListOfDaysAndWorkedHours(workedSchedule);

		Map<Day, List<HoursRange>> hoursRangesGroupedByDay = toWorkedHoursGroupedByDay(daysHoursList);

		float ammount = 0;

		for (Entry<Day, List<HoursRange>> hourRangesByDay : hoursRangesGroupedByDay.entrySet()) {

			for (HoursRange workedHours : hourRangesByDay.getValue()) {
				ammount += computeAmmount(hourRangesByDay.getKey(), workedHours);
			}
		}

		return ammount;
	}

	/*
	 * For a Day and a HoursRange computes the corresponding payment
	 */
	private float computeAmmount(Day day, HoursRange range) {

		// A list of upper bound hours for each range
		List<Hour> maxHours = getMaxHoursConfigurationService.exec(day);

		// Split the range in subrange by the upper bound hours
		List<HoursRange> workedRanges = range.splitByMaxHours(maxHours);

		// Gets the quotation for this day
		Map<HoursRange, Float> quotation = quotationsByDay(day);
		if (quotation == null)
			return 0;

		float ammount = 0;

		for (HoursRange workedRange : workedRanges) {

			for (Entry<HoursRange, Float> entry : quotation.entrySet()) {
				if (entry.getKey().contains(workedRange)) {
					ammount += (entry.getValue() * workedRange.toMinutes()) / 60;
				}
			}
		}

		return ammount;
	}

	private Map<HoursRange, Float> quotationsByDay(Day d) {

		Map<HoursRange, Float> quotations = quotationsByDay.get(d);

		return quotations;
	}

	private Map<Day, List<HoursRange>> toWorkedHoursGroupedByDay(List<Map<String, String>> daysHoursList) {

		Map<Day, List<HoursRange>> workedHoursGroupedByDay = new HashMap<>();

		Optional<Day> day;
		// Groups all worked hours by Day
		for (Map<String, String> dayWorkedHoursHash : daysHoursList)
			for (Entry<String, String> dayWorkedHours : dayWorkedHoursHash.entrySet()) {

				day = Day.parseFrom(dayWorkedHours.getKey());

				if (day.isPresent()) {

					if (!workedHoursGroupedByDay.containsKey(day.get())) {
						workedHoursGroupedByDay.put(day.get(), new ArrayList<>());
					}

					workedHoursGroupedByDay.get(day.get()).add(new HoursRange(dayWorkedHours.getValue()));
				}
			}

		return workedHoursGroupedByDay;
	}

	/*
	 * Returns a List of hashes where the key is the String abbreviation of the day
	 * and the value is the String with worked hours
	 */
	private List<Map<String, String>> toListOfDaysAndWorkedHours(String workedSchedule) {

		if (workedSchedule == null)
			return new ArrayList<>();

		String[] schedules = workedSchedule.split(",");
		List<String> schedulesList = Arrays.asList(schedules);

		List<Map<String, String>> daysHoursList;

		Function<String, Map<String, String>> toListOfDayToScheduleMap = (schedule) -> Map.of(schedule.substring(0, 2),
				schedule.substring(2));

		daysHoursList = schedulesList.stream().map(toListOfDayToScheduleMap).collect(Collectors.toList());

		return daysHoursList;
	}
}