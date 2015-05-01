package ca.ulaval.glo4003.ULavalWebTimeSheet.Infrastructure.Persistance.Convertisseurs;

import org.joda.time.DateTime;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class JodaTimeConvertisseur implements Converter
{

    @Override
    public boolean canConvert(@SuppressWarnings("rawtypes") final Class type) {
	return DateTime.class.isAssignableFrom(type);
    }

    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
	if (source == null) {
	    writer.setValue("0");
	}
	writer.setValue(source.toString());
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
	return new DateTime(reader.getValue());
    }

}
