package wish.samples.kitchensink.form;

import wish.samples.kitchensink.base.BaseIndexPage;

public class IndexPage extends BaseIndexPage {
    public IndexPage() {
        addLink(SimpleFormPage.class, "Simple form", "");
        addLink(AjaxFormPage.class, "Ajax form", "");
    }
}
