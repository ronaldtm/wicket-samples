package wicketsamples.form;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import com.google.common.collect.ImmutableSet;

public class AllowedDomainsValidator implements IValidator<String> {
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