package wicketsamples.form;

import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

@SuppressWarnings("serial")
public class ClassConverter implements IConverter {
    @Override
    public Object convertToObject(String value, Locale locale) {
        try {
            return Class.forName(value);
        } catch (ClassNotFoundException ex) {
            throw new ConversionException(ex.getMessage(), ex);
        }
    }
    @Override
    public String convertToString(Object value, Locale locale) {
        return ((Class<?>) value).getName();
    }
}