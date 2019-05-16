package com.kell.tp.console;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import com.kell.tp.console.controller.TotalPaymentController;
import com.kell.tp.console.controller.dto.PaymentInfo;

public class Application {

	private static PrintStream o = System.out;
	
	private final TotalPaymentController totalPaymentController;
	public Application() {
		totalPaymentController = new TotalPaymentController();
	}

	public static void main(String[] args) {

		if (args == null || args.length == 0)
			printHelp();
		else {
			
			Application app = new Application();
			for (String fileName : args)
				app.processFile(fileName);
		}
	}

	private void processFile(String fileName) {
		o.println(String.format("Processing file %s:", fileName));

		String line = null;
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			while ((line = br.readLine()) != null) {
				processWorkedSchedule(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processWorkedSchedule(String workedScheduleLine) {
		PaymentInfo payment =  totalPaymentController.show(workedScheduleLine);
		
		o.println(String.format("  The amount to pay %s is: %.2f USD", payment.getName(), payment.getAmmount()));
	}
	
	private static void printHelp() {

		StringBuffer help = new StringBuffer();

		String line = null;
		try (InputStream is = Application.class.getResourceAsStream("/help/help_en.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(is));) {

			while ((line = br.readLine()) != null) {
				help.append(String.format("%s%s", line, "\n"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		o.println(help.toString());
	}
}
