package wicketsamples.layout;

import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class FieldBorder extends Border {
    private static final long serialVersionUID = -3302702052863252181L;

    public FieldBorder(String id, String label, final FormComponent<?> component) {
        super(id);

        add(new Label("label", label));

        add(new WebMarkupContainer("required") {
            private static final long serialVersionUID = -2517917279014793049L;
            @Override
            public boolean isVisible() {
                return component.isRequired();
            }
        });

        add(component);

        add(new FeedbackPanel("feedback", new ComponentFeedbackMessageFilter(component)));
    }
}
