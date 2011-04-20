package wicketsamples.base;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.devutils.inspector.InspectorPage;
import org.apache.wicket.devutils.inspector.LiveSessionsPage;
import org.apache.wicket.devutils.stateless.StatelessComponent;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import wicketsamples.App;
import wicketsamples.base.jquery.JQuery;

@StatelessComponent
public class MainPage extends WebPage {

    private String pageTitle;

    public MainPage(PageParameters parameters) {
        super(parameters);
        init(pageTitle);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void init(String pageTitle) {
        setPageTitle(pageTitle);
        setDefaultModel(new CompoundPropertyModel(this));

        add(JQuery.getHeaderContributions());
        add(JQuery.ready("$('#sections').tabs();"));

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

        add(new Label("extraHeaderContent", new PropertyModel(this, "application.extraHeaderContent"))
            .setEscapeModelStrings(false));
        add(new Label("extraBodyContent", new PropertyModel(this, "application.extraBodyContent"))
            .setEscapeModelStrings(false));

        add(new Behavior() {
            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                response.renderString(App.get().getExtraHtmlHeadContent());
                response.renderCSSReference(new PackageResourceReference(MainPage.class, "MainPage.css"));
            }
        });
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
