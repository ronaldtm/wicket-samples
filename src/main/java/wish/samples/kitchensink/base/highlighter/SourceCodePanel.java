package wish.samples.kitchensink.base.highlighter;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class SourceCodePanel extends Panel {
    private static final long serialVersionUID = -8019967803675238350L;

    public SourceCodePanel(String id, IModel<String> model, String lang) {
        super(id, model);
        Label sourceCode = new Label("sourceCode", model);
        sourceCode.add(new AttributeAppender("class", Model.of("brush: " + lang), " "));
        add(sourceCode);

        Highligher.addHeaderContributions(this);
    }

}
