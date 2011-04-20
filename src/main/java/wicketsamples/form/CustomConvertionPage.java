package wicketsamples.form;

import com.google.common.collect.Sets;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Set;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class CustomConvertionPage extends WebPage {

    Class<?> clazz;

    public CustomConvertionPage() throws ParseException {

        FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        Form form = new Form("form", new CompoundPropertyModel(this));
        add(form);

        TextField classField = new TextField("clazz") {
            @Override
            public <C> IConverter<C> getConverter(Class<C> type) {
                if (type.equals(Class.class)) {
                    return (IConverter<C>) new ClassConverter();
                } else {
                    return super.getConverter(type);
                }
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
}
