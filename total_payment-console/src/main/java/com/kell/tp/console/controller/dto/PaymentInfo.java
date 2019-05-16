package com.kell.tp.console.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class PaymentInfo {

	private final String name;
	private final float ammount;
}
