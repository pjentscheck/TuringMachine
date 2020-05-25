/**
 * Copyright (c) 2020 Philip Jentscheck to Present. All rights reserved.
 */
package com.util.turingmachine;

import java.util.HashMap;
import java.util.Map;

public class TuringMachineState extends TuringMachineEntity {

	public final String name;
	public final Map<Character, TuringMachineTransition> transitions = new HashMap<Character, TuringMachineTransition>();
	public TuringMachineTransition defaultTransition;

	public TuringMachineState(final Map<String, Object> data, final String name) {
		super(data);
		this.name = name;
	}

	public TuringMachineTransition getTransaction(final char character) {
		if (this.transitions.get(character) == null) {
			return this.defaultTransition;
		} else {
			return this.transitions.get(character);
		}
	}

	public String getName() {
		return this.name;
	}
}
