package wicketsamples.base;

import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.StringHeaderContributor;
import org.apache.wicket.devutils.inspector.InspectorPage;
import org.apache.wicket.devutils.inspector.LiveSessionsPage;
import org.apache.wicket.devutils.stateless.StatelessComponent;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import wicketsamples.base.jquery.JQuery;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;

@StatelessComponent
public class MainPage extends WebPage {

    private String pageTitle;

    public MainPage(PageParameters parameters) {
        super(parameters);
        init(pageTitle);
    }

    @SuppressWarnings({ "rawtypes" })
    protected void init(String pageTitle) {
        setPageTitle(pageTitle);
        setDefaultModel(new CompoundPropertyModel(this));

        add(new BookmarkablePageLink<Void>("homeLink", getApplication().getHomePage()));

        add(new BookmarkablePageLink<Void>("inspector", InspectorPage.class)
            .setPopupSettings(defaultPopupSettings("inspector")));
        add(new BookmarkablePageLink<Void>("sessions", LiveSessionsPage.class)
            .setPopupSettings(defaultPopupSettings("sessions")));
        add(new StatelessLink("deleteSessions") {
            @Override
            public void onClick() {
                DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
                Query query = new Query("_ah_SESSION");
                for (Entity entity : ds.prepare(query).asIterable()) {
                    ds.delete(entity.getKey());
                }
            }
        });

        add(new Label("pageTitle", new PropertyModel<String>(this, "pageTitle")));

        add(new MenuPanel("menu", "demoFrame", new PageCategoriesModel()));

        add(new StringHeaderContributor(new PropertyModel(this, "application.extraHtmlHeadContent")));
        add(new Label("extraHeaderContent", new PropertyModel(this, "application.extraHeaderContent"))
            .setEscapeModelStrings(false));
        add(new Label("extraBodyContent", new PropertyModel(this, "application.extraBodyContent"))
            .setEscapeModelStrings(false));

        JQuery.addHeaderContributionsTo(this);
        add(CSSPackageResource.getHeaderContribution(MainPage.class, "MainPage.css"));
        add(JQuery.ready("$('#sections').tabs();"));
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
