package wish.samples.kitchensink.base.jquery;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.JavascriptPackageResource;

public class JQuery {
    private static final HeaderContributor HEADER_CONTRIBUTION_JQUERY_UI_SMOOTHNESS_CSS = CSSPackageResource
        .getHeaderContribution("http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.0/themes/smoothness/jquery-ui.css");
    private static final HeaderContributor HEADER_CONTRIBUTION_JQUERY_UI_JS = JavascriptPackageResource
        .getHeaderContribution("http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js");
    private static final HeaderContributor HEADER_CONTRIBUTION_JQUERY_JS = JavascriptPackageResource
        .getHeaderContribution("http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js");

    public static void addHeaderContributionsTo(Component component) {
        component.add(HEADER_CONTRIBUTION_JQUERY_JS);
        component.add(HEADER_CONTRIBUTION_JQUERY_UI_JS);
        component.add(HEADER_CONTRIBUTION_JQUERY_UI_SMOOTHNESS_CSS);
    }

    public static JQueryReadyHeaderContributor ready(String... scriptLines) {
        return new JQueryReadyHeaderContributor(scriptLines);
    }

    public static JQueryReadyHeaderContributor ready(Component target, String... scriptLines) {
        return new JQueryReadyHeaderContributor(target, scriptLines);
    }

    public static String $(Component target, String... scriptLines) {
        return "$('#" + target.getMarkupId() + "')" + StringUtils.join(scriptLines, "\n");
    }

    public static JQueryBehavior script(String... scriptLines) {
        return new JQueryBehavior(scriptLines);
    }
}
