package wicketsamples;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.Page;

import com.google.common.collect.Lists;

public class PageCategory implements IClusterable {
    private static final long serialVersionUID = -1209873707198411368L;
    public final String title;
    public final String path;
    public final List<PageItem> links = Lists.newArrayList();
    public PageCategory(String path, String title, PageItem... links) {
        this.path = path;
        this.title = title;
        this.links.addAll(Arrays.asList(links));
    }
    public PageCategory add(Class<? extends Page> pageClass, String title, String description,
        Class<?>... additionalResources) {

        links.add(new PageItem(pageClass, title, description, additionalResources));
        return this;
    }
}