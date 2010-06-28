package wicketsamples.base.highlighter;

import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.behavior.IBehavior;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.JavascriptPackageResource;
import org.apache.wicket.model.Model;

import wicketsamples.base.jquery.JQuery;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class Highlighter {

    public enum Syntax {
        ActionScript3,
        Shell,
        ColdFusion,
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
        Perl,
        PHP,
        Text,
        PowerShell,
        Python,
        Ruby,
        Scala,
        SQL,
        VisualBasic("vb"),
        XML,
        HTML;

        public final String alias;
        public final ImmutableSet<String> extensions;

        private Syntax(String alias, String... extensions) {
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
            this.alias = name().toLowerCase();
            this.extensions = ImmutableSet.of(alias);
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

        public IBehavior getBehavior() {
            return new AttributeAppender("class", Model.of("brush: " + alias), " ");
        }
    }

    static final IHeaderContributor SYNTAX_HIGHLIGHTER_CLIPBOARD_CONTRIBUTION = new IHeaderContributor() {
        private static final long serialVersionUID = 3849524398268176603L;

        @Override
        public void renderHead(IHeaderResponse response) {
            ResourceReference clipboardRef = new ResourceReference(Highlighter.class, "scripts/clipboard.swf");
            String script = String.format(
                "SyntaxHighlighter.config.clipboardSwf = '%s';\n" +
                "SyntaxHighlighter.all();", RequestCycle.get().urlFor(clipboardRef));
            response.renderJavascript(script, null);
        }

    };

    public static void addHeaderContributions(Component comp) {
        String[] scripts = new String[] {
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
            "scripts/shBrushXml.js" };
        String[] styles = new String[] {
            "styles/shCore.css",
            "styles/shThemeDefault.css" };

        for (String script : scripts)
            comp.add(JavascriptPackageResource.getHeaderContribution(Highlighter.class, script));
        for (String style : styles)
            comp.add(CSSPackageResource.getHeaderContribution(Highlighter.class, style));
        comp.add(new HeaderContributor(SYNTAX_HIGHLIGHTER_CLIPBOARD_CONTRIBUTION));
        comp.add(JQuery.ready("SyntaxHighlighter.all();"));
    }

}
