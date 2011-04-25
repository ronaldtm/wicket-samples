package wicketsamples.data;

import java.io.Serializable;
import java.util.Date;

public class Contact implements Serializable {
    private static final long serialVersionUID = -9174551543628264659L;

    public String name;
    public String email;
    public Date birthDate;
    public float height;
    public float weight;

    public Contact() {
    }

    @Override
    public String toString() {
        return String.format("Contact: %s (%s), %3$td-%3$tm-%3$tY, %4$.2f, %4$.2f",
            name, email, birthDate, height, weight);
    }
}
