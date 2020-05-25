/**
 * Copyright (c) 2020 Philip Jentscheck to Present. All rights reserved.
 */
package com.util.turingmachine;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TuringMachine extends TuringMachineEntity {

	private final Map<String, TuringMachineState> states = new HashMap<String, TuringMachineState>();
	private TuringMachineState startState;
	private TuringMachineState successState;
	private TuringMachineState errorState;
	private TuringMachineState currentState;

	public TuringMachine() {
		super(new HashMap<String, Object>());
	}

	public void addState(final String stateName) {
		if (this.states.get(stateName) == null) {
			final TuringMachineState state = new TuringMachineState(this.data, stateName);
			this.states.put(stateName, state);
		}
	}

	public void setStartState(final String startState) {
		addState(startState);
		this.startState = this.states.get(startState);
	}

	public void setSuccessState(final String successState) {
		addState(successState);
		this.successState = this.states.get(successState);
	}

	public void setErrorState(final String errorState) {
		addState(errorState);
		this.errorState = this.states.get(errorState);
	}

	public TuringMachineState getState(final String name) {
		return this.states.get(name);
	}

	public void addTransition(final String stateName1, final String stateName2) {
		addTransition(stateName1, null, stateName2, null);
	}

	public void addTransition(final String stateName1, final String stateName2,
			final TuringMachineAction turingMachineAction) {
		addTransition(stateName1, null, stateName2, turingMachineAction);
	}

	public void addTransition(final String stateName1, final Character transitionEquals, final String stateName2) {
		addTransition(stateName1, transitionEquals, stateName2, null);
	}

	public void addTransition(final String stateName1, final Character transitionEquals, final String stateName2,
			final TuringMachineAction turingMachineAction) {
		addState(stateName1);
		addState(stateName2);
		TuringMachineState state2 = getState(stateName2);
		final TuringMachineTransition transition = new TuringMachineTransition(this.data, transitionEquals, state2) {
			@Override
			public boolean matches(final Character character) {
				return this.character == null || this.character == character;
			}
		};
		if (turingMachineAction != null) {
			transition.setAction(turingMachineAction);
		}
		addTransition(stateName1, transition);
	}

	public void addTransition(final String stateName1, final TuringMachineTransition transition) {
		if (transition.character == null) {
			this.states.get(stateName1).defaultTransition = transition;
		} else {
			this.states.get(stateName1).transitions.put(transition.character, transition);
		}
	}

//	public Scanner scanner = new Scanner(System.in);
	public static boolean isDebugActive = false;
//	public int stop = 0;

	public void run(String value, final boolean failOnMissingTransition, final TuringMachineEvents events) {
		if (value == null || events == null) {
			return;
		}
		events.setData(this.data);
		events.onStart(value);
		this.currentState = this.startState;
		TuringMachineCursor cursor = new TuringMachineCursor(value.length());
		while (cursor.hasNext()) {
			final char character = value.charAt(cursor.getIndex());
			if (this.currentState.equals(this.successState)) {
				events.onSuccess(value, cursor);
				return;
			}
			if (this.currentState.equals(this.errorState)) {
				events.onError(value, cursor);
				return;
			}
			final TuringMachineTransition currentTransition = this.states.get(this.currentState.getName())
					.getTransaction(character);
			if (currentTransition == null) {
				if (failOnMissingTransition) {
					events.onFailure(value, cursor);
					return;
				} else {
					continue;
				}
			}
			int oldIndex = cursor.getIndex();
			if (currentTransition.matches(character)) {
				if (currentTransition.action != null) {
					value = currentTransition.action.execute(value, cursor);
				}
				if (value == null || value.isEmpty()) {
					events.onFailure(value, cursor);
					return;
				}
//				if (isDebugActive) {
//					final int stopStep = 10;
//					System.out.println();
//					System.out.println("Index: " + cursor.getIndex() + ", Current State: \""
//							+ this.currentState.getName() + "\", Current Transition: \"" + currentTransition.character
//							+ "\", Next State: \"" + currentTransition.nextState.getName() + "\"");
//					System.out.println("value.charAt(oldIndex) " + value.charAt(oldIndex) + ", value.charAt(newIndex) "
//							+ value.charAt(cursor.getIndex()));
//					System.out.println(value.substring(0, cursor.getIndex() + 1));
//					if (cursor.getIndex() >= this.stop) {
//						this.stop += stopStep;
//						this.scanner.nextLine();
//					}
//				}
				this.currentState = currentTransition.nextState;
			}
			cursor.next();
		}
		events.onEnd(value);
	}

	public static String generateStateName() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
