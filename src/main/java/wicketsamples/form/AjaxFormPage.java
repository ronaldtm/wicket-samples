package wicketsamples.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import wicketsamples.entity.Contact;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class AjaxFormPage extends WebPage {

    Contact contact = new Contact();

    public AjaxFormPage() {

        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        add(feedback.setOutputMarkupId(true));

        Form form = new Form("form", new CompoundPropertyModel(new PropertyModel(this, "contact")));
        add(form.setOutputMarkupId(true));

        form.add(new TextField("name").setRequired(true));
        form.add(new TextField("email").setRequired(true).add(EmailAddressValidator.getInstance()));
        form.add(new DateTextField("birthDate", "dd-MM-yyyy"));

        form.add(new AjaxButton("send") {
            private static final long serialVersionUID = -3259847450071890320L;
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                info(contact.toString());
                contact = new Contact();
                target.add(form);
                target.add(feedback);
            }
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(form);
                target.add(feedback);
            }
        });
    }
}
