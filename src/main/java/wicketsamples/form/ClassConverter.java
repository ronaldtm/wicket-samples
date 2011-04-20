package wicketsamples.form;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import java.util.Locale;

@SuppressWarnings("serial")
public class ClassConverter implements IConverter<Class<?>> {
    @Override
    public Class<?> convertToObject(String value, Locale locale) {
        try {
            return Class.forName(value);
        } catch (ClassNotFoundException ex) {
            throw new ConversionException(ex.getMessage(), ex);
        }
    }

    @Override
    public String convertToString(Class<?> value, Locale locale) {
        return ((Class<?>) value).getName();
    }
}