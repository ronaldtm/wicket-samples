package wicketsamples.layout;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import wicketsamples.form.Contact;

@SuppressWarnings({ "serial", "rawtypes", "unchecked" })
public class BorderPage extends WebPage {

    Contact contact = new Contact();

    public BorderPage() {

        Form form = new Form("form", new CompoundPropertyModel(new PropertyModel(this, "contact")));
        add(form);

        form.add(new FieldBorder("nameField", "Name:", new TextField("name").setRequired(true)));

        form.add(new FieldBorder("emailField", "Email:",
            new TextField("email").add(EmailAddressValidator.getInstance())));

        form.add(new FieldBorder("birthDateField", "Birth Date (dd-MM-yyyy):", new DateTextField("birthDate",
            "dd-MM-yyyy")));

        form.add(new AjaxButton("send") {
            private static final long serialVersionUID = -7118298727205594967L;
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                target.addComponent(form);
                target.appendJavascript("alert('" + contact + "');");
            }
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.addComponent(form);
            }
        });
    }
}
