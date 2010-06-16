package wish.samples.kitchensink.form;

import java.io.Serializable;
import java.util.Date;

public class Contact implements Serializable {
    public String name;
    public String email;
    public Date birthDate;

    public Contact() {
    }

    public Contact(String name, String email, Date birthDate) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return String.format("Contact: %s (%s), %3$td-%3$tm-%3$tY", name, email, birthDate);
    }
}
