package wicketsamples;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.Application;
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

import wicketsamples.ajax.AjaxFileUploadPage;
import wicketsamples.ajax.AutocompletePage;
import wicketsamples.base.MainPage;
import wicketsamples.base.config.AppConfig;
import wicketsamples.form.AjaxFormPage;
import wicketsamples.form.AllowedDomainsValidator;
import wicketsamples.form.BuiltInValidationPage;
import wicketsamples.form.ClassConverter;
import wicketsamples.form.Contact;
import wicketsamples.form.CustomConvertionPage;
import wicketsamples.form.CustomValidationPage;
import wicketsamples.form.SimpleFormPage;
import wicketsamples.layout.BorderPage;
import wicketsamples.layout.FieldBorder;
import wicketsamples.layout.FragmentPage;
import wicketsamples.layout.PanelPage;

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
        return MainPage.class;
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
        //        final String[] EMPTY_STRING_ARRAY = new String[0];
        //        for (PageCategory category : getPageCategories()) {
        //            for (PageItem item : category.links) {
        //                mount(new MixedParamHybridUrlCodingStrategy(
        //                    category.path + "/" + item.pageClass.getSimpleName(),
        //                    item.pageClass, EMPTY_STRING_ARRAY));
        //            }
        //        }
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

    public String getExtraHeaderContent() {
        return AppConfig.get()
            .getProperty(AppConfig.KEY_EXTRA_HEADER_CONTENT, "");
    }

    public String getExtraBodyContent() {
        return AppConfig.get()
            .getProperty(AppConfig.KEY_EXTRA_BODY_CONTENT, "");
    }

    public String getExtraHtmlHeadContent() {
        return AppConfig.get()
            .getProperty(AppConfig.KEY_EXTRA_HTML_HEAD_CONTENT, "");
    }

    public List<PageCategory> getPageCategories() {
        List<PageCategory> categories = Lists.newArrayList();
        categories.add(new PageCategory("form", "Forms")
            .add(SimpleFormPage.class, "Simple form", "Simple form", Contact.class)
            .add(BuiltInValidationPage.class, "Built-in validation", "Built-in validation", Contact.class)
            .add(CustomValidationPage.class, "Custom validation", "Custom validation", AllowedDomainsValidator.class, Contact.class)
            .add(CustomConvertionPage.class, "Custom convertion", "Custom convertion", ClassConverter.class, Contact.class)
            );
        categories.add(new PageCategory("ajax", "Ajax")
            .add(AjaxFormPage.class, "Ajax form", "Simple form with ajax submit", Contact.class)
            .add(AutocompletePage.class, "Auto-Complete", "Show matching options while you type")
            .add(AjaxFileUploadPage.class, "File Upload", "Upload a file in an 'ajax-like' request")
            );
        categories.add(new PageCategory("layout", "Layout")
            .add(FragmentPage.class, "Fragment", "Reusing markup fragments")
            .add(PanelPage.class, "Panel", "Creating reusable composite components with panels", Contact.class)
            .add(BorderPage.class, "Border", "Wrapping components with reusable borders", FieldBorder.class, Contact.class)
            );
        return categories;
    }
}
