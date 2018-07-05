package com.hp.vtms.model;

import java.io.Serializable;

public class InstructorGloab implements Serializable {
	
/**
	 * 
	 */
private static final long serialVersionUID = -1392786401935675298L;

private Boolean gloabRdp;
	
private Boolean gloabConsole;


private Boolean gloabOption;

private String instructorName;

public Boolean getGloabRdp() {
	return gloabRdp;
}

public void setGloabRdp(Boolean gloabRdp) {
	this.gloabRdp = gloabRdp;
}

public Boolean getGloabConsole() {
	return gloabConsole;
}

public void setGloabConsole(Boolean gloabConsole) {
	this.gloabConsole = gloabConsole;
}

public Boolean getGloabOption() {
	return gloabOption;
}

public void setGloabOption(Boolean gloabOption) {
	this.gloabOption = gloabOption;
}

public String getInstructorName() {
	return instructorName;
}

public void setInstructorName(String instructorName) {
	this.instructorName = instructorName;
}


}
