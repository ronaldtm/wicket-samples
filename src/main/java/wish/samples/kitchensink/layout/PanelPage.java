package wish.samples.kitchensink.layout;

import org.apache.wicket.model.PropertyModel;

import wish.samples.kitchensink.base.BasePage;

public class PanelPage extends BasePage {

    Contact contact1 = new Contact();
    Contact contact2 = new Contact();
    Contact contact3 = new Contact();

    @SuppressWarnings("unchecked")
    public PanelPage() {
        setPageTitle("Panel Page");

        add(new PanelPagePanel("contact1", new PropertyModel(this, "contact1")));
        add(new PanelPagePanel("contact2", new PropertyModel(this, "contact2")));
        add(new PanelPagePanel("contact3", new PropertyModel(this, "contact3")));
    }
}
