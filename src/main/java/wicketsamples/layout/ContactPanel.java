package wicketsamples.layout;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import wicketsamples.form.Contact;

public class ContactPanel extends Panel {
    private static final long serialVersionUID = -8501926258693714203L;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ContactPanel(String id, final IModel<Contact> model) {
        super(id, model);
        setOutputMarkupId(true);

        Form form = new Form("form", new CompoundPropertyModel(this.getDefaultModel()));
        add(form);

        form.add(new TextField("name").setRequired(true));
        form.add(new TextField("email").add(EmailAddressValidator.getInstance()));
        form.add(new DateTextField("birthDate", "dd-MM-yyyy"));

        form.add(new AjaxButton("send") {
            private static final long serialVersionUID = 6382669836481145791L;
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                info(form.getModelObject().toString());
                model.setObject(new Contact());
                target.add(ContactPanel.this);
            }
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.add(ContactPanel.this);
            }
        });

        add(new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(form)));
    }
}
