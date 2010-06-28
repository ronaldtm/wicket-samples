package wicketsamples.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.util.io.IOUtils;

public class URLContentModel extends AbstractReadOnlyModel<String> {
    private static final long serialVersionUID = 5912098730402032637L;

    private final URL url;

    public URLContentModel(URL url) {
        this.url = url;
    }

    @Override
    public String getObject() {
        InputStream input = null;
        try {

            input = url.openStream();
            return IOUtils.toString(input);

        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }
}
