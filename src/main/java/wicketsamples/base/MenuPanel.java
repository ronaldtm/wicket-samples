package wicketsamples.base;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import wicketsamples.App;
import wicketsamples.App.PageCategory;
import wicketsamples.App.PageItem;
import wicketsamples.base.jquery.JQuery;

final class MenuPanel extends Panel {
    private static final long serialVersionUID = -7402552099056811629L;

    MenuPanel(String id) {
        super(id);
        setOutputMarkupId(true);

        add(new ListView<PageCategory>("categories", App.get().getPageCategories()) {
            private static final long serialVersionUID = -524720473542630510L;
            @Override
            protected void populateItem(final ListItem<PageCategory> categoryItem) {
                categoryItem.setModel(new CompoundPropertyModel<PageCategory>(categoryItem.getModel()));
                categoryItem.add(new Label("title"));
                categoryItem.add(new ListView<PageItem>("links") {
                    private static final long serialVersionUID = -757689351285493796L;
                    @Override
                    protected void populateItem(ListItem<PageItem> linkItem) {
                        linkItem.setModel(new CompoundPropertyModel<PageItem>(linkItem.getModel()));
                        PageItem pageItem = linkItem.getModelObject();

                        BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>("link", pageItem.pageClass);
                        linkItem.add(link);
                        link.add(new Label("title"));
                        link.add(new SimpleAttributeModifier("title", pageItem.description));
                        if (pageItem.pageClass == getPage().getPageClass()) {
                            link.setEnabled(false);
                            add(JQuery.ready(MenuPanel.this, "accordion('activate', " + categoryItem.getIndex() + ");"));
                        }
                    }
                });
            }
        });
        add(JQuery.ready(this, "accordion();"));
    }
}