/**
 * Copyright (c) 2020 Philip Jentscheck to Present. All rights reserved.
 */
package com.util.turingmachine;

import java.util.List;
import java.util.Map;

public abstract class TuringMachineEntity {
	protected Map<String, Object> data;

	public TuringMachineEntity(final Map<String, Object> data) {
		this.data = data;
	}

	protected void setData(final Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, Object> getData() {
		return this.data;
	}

	public String getDataString(final String key) {
		Object obj = this.data.get(key);
		if (obj == null) {
			return null;
		} else {
			if (obj instanceof String) {
				return (String) obj;
			} else {
				return null;
			}
		}
	}

	public List getDataList(final String key) {
		Object obj = this.data.get(key);
		if (obj == null) {
			return null;
		} else {
			if (obj instanceof List) {
				return (List) obj;
			} else {
				return null;
			}
		}
	}

	public void setData(final String key, final Object value) {
		this.data.put(key, value);
	}
}
