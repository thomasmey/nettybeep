package de.m3y3r.nbeep.frame.mapping.tcp;

import de.m3y3r.nbeep.frame.Frame;

// rfc3081 - 3.1.3
public class SeqFrame implements Frame {

	// type "SEQ"
	int channel;
	long ackNo; // == seqNo
	int window; // == size

}
