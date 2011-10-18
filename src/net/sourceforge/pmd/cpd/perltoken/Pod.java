package net.sourceforge.pmd.cpd.perltoken;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import net.sourceforge.pmd.cpd.PerlTokenizer;

public class Pod extends Token {
    
    StringBuffer buf   = new StringBuffer();
    Pattern pod        = Pattern.compile("^=(\\w+).*");
    
    public Pod (String s) {
        buf.append(s);
        buf.append("\n");
    }

    public boolean onLineStart (PerlTokenizer t) {
        String line = t.getCurrentLine();
        Matcher m;
        m = pod.matcher(line);
        if (m.matches()) {
            if (m.group(1).equals("cut")) {
                t.newToken("Pod", buf.toString());
                return true;
            }
        }
        buf.append(line);
        buf.append("\n");
        return true;
    }
}