package wicketsamples;

import com.google.common.collect.Lists;
import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.devutils.inspector.InspectorPage;
import org.apache.wicket.devutils.inspector.LiveSessionsPage;
import org.apache.wicket.markup.IMarkupCache;
import org.apache.wicket.markup.MarkupCache;
import org.apache.wicket.markup.MarkupFactory;
import org.apache.wicket.page.IPageManager;
import org.apache.wicket.page.IPageManagerContext;
import org.apache.wicket.page.PersistentPageManager;
import org.apache.wicket.pageStore.DefaultPageStore;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.memory.HttpSessionDataStore;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.MountedMapper;
import org.apache.wicket.session.HttpSessionStore;
import org.apache.wicket.session.ISessionStore;
import org.apache.wicket.util.IProvider;
import org.apache.wicket.util.io.IObjectStreamFactory;
import org.apache.wicket.util.lang.WicketObjects;
import wicketsamples.ajax.AutocompletePage;
import wicketsamples.base.MainPage;
import wicketsamples.base.config.AppConfig;
import wicketsamples.form.*;
import wicketsamples.layout.BorderPage;
import wicketsamples.layout.FieldBorder;
import wicketsamples.layout.FragmentPage;
import wicketsamples.layout.PanelPage;

import java.io.*;
import java.util.List;

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

        setSessionStoreProvider(new IProvider<ISessionStore>() {
            @Override
            public ISessionStore get() {
                return new HttpSessionStore();
            }
        });
        setPageManagerProvider(new DefaultPageManagerProvider(this) {
            @Override
            public IPageManager get(IPageManagerContext pageManagerContext) {
                IDataStore dataStore = new HttpSessionDataStore(pageManagerContext, new PageNumberEvictionStrategy(10));
                IPageStore pageStore = new DefaultPageStore(getName(), dataStore, getCacheSize());
                return new PersistentPageManager(getName(), pageStore, pageManagerContext);
            }
        });

        getResourceSettings().setResourcePollFrequency(null);
        WicketObjects.setObjectStreamFactory(new IObjectStreamFactory() {
            @Override
            public ObjectInputStream newObjectInputStream(InputStream in) throws IOException {
                return new ObjectInputStream(in);
            }
            @Override
            public ObjectOutputStream newObjectOutputStream(OutputStream out) throws IOException {
                return new ObjectOutputStream(out);
            }
        });

        getSecuritySettings().setEnforceMounts(true);

        if ("development".equals(System.getProperty("wicket.configuration"))) {
            getMarkupSettings().setMarkupFactory(new MarkupFactory() {
                final NullMarkupCache CACHE = new NullMarkupCache();

                @Override
                public IMarkupCache getMarkupCache() {
                    return CACHE;
                }
            });
        } else {
        }

        mountPages();
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return MainPage.class;
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new AppSession(request);
    }

    private void mountPages() {
        mount(new MountedMapper("debug/inspector", InspectorPage.class));
        mount(new MountedMapper("debug/sessions", LiveSessionsPage.class));

        for (PageCategory category : getPageCategories()) {
            for (PageItem item : category.links) {
                mount(new MountedMapper(category.path + "/" + item.pageClass.getSimpleName(), item.pageClass));
            }
        }
    }

    private static class NullMarkupCache extends MarkupCache {
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
        return AppConfig.get().getProperty(AppConfig.KEY_EXTRA_HEADER_CONTENT, "");
    }

    public String getExtraBodyContent() {
        return AppConfig.get().getProperty(AppConfig.KEY_EXTRA_BODY_CONTENT, "");
    }

    public String getExtraHtmlHeadContent() {
        return AppConfig.get().getProperty(AppConfig.KEY_EXTRA_HTML_HEAD_CONTENT, "");
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
        );
        categories.add(new PageCategory("layout", "Layout")
            .add(FragmentPage.class, "Fragment", "Reusing markup fragments")
            .add(PanelPage.class, "Panel", "Creating reusable composite components with panels", Contact.class)
            .add(BorderPage.class, "Border", "Wrapping components with reusable borders", FieldBorder.class, Contact.class)
        );
        return categories;
    }
}
