package wicketsamples.base.jquery;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

public class JQuery {
    private static final String URL_JQUERY_UI_CSS =
            "http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.0/themes/cupertino/jquery-ui.css";
    private static final String URL_JQUERY_UI_JS =
            "http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js";
    private static final String URL_JQUERY_JS =
            "http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js";

    private static final Behavior HEADER_CONTRIBUTIONS = new Behavior() {
        @Override
        public void renderHead(Component component, IHeaderResponse response) {
            response.renderCSSReference(URL_JQUERY_UI_CSS);
            response.renderJavaScriptReference(URL_JQUERY_JS);
            response.renderJavaScriptReference(URL_JQUERY_UI_JS);
        }
    };

    public static Behavior getHeaderContributions() {
        return HEADER_CONTRIBUTIONS;
    }

    public static JQueryReadyHeaderContributor ready(String... scriptLines) {
        return new JQueryReadyHeaderContributor(scriptLines);
    }

    public static JQueryReadyHeaderContributor ready(Component target, String... scriptLines) {
        return new JQueryReadyHeaderContributor(target, scriptLines);
    }

    public static String $(Component target, String... scriptLines) {
        target.setOutputMarkupId(true);
        return "$('#" + target.getMarkupId() + "')" + StringUtils.join(scriptLines, "\n");
    }

    public static JQueryBehavior script(String... scriptLines) {
        return new JQueryBehavior(scriptLines);
    }
}
