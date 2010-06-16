package wish.samples.kitchensink.source;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.servlet.AbortWithWebErrorCodeException;
import org.apache.wicket.util.io.IOUtils;

import wish.samples.kitchensink.base.highlighter.SourceCodePanel;
import wish.samples.kitchensink.base.jquery.TabPanel;

@SuppressWarnings("unchecked")
public class SourcePage extends WebPage {

    String pageClassSimpleName;
    String pageString;
    String markupString;

    public SourcePage(PageParameters params) {
        super(params);
        String pageClassName = params.getString("page");

        loadSourceCode(pageClassName);

        TabPanel tabs = new TabPanel("tabs");
        tabs.append(pageClassSimpleName + ".java",
            new SourceCodePanel("source", new PropertyModel(this, "pageString"), "java"));
        tabs.append(pageClassSimpleName + ".html",
            new SourceCodePanel("source", new PropertyModel(this, "markupString"), "html"));
        add(tabs);
    }

    private void loadSourceCode(String baseClassName) {
        try {
            Class<?> baseClass = (Class<?>) Class.forName(baseClassName);
            pageClassSimpleName = baseClass.getSimpleName();
            pageString = resourceAsString(baseClass, baseClass.getSimpleName() + ".java");
            markupString = resourceAsString(baseClass, baseClass.getSimpleName() + ".html");
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AbortWithWebErrorCodeException(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    private String resourceAsString(Class<?> pageClass, String resourcePath) throws IOException {
        InputStream input = null;
        try {
            input = pageClass.getResourceAsStream(resourcePath);
            return IOUtils.toString(input);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }
}
