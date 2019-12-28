package de.m3y3r.nbeep.frame.data;

import javax.xml.soap.MimeHeaders;

import de.m3y3r.nbeep.frame.Frame;

public abstract class AbstractDataFrame implements Frame {

	private MimeHeaders headers;

	int channel;
	int msgNo;
	boolean more;
	long seqNo;
	int size;

	private Object payload;
}
