package wish.samples.kitchensink.form;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import wish.samples.kitchensink.base.BasePage;

@SuppressWarnings("unchecked")
public class AjaxFormPage extends BasePage {

    Contact contact = new Contact();

    FeedbackPanel feedback;

    public AjaxFormPage() {
        feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        
        Form form = new Form("form", new CompoundPropertyModel(new PropertyModel(this, "contact")));
        form.setOutputMarkupId(true);

        form.add(new TextField("name").setRequired(true));
        form.add(new TextField("email").setRequired(true).add(EmailAddressValidator.getInstance()));
        form.add(new DateTextField("birthDate", "dd-MM-yyyy"));

        form.add(new AjaxButton("send") {
            private static final long serialVersionUID = -3259847450071890320L;
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                info(contact.toString());
                contact = new Contact();
                target.addComponent(form);
                target.addComponent(feedback);
            }
        });

        add(form);
        add(feedback);
    }
}
