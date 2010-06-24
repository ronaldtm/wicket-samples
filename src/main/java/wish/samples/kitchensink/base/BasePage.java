package wish.samples.kitchensink.base;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.IPageMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.devutils.inspector.InspectorPage;
import org.apache.wicket.devutils.inspector.LiveSessionsPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.include.Include;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wish.samples.kitchensink.App;
import wish.samples.kitchensink.App.PageCategory;
import wish.samples.kitchensink.App.PageItem;
import wish.samples.kitchensink.base.jquery.JQuery;
import wish.samples.kitchensink.source.SourcePage;

public abstract class BasePage extends WebPage {

    private String pageTitle = "";

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

        add(new BookmarkablePageLink<Void>("homeLink", getApplication().getHomePage()));

        add(createSourceLink("source"));
        add(new BookmarkablePageLink<Void>("inspector", InspectorPage.class)
            .setPopupSettings(defaultPopupSettings("inspector")));
        add(new BookmarkablePageLink<Void>("sessions", LiveSessionsPage.class)
            .setPopupSettings(defaultPopupSettings("sessions")));

        add(new Label("pageTitle", new PropertyModel<String>(this, "pageTitle")) {
            private static final long serialVersionUID = 2648512779130255302L;
            @Override
            public boolean isVisible() {
                return super.isVisible() && StringUtils.trimToNull(getDefaultModelObjectAsString()) != null;
            }
        });

        add(new ListView<PageCategory>("categories", App.get().getPageCategories()) {
            private static final long serialVersionUID = -7402552099056811629L;
            @Override
            protected void populateItem(ListItem<PageCategory> item) {
                item.setModel(new CompoundPropertyModel<PageCategory>(item.getModel()));
                item.add(new Label("title"));
                item.add(new ListView<PageItem>("links") {
                    private static final long serialVersionUID = -757689351285493796L;
                    @Override
                    protected void populateItem(ListItem<PageItem> item) {
                        item.setModel(new CompoundPropertyModel<PageItem>(item.getModel()));
                        PageItem pageItem = item.getModelObject();

                        item.add(new BookmarkablePageLink<Void>("link", pageItem.pageClass)
                            .add(new Label("title"))
                            .add(new SimpleAttributeModifier("title", pageItem.description)));
                    }
                });
            }
        });
        add(JQuery.ready("$('#menu').accordion({ autoHeight:false, event:'mouseover' })"));

        add(new ListView<String>("extraContent", App.get().loadExtraContentList()) {
            private static final long serialVersionUID = -2176903311152916486L;
            @Override
            protected void populateItem(ListItem<String> item) {
                item.add(new Include("include", item.getModelObject()));
            }
        });

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

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }
}
