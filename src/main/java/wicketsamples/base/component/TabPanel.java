package wicketsamples.base.component;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainerWithAssociatedMarkup;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import wicketsamples.base.jquery.JQuery;

public class TabPanel extends Panel {
    private static final long serialVersionUID = -8108028474236834120L;

    RepeatingView handles;
    RepeatingView tabs;
    public TabPanel(String id) {
        super(id);
        setOutputMarkupId(true);
        JQuery.addHeaderContributionsTo(this);

        add(handles = new RepeatingView("handles"));
        add(tabs = new RepeatingView("tabs"));

        add(JQuery.ready(JQuery.$(this, ".tabs();")));
    }

    public TabPanel append(String labelText, WebMarkupContainerWithAssociatedMarkup content) {
        Fragment tab = new Fragment(tabs.newChildId(), "tab", this);
        tab.setOutputMarkupId(true);
        tab.add(new RepeatingView("container").add(content));

        Fragment handle = new Fragment(handles.newChildId(), "handle", this);
        handle.setOutputMarkupId(true);
        Label label = new Label("label", labelText);
        label.add(new SimpleAttributeModifier("href", "#" + tab.getMarkupId()));
        handle.add(label);

        tabs.add(tab);
        handles.add(handle);
        return this;
    }
}
