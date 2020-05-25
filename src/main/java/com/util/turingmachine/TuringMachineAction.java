/**
 * Copyright (c) 2020 Philip Jentscheck to Present. All rights reserved.
 */
package com.util.turingmachine;

public abstract class TuringMachineAction extends TuringMachineEntity {

	public TuringMachineAction() {
		super(null);
	}

	public abstract String execute(String value, TuringMachineCursor cursor);
}
