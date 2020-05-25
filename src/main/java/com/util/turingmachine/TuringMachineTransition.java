/**
 * Copyright (c) 2020 Philip Jentscheck to Present. All rights reserved.
 */
package com.util.turingmachine;

import java.util.Map;

public abstract class TuringMachineTransition extends TuringMachineEntity {
	public Character character;
	public final TuringMachineState nextState;
	public TuringMachineAction action;

	public TuringMachineTransition(final Map<String, Object> data, final Character character,
			final TuringMachineState nextState) {
		super(data);
		this.character = character;
		this.nextState = nextState;
	}

	public abstract boolean matches(final Character character);

	public void setAction(final TuringMachineAction action) {
		this.action = action;
		this.action.setData(this.data);
	}
}
