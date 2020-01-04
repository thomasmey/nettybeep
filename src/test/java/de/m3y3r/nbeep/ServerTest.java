package de.m3y3r.nbeep;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.ServiceLoader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

import de.m3y3r.nbeep.api.Server;
import de.m3y3r.nbeep.profile.channel.mgmt.model.Greeting;
import de.m3y3r.nbeep.profile.channel.mgmt.model.Start;

public class ServerTest {

	@Test
	public void test() throws NumberFormatException, UnknownHostException, IOException, JAXBException, InterruptedException {
		BEEP beep = new BEEP();
		Server server = beep.getServer();
		String port = "6942";

		Properties config = new Properties();
		config.setProperty("port", port);
		server.start(config);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write("Content-Type: application/beep+xml\r\n".getBytes(StandardCharsets.US_ASCII));
		baos.write('\r');
		baos.write('\n');

		JAXBContext ctx = JAXBContext.newInstance(Greeting.class, Start.class);
		//		Start start = new Start();
		//		start.setNumber(1);

		Greeting o = new Greeting();
		Marshaller m = ctx.createMarshaller();
		m.marshal(o, baos);

		try(
				Socket socket = new Socket((String)null, Integer.parseInt(port));
				OutputStream out = socket.getOutputStream()
				) {
			Channel channel = new Channel(0, out, 1);
			channel.sendMessage("RPY", baos.toByteArray());
			//			channel.sendMessage("MSG", baos.toByteArray());

		}
		Thread.sleep(100000);
	}
}

class Channel {
	final int channelNo;
	final OutputStream out;
	final int maxPayloadSize;

	int msgNo = 0;
	long seqNo = 0;

	public Channel(int channelNo, OutputStream out, int maxPayloadSize) {
		this.channelNo = channelNo;
		this.out = out;
		this.maxPayloadSize = maxPayloadSize;
	}

	void sendMessage(String type, byte[] payload) throws IOException {
		int len = Math.min(maxPayloadSize, payload.length);
		int start = 0;
		int size = payload.length;

		while(size > 0) {
			boolean cont = size - len != 0;
			String header = createHeader(type, channelNo, msgNo, cont, seqNo, len);
			out.write(header.getBytes(StandardCharsets.US_ASCII));
			out.write(payload, start, len);
			out.write("END\r\n".getBytes(StandardCharsets.US_ASCII));

			seqNo = seqNo + len;

			size -= len;
			start += len;
		}
		msgNo = msgNo + 1;
	}

	public static String createHeader(String type, int channelNo, int msgNo, boolean continu, long seqNo, int size) {
		char cont = continu ? '*' : '.'; // . == last frame for message
		return String.format("%s %d %d %c %d %d\r\n", type, channelNo, msgNo, cont, seqNo, size);
	}

}