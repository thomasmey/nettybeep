package de.m3y3r.nbeep.impl.netty;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

import de.m3y3r.nbeep.netty.model.frame.data.FrameHeader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class FrameHeaderTest {

	@Test
	public void test() {
//		String headerString = "NUL 0 0 . 0 0\r\n";
		String headerString = "ANS 2147483647 2147483647 . 4294967295 2147483647 2147483647\r\n";
		ByteBuf buf = Unpooled.wrappedBuffer(headerString.getBytes(StandardCharsets.US_ASCII));
		FrameHeader frameHeader = FrameHeader.parse(buf);
	}
}
