package de.m3y3r.nbeep.profile.sasl.model;

import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.junit.Test;

public class JaxbTest {

	@Test
	public void test() throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance(Blob.class);
		Blob b = new Blob();
		b.setStatus(Blob.Status.COMPLETE);
		b.setContent(new byte[3]);
		Marshaller m = ctx.createMarshaller();
		m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
		{
			StringWriter writer = new StringWriter();
			m.marshal(b, writer);
			System.out.println(writer);
		}
	}
}
