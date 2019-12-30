package de.m3y3r.nbeep.frame.data;

public abstract class DataFrame {

	private int channel;
	private int msgNo;
	private boolean more;
	private long seqNo;
	private int size;

	private Object payload;

	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}

	public int getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(int msgNo) {
		this.msgNo = msgNo;
	}

	public boolean isMore() {
		return more;
	}
	public void setMore(boolean more) {
		this.more = more;
	}

	public long getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(long seqNo) {
		this.seqNo = seqNo;
	}

	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	public Object getPayload() {
		return payload;
	}
	public void setPayload(Object payload) {
		this.payload = payload;
	}
}
