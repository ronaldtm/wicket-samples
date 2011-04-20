package wicketsamples.base.jquery;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

/**
 * @author tetsuo
 */
public class JQueryReadyHeaderContributor extends Behavior {
    private static final long serialVersionUID = 1L;

    private final Component target;
    private final String script;

    public JQueryReadyHeaderContributor(String... scriptLines) {
        this.target = null;
        this.script = StringUtils.join(scriptLines, "\n");
    }

    public JQueryReadyHeaderContributor(Component target, String... scriptLines) {
        this.target = target;
        this.script = StringUtils.join(scriptLines, "\n");
        target.setOutputMarkupId(true);
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        JQuery.getHeaderContributions().renderHead(component, response);
        StringBuilder sb = new StringBuilder();
        sb.append("$(document).ready(function(){");
        if (target != null) {
            sb.append("$('#" + target.getMarkupId() + "').");
        }
        sb.append(script);
        sb.append("});");
        response.renderJavaScript(sb.toString(), component.getMarkupId() + "_" + getClass().getSimpleName());
    }
}
