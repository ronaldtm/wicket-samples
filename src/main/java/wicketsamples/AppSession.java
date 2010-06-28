package wicketsamples;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

public class AppSession extends WebSession {
    private static final long serialVersionUID = 1L;

    public static AppSession get() {
        return (AppSession) WebSession.get();
    }

    public AppSession(Request request) {
        super(request);
    }
}
