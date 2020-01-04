package de.m3y3r.nbeep.profile.channel.mgmt.netty;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.mail.internet.InternetHeaders;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.m3y3r.nbeep.ProfileCodec;
import de.m3y3r.nbeep.api.Session;
import de.m3y3r.nbeep.profile.channel.mgmt.model.Close;
import de.m3y3r.nbeep.profile.channel.mgmt.model.Greeting;
import de.m3y3r.nbeep.profile.channel.mgmt.model.Ok;
import de.m3y3r.nbeep.profile.channel.mgmt.model.Start;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

public class ChannelManagementCodec implements ProfileCodec<Object> {

	private final Session session;
	private JAXBContext jaxbContext;

	public ChannelManagementCodec(Session session) {
		this.session = session;
		try {
			this.jaxbContext = JAXBContext.newInstance(
					Close.class,
					Error.class,
					Greeting.class,
					Ok.class,
					Start.class
			);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public ByteBuf encode(Object message) throws JAXBException {
		ByteBuf buf = Unpooled.buffer();
		ByteBufOutputStream os = new ByteBufOutputStream(buf);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.marshal(message, os);
		return buf;
	}

	@Override
	public List<Object> decode(ByteBuf msg) throws Exception {
		InputStream in = new ByteBufInputStream(msg);
		InternetHeaders headers = new InternetHeaders(in);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Object unmarshal = unmarshaller.unmarshal(in);
		return Arrays.asList(headers, unmarshal);
	}
}
