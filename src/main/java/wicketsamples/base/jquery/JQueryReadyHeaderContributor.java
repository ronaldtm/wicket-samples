package wicketsamples.base.jquery;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.template.JavaScriptTemplate;
import org.apache.wicket.util.template.PackagedTextTemplate;
import org.apache.wicket.util.template.TextTemplateHeaderContributor;

/**
 * @author tetsuo
 */
public class JQueryReadyHeaderContributor extends TextTemplateHeaderContributor {
    private static final long serialVersionUID = 1L;
    public JQueryReadyHeaderContributor(String... scriptLines) {
        super(new JavaScriptTemplate(
            new PackagedTextTemplate(JQueryReadyHeaderContributor.class, "JQueryReadyHeaderContributor.ready.js")),
            toMapModel("script", StringUtils.join(scriptLines, "\n")));
    }
    public JQueryReadyHeaderContributor(Component target, String... scriptLines) {
        this("$('#" + target.getMarkupId() + "')." + StringUtils.join(scriptLines, "\n"));
        target.setOutputMarkupId(true);
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static IModel<Map<String, Object>> toMapModel(String key, String value) {
        HashMap<String, Object> variables = new HashMap<String, Object>();
        variables.put(key, value);
        return new Model(variables);
    }
}
