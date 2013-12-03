package stix;

import java.util.Date;

public class Record {
	
	ThreatActor threatActor;
	Date timeStamp;
	String moduleName;
	
	public Record(String protocol){
		threatActor = new ThreatActor();
		timeStamp = new Date();
		this.moduleName = protocol;
	}

	public ThreatActor getThreatActor() {
		return threatActor;
	}

	public void setThreatActor(ThreatActor threatActor) {
		this.threatActor = threatActor;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	

}
