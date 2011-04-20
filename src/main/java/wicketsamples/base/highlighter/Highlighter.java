package wicketsamples.base.highlighter;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;
import wicketsamples.base.jquery.JQuery;

import java.util.Set;

public class Highlighter {

    public enum Syntax {
        ActionScript3,
        Shell("sh"),
        ColdFusion("cfm", "cfml"),
        Csharp,
        Cpp,
        CSS,
        Delphi,
        Diff,
        Erlang,
        Groovy,
        JavaScript,
        Java,
        JavaFX,
        Perl("pl"),
        PHP,
        Text,
        PowerShell,
        Python("py"),
        Ruby,
        Scala,
        SQL,
        VisualBasic("vb"),
        XML,
        HTML;

        public final String alias;
        public final ImmutableSet<String> extensions;

        private Syntax(String alias, String... extensions) {
            if (alias == null)
                alias = name().toLowerCase();
            if (extensions == null)
                extensions = new String[]{alias};

            Set<String> extensionSet = Sets.newLinkedHashSet();
            for (String extension : extensions)
                extensionSet.add(extension.toLowerCase());
            extensionSet.add(alias);
            String[] extensionArray = Lists.newArrayList(extensionSet).toArray(new String[extensionSet.size()]);

            this.alias = alias;
            this.extensions =
                ImmutableSet.of(extensionArray);
        }

        private Syntax() {
            this(null, (String[]) null);
        }

        public boolean match(String filename) {
            String extension = filename.substring(filename.lastIndexOf('.') + 1);
            return extensions.contains(extension);
        }

        public static Syntax of(String filename) {
            for (Syntax syntax : values()) {
                if (syntax.match(filename))
                    return syntax;
            }
            return Text;
        }

        public Behavior getBehavior() {
            return new AttributeAppender("class", Model.of("brush: " + alias), " ");
        }
    }

    public static Behavior getHeaderContributions() {
        return new Behavior() {
            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                String[] styles = new String[]{
                    "styles/shCore.css",
                    "styles/shThemeDefault.css"};
                String[] scripts = new String[]{
                    "scripts/shCore.js",
                    "scripts/shBrushBash.js",
                    "scripts/shBrushCpp.js",
                    "scripts/shBrushCSharp.js",
                    "scripts/shBrushCss.js",
                    "scripts/shBrushDelphi.js",
                    "scripts/shBrushDiff.js",
                    "scripts/shBrushGroovy.js",
                    "scripts/shBrushJava.js",
                    "scripts/shBrushJScript.js",
                    "scripts/shBrushPhp.js",
                    "scripts/shBrushPlain.js",
                    "scripts/shBrushPython.js",
                    "scripts/shBrushRuby.js",
                    "scripts/shBrushScala.js",
                    "scripts/shBrushSql.js",
                    "scripts/shBrushVb.js",
                    "scripts/shBrushXml.js"};

                for (String style : styles)
                    response.renderCSSReference(new PackageResourceReference(Highlighter.class, style));
                for (String script : scripts)
                    response.renderJavaScriptReference(new JavaScriptResourceReference(Highlighter.class, script));

                CharSequence clipboardSwfUrl = RequestCycle.get().urlFor(
                    new PackageResourceReference(Highlighter.class, "scripts/clipboard.swf"), new PageParameters());
                JQuery.ready("SyntaxHighlighter.config.clipboardSwf = '" + clipboardSwfUrl + "';" +
                    "SyntaxHighlighter.all();").renderHead(component, response);
            }
        };
    }

}
