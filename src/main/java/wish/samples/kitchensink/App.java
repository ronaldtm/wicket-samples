package wish.samples.kitchensink;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Application;
import org.apache.wicket.IClusterable;
import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.devutils.inspector.InspectorPage;
import org.apache.wicket.devutils.inspector.LiveSessionsPage;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadWebRequest;
import org.apache.wicket.markup.MarkupCache;
import org.apache.wicket.protocol.http.HttpSessionStore;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.apache.wicket.session.ISessionStore;
import org.apache.wicket.session.pagemap.LeastRecentlyAccessedEvictionStrategy;

import wish.samples.kitchensink.ajax.AjaxFileUploadPage;
import wish.samples.kitchensink.ajax.AutocompletePage;
import wish.samples.kitchensink.form.AjaxFormPage;
import wish.samples.kitchensink.form.SimpleFormPage;
import wish.samples.kitchensink.home.HomePage;
import wish.samples.kitchensink.layout.BorderPage;
import wish.samples.kitchensink.layout.FragmentPage;
import wish.samples.kitchensink.layout.PanelPage;

import com.google.common.collect.Lists;

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

        if ("development".equals(System.getProperty("wicket.configuration"))) {
            getMarkupSettings().setMarkupCache(new NullMarkupCache(this));
        } else {

        }

        mount(new QueryStringUrlCodingStrategy("debug/inspector", InspectorPage.class));
        mount(new QueryStringUrlCodingStrategy("debug/sessions", LiveSessionsPage.class));
        mountPages();
    }

    @Override
    protected WebRequest newWebRequest(HttpServletRequest servletRequest) {
        return new UploadWebRequest(servletRequest);
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

    private void mountPages() {
        for (PageCategory category : getPageCategories()) {
            for (PageItem link : category.links) {
                mount(new QueryStringUrlCodingStrategy(link.getMountPath(category), link.pageClass));
            }
        }
    }

    public List<PageCategory> getPageCategories() {
        List<PageCategory> categories = Lists.newArrayList();
        categories.add(new PageCategory("forms", "Forms")
            .add(SimpleFormPage.class, "Simple form", "Simple form")
            .add(AjaxFormPage.class, "Ajax form", "Simple form with ajax submit")
            );
        categories.add(new PageCategory("ajax", "Ajax")
            .add(AutocompletePage.class, "Auto-Complete", "Show matching options while you type")
            .add(AjaxFileUploadPage.class, "File Upload", "Upload a file in an 'ajax-like' request")
            );
        categories.add(new PageCategory("layout", "Layout")
            .add(FragmentPage.class, "Fragment", "Reusing markup fragments")
            .add(PanelPage.class, "Panel", "Creating reusable composite components with panels")
            .add(BorderPage.class, "Border", "Wrapping components with reusable borders")
            );
        return categories;
    }

    public static class PageCategory implements IClusterable {
        private static final long serialVersionUID = -1209873707198411368L;
        public final String path;
        public final String title;
        public final List<PageItem> links = Lists.newArrayList();
        public PageCategory(String path, String title, PageItem... links) {
            this.path = path;
            this.title = title;
            this.links.addAll(Arrays.asList(links));
        }
        public PageCategory add(Class<? extends Page> pageClass, String title, String description) {
            links.add(new PageItem(pageClass, title, description));
            return this;
        }
    }

    public static class PageItem implements IClusterable {
        private static final long serialVersionUID = -3018927676806533341L;
        public final Class<? extends Page> pageClass;
        public final String title;
        public final String description;
        public PageItem(Class<? extends Page> pageClass, String title, String description) {
            this.pageClass = pageClass;
            this.title = title;
            this.description = description;
        }
        public String getMountPath(PageCategory category) {
            String pagePath = pageClass.getSimpleName().replaceFirst("Page$", "");
            pagePath = Character.toLowerCase(pagePath.charAt(0)) + pagePath.substring(1);
            return String.format("%s/%s", category.path, pagePath);
        }
    }

    private static class NullMarkupCache extends MarkupCache {
        public NullMarkupCache(Application application) {
            super(application);
        }
        @Override
        protected <K, V> ICache<K, V> newCacheImplementation() {
            return new DefaultCacheImplementation<K, V>() {
                @Override
                public void put(K key, V value) {
                }
            };
        }
    }

    public List<String> loadExtraContentList() {
        List<String> extraContentList = Lists.newArrayList();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String value = System.getProperty("extraContent." + i);
            if (value != null) {
                extraContentList.add(value);
            } else {
                break;
            }
        }
        return extraContentList;
    }
}
