package wish.samples.kitchensink.base.highlighter;

import org.apache.wicket.Component;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.JavascriptPackageResource;

import wish.samples.kitchensink.base.jquery.JQuery;

public class Highligher {

    static final IHeaderContributor SYNTAX_HIGHLIGHTER_CLIPBOARD_CONTRIBUTION = new IHeaderContributor() {
        private static final long serialVersionUID = 3849524398268176603L;

        @Override
        public void renderHead(IHeaderResponse response) {
            ResourceReference clipboardRef = new ResourceReference(Highligher.class, "scripts/clipboard.swf");
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
            comp.add(JavascriptPackageResource.getHeaderContribution(Highligher.class, script));
        for (String style : styles)
            comp.add(CSSPackageResource.getHeaderContribution(Highligher.class, style));
        comp.add(new HeaderContributor(SYNTAX_HIGHLIGHTER_CLIPBOARD_CONTRIBUTION));
        comp.add(JQuery.ready("SyntaxHighlighter.all();"));
    }

}
