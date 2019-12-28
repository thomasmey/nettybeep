package de.m3y3r.nbeep.profile.channel.mgmt.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "close")
@XmlAccessorType(XmlAccessType.FIELD)
public class Close {

	@XmlAttribute
	private int number;

	@XmlAttribute
	private int code; //ReplyCodes

}
