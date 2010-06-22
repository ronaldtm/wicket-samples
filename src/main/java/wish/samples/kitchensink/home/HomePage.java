package wish.samples.kitchensink.home;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import wish.samples.kitchensink.base.BasePage;

@SuppressWarnings("unchecked")
public class HomePage extends BasePage {
    public HomePage() {

        List<PageBean> links = Arrays.asList(
            new PageBean(wish.samples.kitchensink.form.IndexPage.class, "form", ""),
            new PageBean(wish.samples.kitchensink.ajax.IndexPage.class, "ajax", ""));

        add(new ListView<PageBean>("links", Model.ofList(links)) {
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