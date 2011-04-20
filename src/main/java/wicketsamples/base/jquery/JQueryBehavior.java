package wicketsamples.base.jquery;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;

public class JQueryBehavior extends Behavior {
    private static final long serialVersionUID = 1L;

    private final String script;

    public JQueryBehavior(String... scriptLines) {
        this.script = StringUtils.join(scriptLines, "\n");
    }

    @Override
    public void afterRender(Component component) {
        Response resp = RequestCycle.get().getResponse();
        resp.write("<script type='text/javascript'> $('#" + component.getMarkupId() + "')" + script + " </script>");
    }
}
