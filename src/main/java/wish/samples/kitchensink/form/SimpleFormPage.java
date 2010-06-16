package wish.samples.kitchensink.form;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import wish.samples.kitchensink.base.BasePage;

@SuppressWarnings("unchecked")
public class SimpleFormPage extends BasePage {

    String name;
    String email;

    public SimpleFormPage() {

        Form form = new Form("form", new CompoundPropertyModel(this));

        form.add(new TextField("name").setRequired(true));
        form.add(new TextField("email").setRequired(true).add(EmailAddressValidator.getInstance()));

        form.add(new Button("send") {
            private static final long serialVersionUID = -3259847450071890320L;
            @Override
            public void onSubmit() {
                info(String.format("Name=%s; Email=%s", name, email));
            }
        });

        add(form);
        add(new FeedbackPanel("feedback"));
    }
}
