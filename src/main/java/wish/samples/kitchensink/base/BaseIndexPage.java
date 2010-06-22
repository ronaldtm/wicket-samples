package wish.samples.kitchensink.base;

import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

import com.google.common.collect.Lists;

@SuppressWarnings("unchecked")
public abstract class BaseIndexPage extends BasePage {
    
    private List<PageBean> links = Lists.newArrayList();
    
    public BaseIndexPage() {
        add(new ListView<PageBean>("links", new PropertyModel(this, "links")) {
            private static final long serialVersionUID = -757689351285493796L;

            @Override
            protected void populateItem(ListItem<PageBean> item) {
                PageBean pageBean = item.getModelObject();
                BookmarkablePageLink link = new BookmarkablePageLink("link", pageBean.pageClass);
                link.add(new Label("title", pageBean.title));
                item.add(link);
                item.add(new Label("description", pageBean.description));
            }
        });
    }
    
    protected void addLink(Class<? extends Page> pageClass, String title, String description) {
        links.add(new PageBean(pageClass, title, description));
    }

    private class PageBean implements IClusterable {
        private static final long serialVersionUID = -3018927676806533341L;
        public final Class<? extends Page> pageClass;
        public final String title;
        public final String description;
        public PageBean(Class<? extends Page> pageClass, String title, String description) {
            this.pageClass = pageClass;
            this.title = title;
            this.description = description;
        }
    }
}
