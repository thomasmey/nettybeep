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

import de.m3y3r.nbeep.profile.channel.mgmt.model.Start;

public class ServerTest {

	@Test
	public void test() throws NumberFormatException, UnknownHostException, IOException, JAXBException, InterruptedException {
		String port = "6942";

		Server server = ServiceLoader.load(Server.class).iterator().next();
		Properties config = new Properties();
		config.setProperty("port", port);
		server.start(config);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write("Content-Type: application/beep+xml\r\n".getBytes(StandardCharsets.US_ASCII));
		baos.write("\r\n".getBytes(StandardCharsets.US_ASCII));

		JAXBContext ctx = JAXBContext.newInstance(Start.class);
		Start start = new Start();
		start.setNumber(1);
		Marshaller m = ctx.createMarshaller();
		m.marshal(start, baos);

		try(
			Socket socket = new Socket((String)null, Integer.parseInt(port));
			OutputStream out = socket.getOutputStream()
			) {
			String header = "MSG 0 1 . 0 " + baos.size() + "\r\n";
			out.write(header.getBytes(StandardCharsets.US_ASCII));
			out.write(baos.toByteArray());
			out.write("END\r\n".getBytes(StandardCharsets.US_ASCII));

			header = "MSG 0 2 . 0 " + baos.size() + "\r\n";
			out.write(header.getBytes(StandardCharsets.US_ASCII));
			out.write(baos.toByteArray());
			out.write("END\r\n".getBytes(StandardCharsets.US_ASCII));
		}
		Thread.currentThread().sleep(10000000);
	}
}
