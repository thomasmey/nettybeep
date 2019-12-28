package de.m3y3r.nbeep.profile.channel.mgmt.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "greeting")
@XmlAccessorType(XmlAccessType.FIELD)
public class Greeting {

	@XmlElement(name = "profile")
	private List<Profile> profiles;

	@XmlAttribute(name = "features")
	private String features;

	@XmlAttribute
	private String localize;

	public List<Profile> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
}
