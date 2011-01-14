package wicketsamples.source;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import wicketsamples.base.highlighter.Highlighter;

public class SourceCodePanel extends Panel {
    private static final long serialVersionUID = -8019967803675238350L;

    public SourceCodePanel(String id, IModel<String> model, Highlighter.Syntax syntax) {
        super(id, model);
        setOutputMarkupId(true);

        Label sourceCode = new Label("sourceCode", model);
        sourceCode.add(syntax.getBehavior());
        add(sourceCode);

        this.add(Highlighter.getHeaderContributions());
    }

}
