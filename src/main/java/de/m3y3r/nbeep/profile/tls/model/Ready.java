package de.m3y3r.nbeep.profile.tls.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ready")
public class Ready {

	@XmlAttribute(name = "version", required = false)
	private String version;

}
