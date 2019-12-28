package de.m3y3r.nbeep.profile.channel.mgmt.model;

import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

public class JaxbTest {

	@Test
	public void test() throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance(Greeting.class, Start.class);
		Greeting g = new Greeting();
		Profile p1 = new Profile();
		p1.setUri("uri://test1");
		Profile p2 = new Profile();
		p2.setUri("uri://test2");
		g.setProfiles(Arrays.asList(p1,p2));
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

		{
			StringWriter writer = new StringWriter();
			m.marshal(g, writer);
			System.out.println(writer);
		}

		{
			g.setProfiles(null);
			StringWriter writer = new StringWriter();
			m.marshal(g, writer);
			System.out.println(writer);
		}

	}
}
