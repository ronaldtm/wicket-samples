package wicketsamples.ajax;

import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.DefaultCssAutocompleteTextField;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import wicketsamples.base.BasePage;

import com.google.common.collect.Lists;

@SuppressWarnings("unchecked")
public class AutocompletePage extends BasePage {
    List<String> colorOptions = Lists.newArrayList(
        "black", "blue", "cyan", "gray", "green", "magenta", "orange", "pink", "red", "white", "yellow");

    String color;

    @SuppressWarnings("rawtypes")
    public AutocompletePage() {
        super("Autocomplete");

        Form form = new Form("form");
        add(form);

        form.add(new DefaultCssAutocompleteTextField("color", new PropertyModel(this, "color")) {
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

        add(new FeedbackPanel("feedback"));
    }
}
