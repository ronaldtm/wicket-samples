package wicketsamples.form;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import wicketsamples.base.BasePage;

import com.google.common.collect.ImmutableSet;

@SuppressWarnings("unchecked")
public class CustomValidationPage extends BasePage {

    String email;

    @SuppressWarnings("rawtypes")
    public CustomValidationPage() throws ParseException {
        super("Simple Form");

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

    private static class AllowedDomainsValidator implements IValidator<String> {
        private static final long serialVersionUID = -6615003172960385827L;

        private static final ImmutableSet<String> allowedDomains = ImmutableSet.of(
            "gmail.com", "yahoo.com", "foo.com", "bar.com");

        @Override
        public void validate(IValidatable<String> validatable) {
            String email = validatable.getValue();
            String domain = StringUtils.substringAfterLast(email, "@");
            if (!allowedDomains.contains(domain)) {
                ValidationError error = new ValidationError();
                error.setMessage("Domain ${domain} not allowed");
                error.setVariable("domain", domain);
                validatable.error(error);
            }
        }
    }
}
