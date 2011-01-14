package wicketsamples.base;

import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;

import wicketsamples.App;
import wicketsamples.PageCategory;

public final class PageCategoriesModel extends LoadableDetachableModel<List<? extends PageCategory>> {
    @Override
    protected List<? extends PageCategory> load() {
        return App.get().getPageCategories();
    }
}