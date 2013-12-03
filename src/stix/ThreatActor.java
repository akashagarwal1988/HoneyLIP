package stix;

import java.util.List;

public class ThreatActor {

		Identity identity;
	String intent;
	TTP ObservedTTP;
	String historicalCampaigns;
	String associatedActors;
	String handling;
	String confidence;
	List<String> activity;
	String informationSource;

	public Identity getIdentity() {
		return identity;
	}

	public void setIdentity(Identity identity) {
		this.identity = identity;
	}

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public TTP getObservedTTP() {
		return ObservedTTP;
	}

	public void setObservedTTP(TTP observedTTP) {
		ObservedTTP = observedTTP;
	}

	public String getHistoricalCampaigns() {
		return historicalCampaigns;
	}

	public void setHistoricalCampaigns(String historicalCampaigns) {
		this.historicalCampaigns = historicalCampaigns;
	}

	public String getAssociatedActors() {
		return associatedActors;
	}

	public void setAssociatedActors(String associatedActors) {
		this.associatedActors = associatedActors;
	}

	public String getHandling() {
		return handling;
	}

	public void setHandling(String handling) {
		this.handling = handling;
	}

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}

	public List<String> getActivity() {
		return activity;
	}

	public void setActivity(List<String> activity) {
		this.activity = activity;
	}

	public String getInformationSource() {
		return informationSource;
	}

	public void setInformationSource(String informationSource) {
		this.informationSource = informationSource;
	}

}
