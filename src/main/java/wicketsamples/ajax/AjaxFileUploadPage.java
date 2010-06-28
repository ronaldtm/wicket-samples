package wicketsamples.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.crypt.Base64;
import org.apache.wicket.util.lang.Bytes;

import wicketsamples.base.BasePage;

@SuppressWarnings("unchecked")
public class AjaxFileUploadPage extends BasePage {

    FileUpload file;

    public AjaxFileUploadPage() {
        super("Ajax File Upload");

        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);

        Form form = new Form("form", new CompoundPropertyModel(this));
        add(form);
        form.setOutputMarkupId(true);
        form.setMultiPart(true);
        form.setMaxSize(Bytes.kilobytes(1000));

        form.add(new FileUploadField("file"));

        form.add(new AjaxButton("send") {
            private static final long serialVersionUID = -3259847450071890320L;
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                if (file != null) {
                    info(String.format("Sent: %s (%s), %d bytes [%s]",
                        file.getClientFileName(), file.getContentType(),
                        file.getSize(), new String(Base64.encodeBase64(file.getMD5()))));
                } else {
                    info("No file sent");
                }
                target.addComponent(feedback);
            }
            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                target.addComponent(feedback);
            }
        });
    }
}
