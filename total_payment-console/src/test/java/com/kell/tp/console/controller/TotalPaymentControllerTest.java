package com.kell.tp.console.controller;

import org.junit.jupiter.api.Test;

import com.kell.tp.console.controller.dto.PaymentInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalPaymentControllerTest {

	private final TotalPaymentController controller = new TotalPaymentController();

	public void testShow() {
		PaymentInfo expected = new PaymentInfo("RENE", 215f);
		
		PaymentInfo computed = controller.show("RENE=MO10:00-12:00,TU10:00-12:00,TH01:00-03:00,SA14:00-18:00,SU20:00-21:00");
		assertEquals(expected, computed);

	    expected = new PaymentInfo("ASTRID", 85f);
	    computed = controller.show("ASTRID=MO10:00-12:00,TH12:00-14:00,SU20:00-21:00");
	    assertEquals(expected, computed);
	}
}
