package wicketsamples.form;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Locale;
import java.util.Set;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import wicketsamples.base.BasePage;

import com.google.common.collect.Sets;

@SuppressWarnings("unchecked")
public class CustomConvertionPage extends BasePage {

    Class<?> clazz;

    @SuppressWarnings("serial")
    public CustomConvertionPage() throws ParseException {
        super("Simple Form");

        FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        Form form = new Form("form", new CompoundPropertyModel(this));
        add(form);

        TextField classField = new TextField("clazz") {
            public IConverter getConverter(Class<?> type) {
                return new ClassConverter();
            }
        };
        classField.setRequired(true);
        classField.setLabel(Model.of("class"));
        form.add(classField);

        form.add(new Button("send") {
            private static final long serialVersionUID = -3259847450071890320L;
            @Override
            public void onSubmit() {
                Set<String> methodNames = Sets.newTreeSet();
                for (Method method : clazz.getMethods()) {
                    methodNames.add(method.getName());
                }
                info("Class methods: " + methodNames);
            }
        });
    }

    @SuppressWarnings("serial")
    private static class ClassConverter implements IConverter {
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
}
