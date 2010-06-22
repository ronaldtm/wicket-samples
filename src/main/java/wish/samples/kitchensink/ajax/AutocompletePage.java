package wish.samples.kitchensink.ajax;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteSettings;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import wish.samples.kitchensink.base.BasePage;

import com.google.common.collect.Lists;

@SuppressWarnings("unchecked")
public class AutocompletePage extends BasePage {
    List<String> colorOptions = Lists.newArrayList(
        "blue", "green", "red", "yellow", "orange", "white", "gray", "black");
    String color;

    public AutocompletePage() {

        Form form = new Form("form", new CompoundPropertyModel(this));

        AutoCompleteSettings settings = new AutoCompleteSettings();
        form.add(new AutoCompleteTextField("color", settings) {
            private static final long serialVersionUID = -1663184879501520925L;
            @Override
            protected Iterator getChoices(String input) {
                List<String> choices = Lists.newArrayList();
                for (String color : colorOptions)
                    if (color.startsWith(input.toLowerCase()))
                        choices.add(color);
                return choices.iterator();
            }
        });

        form.add(new Button("send") {
            private static final long serialVersionUID = -3259847450071890320L;
            @Override
            public void onSubmit() {
                info("Sent: " + color);
            }
        });

        add(form);
        add(new FeedbackPanel("feedback"));
    }
}
