package wish.samples.kitchensink.base.jquery;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.behavior.AbstractBehavior;

public class JQueryBehavior extends AbstractBehavior {
    private static final long serialVersionUID = 6915030076293491682L;

    private final String script;
    public JQueryBehavior(String... scriptLines) {
        this.script = StringUtils.join(scriptLines, "\n");
    }

    public void onRendered(Component component) {
        super.onRendered(component);
        Response resp = RequestCycle.get().getResponse();
        resp.write(
            String.format("<script type='text/javascript'> $('#%s')%s </script>",
                component.getMarkupId(), script));
    }
}
