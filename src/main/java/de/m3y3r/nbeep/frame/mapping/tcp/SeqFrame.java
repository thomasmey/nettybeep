package de.m3y3r.nbeep.frame.mapping.tcp;

// rfc3081 - 3.1.3
public class SeqFrame {

	// type "SEQ"
	private int channel;
	private long ackNo; // == seqNo
	private int window; // == size

	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public long getAckNo() {
		return ackNo;
	}
	public void setAckNo(long ackNo) {
		this.ackNo = ackNo;
	}
}
