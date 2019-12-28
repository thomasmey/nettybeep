package de.m3y3r.nbeep.frame;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Frame {

	 void writeObject(OutputStream out) throws IOException;
	 void readObject(InputStream in) throws IOException;
}
