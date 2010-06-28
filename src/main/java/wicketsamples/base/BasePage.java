package wicketsamples.base;

import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.IPageMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.devutils.inspector.InspectorPage;
import org.apache.wicket.devutils.inspector.LiveSessionsPage;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.include.Include;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import wicketsamples.App;
import wicketsamples.base.highlighter.Highlighter;
import wicketsamples.base.jquery.JQuery;
import wicketsamples.source.SourceCodePanel;

public abstract class BasePage extends WebPage {

    private String pageTitle;

    private RepeatingView sourceCodeTabTitles = new RepeatingView("sourceCodeTabTitles");
    private RepeatingView sourceCodeTabContents = new RepeatingView("sourceCodeTabContents");

    public BasePage(String pageTitle) {
        super();
        init(pageTitle);
    }

    public BasePage(String pageTitle, IModel<?> model) {
        super(model);
        init(pageTitle);
    }

    public BasePage(String pageTitle, IPageMap pageMap, IModel<?> model) {
        super(pageMap, model);
        init(pageTitle);
    }

    public BasePage(String pageTitle, IPageMap pageMap, PageParameters parameters) {
        super(pageMap, parameters);
        init(pageTitle);
    }

    public BasePage(String pageTitle, IPageMap pageMap) {
        super(pageMap);
        init(pageTitle);
    }

    public BasePage(PageParameters parameters) {
        super(parameters);
        init(pageTitle);
    }

    @SuppressWarnings("unchecked")
    protected void init(String pageTitle) {
        setPageTitle(pageTitle);
        setDefaultModel(new CompoundPropertyModel(this));

        add(new BookmarkablePageLink<Void>("homeLink", getApplication().getHomePage()));

        add(new BookmarkablePageLink<Void>("inspector", InspectorPage.class)
            .setPopupSettings(defaultPopupSettings("inspector")));
        add(new BookmarkablePageLink<Void>("sessions", LiveSessionsPage.class)
            .setPopupSettings(defaultPopupSettings("sessions")));

        add(new Label("pageTitle", new PropertyModel<String>(this, "pageTitle")));

        add(new MenuPanel("menu"));

        add(sourceCodeTabTitles);
        add(sourceCodeTabContents);

        add(new ListView<String>("extraContent", App.get().loadExtraContentList()) {
            private static final long serialVersionUID = -2176903311152916486L;
            @Override
            protected void populateItem(ListItem<String> item) {
                item.add(new Include("include", item.getModelObject()));
            }
        });

        appendTabForSourceCodeForJavaAndHTML(getClass());

        JQuery.addHeaderContributionsTo(this);
        Highlighter.addHeaderContributions(this);
        add(CSSPackageResource.getHeaderContribution(BasePage.class, "BasePage.css"));
        add(JQuery.ready("$('#sections').tabs();"));
    }

    protected final void appendTabForSourceCodeForJava(Class<?> componentClass) {
        String classSimpleName = componentClass.getSimpleName();
        appendTabForSourceCode(componentClass.getResource(classSimpleName + ".java"));
    }

    protected final void appendTabForSourceCodeForJavaAndHTML(Class<? extends Component> componentClass) {
        String classSimpleName = componentClass.getSimpleName();
        appendTabForSourceCode(componentClass.getResource(classSimpleName + ".java"));
        appendTabForSourceCode(componentClass.getResource(classSimpleName + ".html"));
    }

    protected final void appendTabForSourceCode(URL url) {
        String filename = StringUtils.substringAfterLast(url.getPath(), "/");

        SourceCodePanel sourceCodePanel = new SourceCodePanel(sourceCodeTabContents.newChildId(),
            new URLContentModel(url), Highlighter.Syntax.of(filename));

        sourceCodeTabTitles.add(new Fragment(sourceCodeTabTitles.newChildId(), "linkFragment", this)
            .add(new Label("link", filename)
            .add(new SimpleAttributeModifier("href", "#" + sourceCodePanel.getMarkupId()))));

        sourceCodeTabContents.add(sourceCodePanel);
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
