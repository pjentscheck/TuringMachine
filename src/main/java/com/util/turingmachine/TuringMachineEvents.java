/**
 * Copyright (c) 2020 Philip Jentscheck to Present. All rights reserved.
 */
package com.util.turingmachine;

public abstract class TuringMachineEvents extends TuringMachineEntity {
	public TuringMachineEvents() {
		super(null);
	}

	public abstract void onStart(final String value);

	public abstract void onSuccess(final String value, final TuringMachineCursor cursor);

	public abstract void onError(final String value, final TuringMachineCursor cursor);

	public abstract void onEnd(final String value);

	public abstract void onFailure(final String value, final TuringMachineCursor cursor);
}
