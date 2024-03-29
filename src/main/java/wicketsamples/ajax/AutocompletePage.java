package wicketsamples.ajax;

import com.google.common.collect.Lists;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.DefaultCssAutoCompleteTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"serial", "rawtypes", "unchecked"})
public class AutocompletePage extends WebPage {
    List<String> colorOptions = Lists.newArrayList(
            "black", "blue", "cyan", "gray", "green", "magenta", "orange", "pink", "red", "white", "yellow");

    String color;

    public AutocompletePage() {

        Form form = new Form("form");
        add(form);

        form.add(new DefaultCssAutoCompleteTextField("color", new PropertyModel(this, "color")) {
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
