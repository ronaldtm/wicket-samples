package wicketsamples.base;

import java.util.List;

import org.apache.wicket.IPageMap;
import org.apache.wicket.PageMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import wicketsamples.PageCategory;
import wicketsamples.PageItem;
import wicketsamples.base.jquery.JQuery;

final class MenuPanel extends Panel {
    private static final long serialVersionUID = -7402552099056811629L;

    MenuPanel(String id, final String targetFrameId, IModel<? extends List<? extends PageCategory>> model) {
        super(id);
        setOutputMarkupId(true);

        add(new ListView<PageCategory>("categories", model) {
            private static final long serialVersionUID = 1;

            @Override
            protected void populateItem(final ListItem<PageCategory> categoryItem) {
                categoryItem.setModel(new CompoundPropertyModel<PageCategory>(categoryItem.getModel()));
                categoryItem.add(new Label("title"));

                categoryItem.add(new ListView<PageItem>("links") {
                    private static final long serialVersionUID = 1;
                    @Override
                    protected void populateItem(ListItem<PageItem> listItem) {
                        PageItem pageItem = listItem.getModelObject();

                        IPageMap pageMap = PageMap.forName(PageMap.DEFAULT_NAME);
                        String pagePath = urlFor(pageMap, pageItem.pageClass, new PageParameters()).toString();
                        String sourcePath = urlFor(pageMap, SourceCodePage.class,
                            new PageParameters("pageClass=" + pageItem.pageClass.getName())).toString();

                        String script = String.format("openPage('%s','%s'); return false;", pagePath, sourcePath);
                        listItem.add(new Label("link", Model.of(pageItem.title))
                            .add(new SimpleAttributeModifier("title", pageItem.description))
                            .add(new SimpleAttributeModifier("onclick", script)));
                    }
                });
            }
        });
        add(JQuery.ready(this, "accordion();"));
    }
}