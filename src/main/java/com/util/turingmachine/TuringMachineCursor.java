/**
 * Copyright (c) 2020 Philip Jentscheck to Present. All rights reserved.
 */
package com.util.turingmachine;

public class TuringMachineCursor {
	private int length;
	private int index;
	private Direction direction;

	public TuringMachineCursor(final int length) {
		this.length = length;
		this.index = 0;
		this.direction = Direction.FORWARD;
	}

	public boolean hasNext() {
		return this.index < this.length;
	}

	public void next() {
		if (this.direction == Direction.FORWARD) {
			this.index++;
		} else {
			this.index--;
		}
	}

	public void addDiff(final int diff) {
		this.index += diff;
		this.length += diff;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(final int length) {
		this.length = length;
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public Direction getDirection() {
		return this.direction;
	}

	public void setDirection(final Direction direction) {
		this.direction = direction;
	}

	public enum Direction {
		FORWARD, BACKWARD
	}
}
