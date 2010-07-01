package wicketsamples.base.config;

import java.io.Serializable;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class AppConfig {

    private static final ConfigKey<String> KEY_DUMMY = new ConfigKey<String>("KEY_DUMMY", String.class);

    public static final ConfigKey<String> KEY_EXTRA_HTML_HEAD_CONTENT =
        new ConfigKey<String>("KEY_EXTRA_HTML_HEAD_CONTENT", String.class);

    public static final ConfigKey<String> KEY_EXTRA_HEADER_CONTENT =
        new ConfigKey<String>("KEY_EXTRA_HEADER_CONTENT", String.class);

    public static final ConfigKey<String> KEY_EXTRA_BODY_CONTENT =
        new ConfigKey<String>("KEY_EXTRA_BODY_CONTENT", String.class);

    private static final String ENTITY_KIND_APP_CONFIG = "AppConfig";
    private static final String ENTITY_PROPERTY_NAME = "NAME";
    private static final String ENTITY_PROPERTY_VALUE = "value";

    private static final AppConfig INSTANCE = new AppConfig();
    private final DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

    private AppConfig() {
        Query query = new Query(ENTITY_KIND_APP_CONFIG);
        if (ds.prepare(query).countEntities() == 0) {
            setProperty(KEY_DUMMY, "");
        }
    }

    public <T> void setProperty(ConfigKey<T> key, T value) {
        Entity entity = fetchEntity(key);
        entity.setProperty(ENTITY_PROPERTY_VALUE, value);
        ds.put(entity);
    }

    public <T> T getProperty(ConfigKey<T> key) {
        return getProperty(key, null);
    }

    private <T> Entity fetchEntity(ConfigKey<T> key) {
        Query query = new Query(ENTITY_KIND_APP_CONFIG)
            .addFilter(ENTITY_PROPERTY_NAME, FilterOperator.EQUAL, key.name);
        Entity entity = ds.prepare(query).asSingleEntity();
        if (entity == null) {
            entity = new Entity(ENTITY_KIND_APP_CONFIG);
            entity.setProperty(ENTITY_PROPERTY_NAME, key.name);
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(ConfigKey<T> key, T defaultValue) {
        Entity entity = fetchEntity(key);
        T value = (T) entity.getProperty(ENTITY_PROPERTY_VALUE);
        return (value != null) ? value : defaultValue;
    }

    public static AppConfig get() {
        return INSTANCE;
    }

    public static final class ConfigKey<T> implements Serializable {
        private static final long serialVersionUID = 3338684466397217733L;
        public final String name;
        public final Class<T> type;
        private ConfigKey(String name, Class<T> type) {
            this.name = name;
            this.type = type;
        }
        @Override
        public String toString() {
            return name;
        }
    }
}
