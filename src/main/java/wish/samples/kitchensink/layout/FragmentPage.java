package wish.samples.kitchensink.layout;

import java.util.Date;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import wish.samples.kitchensink.base.BasePage;

public class FragmentPage extends BasePage {

    String name;
    String email;
    Date birthDate;

    @SuppressWarnings("unchecked")
    public FragmentPage() {
        setPageTitle("Fragment");

        Form form = new Form("form");
        add(form);

        form.add(new FieldFragment("name", "Name:", new TextField("field").setRequired(true)));
        form.add(new FieldFragment("email", "Email:", new TextField("field").add(EmailAddressValidator.getInstance())));
        form.add(new FieldFragment("birthDate", "Birth Date:", new DateTextField("field")));

        form.add(new Button("send") {
            private static final long serialVersionUID = 2957501468446185355L;
            @Override
            public void onSubmit() {
                info(String.format("%s [%s] %t", name, email, birthDate));
            }
        });
    }

    @SuppressWarnings("unchecked")
    class FieldFragment extends Fragment {
        private static final long serialVersionUID = -2291031031424315050L;
        public FieldFragment(String id, String label, FormComponent<?> field) {
            super(id, "fieldFragment", FragmentPage.this);

            field.setModel(new PropertyModel(FragmentPage.this, id));

            add(new Label("label", label));
            add(field);
            add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(field)));
        }
    }
}
