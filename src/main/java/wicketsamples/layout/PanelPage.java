package wicketsamples.layout;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.PropertyModel;

import wicketsamples.entity.Contact;

public class PanelPage extends WebPage {

    Contact contact1 = new Contact();
    Contact contact2 = new Contact();
    Contact contact3 = new Contact();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PanelPage() {

        add(new ContactPanel("contact1", new PropertyModel(this, "contact1")));
        add(new ContactPanel("contact2", new PropertyModel(this, "contact2")));
        add(new ContactPanel("contact3", new PropertyModel(this, "contact3")));
    }
}
