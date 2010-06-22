package wish.samples.kitchensink.ajax;

import wish.samples.kitchensink.base.BaseIndexPage;

public class IndexPage extends BaseIndexPage {
    public IndexPage() {
        addLink(AutocompletePage.class, "Auto-Complete", "");
        addLink(AjaxFileUploadPage.class, "File Upload", "");
    }
}
