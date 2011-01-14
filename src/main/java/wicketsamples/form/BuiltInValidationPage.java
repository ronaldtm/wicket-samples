package wicketsamples.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.DateValidator;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.MinimumValidator;
import org.apache.wicket.validation.validator.StringValidator;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class BuiltInValidationPage extends WebPage {

    Contact contact = new Contact();

    public BuiltInValidationPage() throws ParseException {

        FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        Form form = new Form("form", new CompoundPropertyModel(new PropertyModel(this, "contact")));
        add(form);

        form.add(new TextField("name")
            .setRequired(true)
            .add(StringValidator.minimumLength(5)));

        form.add(new TextField("email")
            .setRequired(true)
            .add(EmailAddressValidator.getInstance()));

        form.add(new TextField("height")
            .add(new MinimumValidator(0f)));

        form.add(new TextField("weight")
            .add(new MinimumValidator(0f)));

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date minimum = sdf.parse("01-01-2010");
        Date maximum = sdf.parse("31-12-2010");
        form.add(new DateTextField("birthDate", "dd-MM-yyyy")
            .setLabel(Model.of("birth date"))
            .setRequired(true)
            .add(DateValidator.range(minimum, maximum, "dd-MM-yyyy")));

        form.add(new Button("send") {
            private static final long serialVersionUID = -3259847450071890320L;
            @Override
            public void onSubmit() {
                info(contact.toString());
            }
        });
    }
}
