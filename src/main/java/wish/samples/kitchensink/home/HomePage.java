package wish.samples.kitchensink.home;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.IClusterable;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;

import wish.samples.kitchensink.ajax.AjaxFileUploadPage;
import wish.samples.kitchensink.ajax.AutocompletePage;
import wish.samples.kitchensink.base.BasePage;
import wish.samples.kitchensink.base.jquery.JQuery;
import wish.samples.kitchensink.form.AjaxFormPage;
import wish.samples.kitchensink.form.SimpleFormPage;

import com.google.common.collect.Lists;

@SuppressWarnings("unchecked")
public class HomePage extends BasePage {

    public HomePage() {
        add(new ListView<CategoryBean>("categories", getCategories()) {
            @Override
            protected void populateItem(ListItem<CategoryBean> item) {
                item.setModel(new CompoundPropertyModel(item.getModel()));
                item.add(new Label("title"));
                item.add(new ListView<LinkBean>("links") {
                    private static final long serialVersionUID = -757689351285493796L;
                    @Override
                    protected void populateItem(ListItem<LinkBean> item) {
                        LinkBean pageBean = item.getModelObject();
                        BookmarkablePageLink link = new BookmarkablePageLink("link", pageBean.pageClass);
                        link.add(new Label("title", pageBean.title));
                        item.add(link);
                        item.add(new Label("description", pageBean.description));
                    }
                });
            }
        });
        add(JQuery.ready("$('#accordion').accordion()"));
    }

    private List<CategoryBean> getCategories() {
        List<CategoryBean> categories = Lists.newArrayList();
        categories.add(new CategoryBean("Forms")
            .add(SimpleFormPage.class, "Simple form", "Simple form")
            .add(AjaxFormPage.class, "Ajax form", "Simple form with ajax submit")
            );
        categories.add(new CategoryBean("Ajax")
            .add(AutocompletePage.class, "Auto-Complete", "Show matching options while you type")
            .add(AjaxFileUploadPage.class, "File Upload", "")
            );
        return categories;
    }

    class CategoryBean {
        public final String title;
        public final List<LinkBean> links = Lists.newArrayList();
        public CategoryBean(String title, LinkBean... links) {
            this.title = title;
            this.links.addAll(Arrays.asList(links));
        }
        public CategoryBean add(Class<? extends Page> pageClass, String title, String description) {
            links.add(new LinkBean(pageClass, title, description));
            return this;
        }
    }

    class LinkBean implements IClusterable {
        private static final long serialVersionUID = -3018927676806533341L;
        public final Class<? extends Page> pageClass;
        public final String title;
        public final String description;
        public LinkBean(Class<? extends Page> pageClass, String title, String description) {
            this.pageClass = pageClass;
            this.title = title;
            this.description = description;
        }
    }
}
