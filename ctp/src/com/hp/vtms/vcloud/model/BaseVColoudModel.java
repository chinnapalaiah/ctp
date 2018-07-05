package com.hp.vtms.vcloud.model;

import java.io.InputStream;
import java.io.Serializable;

public class BaseVColoudModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7013026014183128779L;

	private InputStream in;

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	@Override
	public String toString() {
		return "BaseVColoudModel [in=" + in + "]";
	}

}
