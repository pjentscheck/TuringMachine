/**
 * Copyright (c) 2020 Philip Jentscheck to Present. All rights reserved.
 */
package com;

import com.util.turingmachine.TuringMachine;
import com.util.turingmachine.TuringMachineAction;
import com.util.turingmachine.TuringMachineCursor;
import com.util.turingmachine.TuringMachineEvents;

public class Main {
	public static void main(final String[] args) {
		exampleExcel();
		exampleTranslation();
	}

	private static void exampleTranslation() {
		TuringMachine turingMachine = new TuringMachine();
		final TuringMachineAction captureValue = new TuringMachineAction() {
			public String execute(final String value, final TuringMachineCursor cursor) {
				final String dataKey = "functionName";
				String string = getDataString(dataKey);
				if (string == null) {
					string = value.substring(cursor.getIndex(), cursor.getIndex() + 1);
				} else {
					string += value.substring(cursor.getIndex(), cursor.getIndex() + 1);
				}
				setData(dataKey, string);
				return value;
			}
		};

		turingMachine.setStartState("q1");
		turingMachine.addTransition("q1", "q1", captureValue);
		turingMachine.addTransition("q1", ')', "q2");
		turingMachine.addTransition("q1", '=', "q2");
		turingMachine.addTransition("q1", ' ', "q2");
		turingMachine.addTransition("q2", "q2");

		final TuringMachineEvents events = new TuringMachineEvents() {

			public void onSuccess(final String value, final TuringMachineCursor cursor) {
				System.out.println("onSuccess");
			}

			public void onStart(final String value) {
				System.out.println("onStart");
				System.out.println("value=" + value);
			}

			public void onError(final String value, final TuringMachineCursor cursor) {
				System.out.println("onError");
			}

			public void onEnd(final String value) {
				System.out.println("onEnd");
				System.out.println("value=" + value);
				System.out.println("captureValue " + this.data.get("functionName"));
			}

			public void onFailure(final String value, final TuringMachineCursor cursor) {
				System.out.println("onFailure");
			}
		};
		turingMachine.run("getTranslationSimple)(\"tasks_equipment_status_header_label\", \"CONFIRM ASSET MODE\"));",
				true, events);
	}

	private static void exampleExcel() {
		TuringMachine turingMachine = new TuringMachine();
		final TuringMachineAction addQuote = new TuringMachineAction() {

			public String execute(final String value, final TuringMachineCursor cursor) {
				return value.substring(0, cursor.getIndex()) + "'" + value.substring(cursor.getIndex(), value.length());
			}

		};
		turingMachine.setStartState("q1");

		turingMachine.addTransition("q1", '"', "q2");
		turingMachine.addTransition("q2", '=', "q3", addQuote);
		turingMachine.addTransition("q2", '+', "q3", addQuote);
		turingMachine.addTransition("q2", '-', "q3", addQuote);
		turingMachine.addTransition("q2", '@', "q3", addQuote);
		turingMachine.addTransition("q2", '"', "q4");
		turingMachine.addTransition("q2", "q3");
		turingMachine.addTransition("q3", "q3");
		turingMachine.addTransition("q3", '"', "q4");
		turingMachine.addTransition("q4", '"', "q5");
		turingMachine.addTransition("q4", ',', "q1");
		turingMachine.addTransition("q5", '"', "q4");
		turingMachine.addTransition("q5", "q3");

		final TuringMachineEvents events = new TuringMachineEvents() {

			public void onSuccess(final String value, final TuringMachineCursor cursor) {
				System.out.println("onSuccess");
			}

			public void onStart(final String value) {
				System.out.println("onStart");
				System.out.println("value=" + value);
			}

			public void onError(final String value, final TuringMachineCursor cursor) {
				System.out.println("onError");
			}

			public void onEnd(final String value) {
				System.out.println("onEnd");
				System.out.println("value=" + value);
			}

			public void onFailure(final String value, final TuringMachineCursor cursor) {
				System.out.println("onFailure");
			}
		};
		turingMachine.run("\"=abc\",\"+cba\",\",\"\"=xyz\",\"123\"", true, events);
	}
}
