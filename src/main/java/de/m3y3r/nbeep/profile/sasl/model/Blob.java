package de.m3y3r.nbeep.profile.sasl.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "blob")
@XmlAccessorType(XmlAccessType.FIELD)
public class Blob {

	@XmlEnum
	public enum Status {
		@XmlEnumValue("abort") ABORT,
		@XmlEnumValue("complete") COMPLETE,
		@XmlEnumValue("continue") CONTINUE
	};

	@XmlAttribute(name = "status", required = false)
	private Status status;

	@XmlValue
	private byte[] content;

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
}
