package de.m3y3r.nbeep.frame.data;

import de.m3y3r.nbeep.frame.data.AbstractDataFrame.Type;

public class CommonFrame extends AbstractDataFrame {

	private static enum Type { MSG, RPY, ANS, ERR, NUL }
	private final Type type;

}
