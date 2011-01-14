package wicketsamples.form;

import java.text.ParseException;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class CustomValidationPage extends WebPage {

    String email;

    public CustomValidationPage() throws ParseException {
        FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        Form form = new Form("form", new CompoundPropertyModel(this));
        add(form);

        form.add(new TextField("email")
            .setRequired(true)
            .add(EmailAddressValidator.getInstance())
            .add(new AllowedDomainsValidator()));

        form.add(new Button("send") {
            private static final long serialVersionUID = -3259847450071890320L;
            @Override
            public void onSubmit() {
                info("Submited: " + email);
                email = "";
            }
        });
    }
}
