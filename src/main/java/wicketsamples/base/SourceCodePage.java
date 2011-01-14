package wicketsamples.base;

import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;

import com.google.common.collect.Lists;

import wicketsamples.App;
import wicketsamples.PageCategory;
import wicketsamples.PageItem;
import wicketsamples.base.highlighter.Highlighter;
import wicketsamples.base.highlighter.Highlighter.Syntax;
import wicketsamples.source.SourceCodePanel;

public class SourceCodePage extends WebPage {

    public SourceCodePage(PageParameters parameters) {
        super(parameters);
        add(Highlighter.getHeaderContributions());

        add(new ListView<URL>("sourceFiles", new SourceClassesModel()) {
            @Override
            protected void populateItem(ListItem<URL> item) {
                URL url = item.getModelObject();
                String filename = StringUtils.substringAfterLast(url.getFile(), "/");
                
                item.add(new Label("file", filename));
                item.add(new SourceCodePanel("source", new URLContentModel(url), Syntax.of(filename)));
            }
        });
    }

    private final class SourceClassesModel extends AbstractReadOnlyModel<List<URL>> {
        @Override
        public List<URL> getObject() {
            String pageClassName = getPageParameters().getString("pageClass");
            for (PageCategory pageCategory : App.get().getPageCategories()) {
                for (PageItem pageItem : pageCategory.links) {
                    if (pageItem.pageClass.getName().equals(pageClassName)) {
                        return Lists.newArrayList(pageItem.getResources());
                    }
                }
            }
            return Lists.newArrayList();
        }
    }

}
