package com.kell.tp.console.controller;

import com.kell.tp.console.controller.dto.PaymentInfo;
import com.kell.tp.service.TotalPaymentService;

public class TotalPaymentController {
	
	private final TotalPaymentService totalPaymentService;
	
	public TotalPaymentController() {
		totalPaymentService = new TotalPaymentService();
	}

	public PaymentInfo show(String workedScheduleLine) {
		
		String[] splittedLine = workedScheduleLine.split("=");
		
		String name = splittedLine[0];
		String workedSchedule = splittedLine[1];
		
		PaymentInfo paymentInfo = new PaymentInfo(name, totalPaymentService.exec(workedSchedule));
		
		return paymentInfo;
	}
}
