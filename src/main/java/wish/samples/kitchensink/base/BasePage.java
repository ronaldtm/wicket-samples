package wish.samples.kitchensink.base;

import org.apache.wicket.IPageMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.devutils.inspector.InspectorPage;
import org.apache.wicket.devutils.inspector.LiveSessionsPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.model.IModel;

import wish.samples.kitchensink.base.jquery.JQuery;
import wish.samples.kitchensink.source.SourcePage;

public abstract class BasePage extends WebPage {

    public BasePage() {
        super();
        init();
    }

    public BasePage(IModel<?> model) {
        super(model);
        init();
    }

    public BasePage(IPageMap pageMap, IModel<?> model) {
        super(pageMap, model);
        init();
    }

    public BasePage(IPageMap pageMap, PageParameters parameters) {
        super(pageMap, parameters);
        init();
    }

    public BasePage(IPageMap pageMap) {
        super(pageMap);
        init();
    }

    public BasePage(PageParameters parameters) {
        super(parameters);
        init();
    }

    protected void init() {
        JQuery.addHeaderContributionsTo(this);
        add(createSourceLink("source"));

        add(new BookmarkablePageLink<Void>("inspector", InspectorPage.class)
            .setPopupSettings(defaultPopupSettings("inspector")));
        add(new BookmarkablePageLink<Void>("sessions", LiveSessionsPage.class)
            .setPopupSettings(defaultPopupSettings("sessions")));
    }

    private Link<?> createSourceLink(String id) {
        PageParameters params = new PageParameters();
        params.add("page", getClass().getName());

        Link<?> link = new BookmarkablePageLink<Void>(id, SourcePage.class, params);
        link.setPopupSettings(defaultPopupSettings("sourceCode"));
        return link;
    }

    private PopupSettings defaultPopupSettings(String target) {
        PopupSettings popupSettings = new PopupSettings(PopupSettings.RESIZABLE | PopupSettings.SCROLLBARS);
        popupSettings.setWindowName(target);
        popupSettings.setTarget(target);
        return popupSettings;
    }
}
