package wicketsamples.form;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import wicketsamples.entity.Contact;

@SuppressWarnings("unchecked")
public class SimpleFormPage extends WebPage {

    Contact contact = new Contact();

    @SuppressWarnings("rawtypes")
    public SimpleFormPage() {
        super();

        FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback);

        Form form = new Form("form", new CompoundPropertyModel(new PropertyModel(this, "contact")));
        add(form);

        form.add(new TextField("name"));
        form.add(new TextField("email"));
        form.add(new DateTextField("birthDate", "dd-MM-yyyy"));

        form.add(new Button("send") {
            private static final long serialVersionUID = -3259847450071890320L;
            @Override
            public void onSubmit() {
                info(contact.toString());
                contact = new Contact();
            }
        });
    }
}
