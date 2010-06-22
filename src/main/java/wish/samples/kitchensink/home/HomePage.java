package wish.samples.kitchensink.home;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;

import wish.samples.kitchensink.App;
import wish.samples.kitchensink.App.PageCategory;
import wish.samples.kitchensink.App.PageItem;
import wish.samples.kitchensink.base.BasePage;
import wish.samples.kitchensink.base.jquery.JQuery;

@SuppressWarnings("unchecked")
public class HomePage extends BasePage {

    public HomePage() {
        add(new ListView<PageCategory>("categories", App.get().getPageCategories()) {
            @Override
            protected void populateItem(ListItem<PageCategory> item) {
                item.setModel(new CompoundPropertyModel(item.getModel()));
                item.add(new Label("title"));
                item.add(new ListView<PageItem>("links") {
                    private static final long serialVersionUID = -757689351285493796L;
                    @Override
                    protected void populateItem(ListItem<PageItem> item) {
                        PageItem pageBean = item.getModelObject();
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
}
