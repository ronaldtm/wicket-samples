package wicketsamples;

import java.net.URL;
import java.util.List;
import java.util.Set;

import org.apache.wicket.IClusterable;
import org.apache.wicket.Page;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class PageItem implements IClusterable {
    private static final long serialVersionUID = 1;

    private static final String[] EXTENSIONS = new String[] { "java", "html", "js", "css" };

    public final Class<? extends Page> pageClass;
    public final String title;
    public final String description;
    private final Class<?>[] additionalResources;

    public PageItem(Class<? extends Page> pageClass, String title, String description, Class<?>... additionalResources) {
        this.pageClass = pageClass;
        this.title = title;
        this.description = description;
        this.additionalResources = additionalResources;
    }

    public URL[] getResources() {
        List<URL> urls = Lists.newArrayList();
        Set<Class<?>> classes = Sets.newLinkedHashSet();
        classes.add(pageClass);
        classes.addAll(Lists.newArrayList(additionalResources));

        for (Class<?> clazz : classes) {
            String name = clazz.getSimpleName();
            for (String ext : EXTENSIONS) {
                URL url = clazz.getResource(name + "." + ext);
                if (url != null) {
                    urls.add(url);
                }
            }
        }

        return urls.toArray(new URL[urls.size()]);
    }
}