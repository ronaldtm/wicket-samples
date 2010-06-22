package wish.samples.kitchensink;

import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.devutils.inspector.InspectorPage;
import org.apache.wicket.devutils.inspector.LiveSessionsPage;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.apache.wicket.session.ISessionStore;
import org.apache.wicket.session.pagemap.LeastRecentlyAccessedEvictionStrategy;
import org.apache.wicket.util.lang.PackageName;

import wish.samples.kitchensink.home.HomePage;

public class App extends WebApplication {

    public static App get() {
        return (App) WebApplication.get();
    }

    @Override
    protected void init() {
        getDebugSettings().setAjaxDebugModeEnabled(true);
        getDebugSettings().setDevelopmentUtilitiesEnabled(true);

        getMarkupSettings().setStripComments(true);
        getMarkupSettings().setStripWicketTags(true);
        getMarkupSettings().setStripXmlDeclarationFromOutput(true);
        getMarkupSettings().setDefaultMarkupEncoding("utf-8");

        getRequestLoggerSettings().setRequestsWindowSize(500);
        getRequestLoggerSettings().setRecordSessionSize(true);
        getRequestLoggerSettings().setRequestLoggerEnabled(true);

        getResourceSettings().setResourcePollFrequency(null);

        getSecuritySettings().setEnforceMounts(true);

        getSessionSettings().setMaxPageMaps(1);
        getSessionSettings().setPageMapEvictionStrategy(new LeastRecentlyAccessedEvictionStrategy(3));

        mountPackage("form");
        mountPackage("ajax");

        mount(new QueryStringUrlCodingStrategy("debug/inspector", InspectorPage.class));
        mount(new QueryStringUrlCodingStrategy("debug/sessions", LiveSessionsPage.class));
    }

    private void mountPackage(String mountPath) {
        String subPackageName = mountPath.replaceAll("/", ".");
        String basePackageName = getClass().getPackage().getName();
        String indexClassName = basePackageName + "." + subPackageName + ".IndexPage";
        try {
            mount(mountPath, PackageName.forClass(Class.forName(indexClassName)));
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return HomePage.class;
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new AppSession(request);
    }

    @Override
    protected ISessionStore newSessionStore() {
        return new HttpSessionStore(this);
    }
}
