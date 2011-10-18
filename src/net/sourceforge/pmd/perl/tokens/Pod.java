package net.sourceforge.pmd.perl.tokens;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import net.sourceforge.pmd.perl.PerlParserTokenManager;
import net.sourceforge.pmd.perl.Token;
import net.sourceforge.pmd.perl.PerlParserConstants;

public class Pod extends Token {
    
    StringBuffer buf   = new StringBuffer();
    Pattern pod        = Pattern.compile("^=(\\w+).*");
    
    public Pod (String s, Token t) {
        super(t);
        buf.append(s);
        buf.append("\n");
        kind = PerlParserConstants.POD;
    }
    
    public String getImage () {
        return buf.toString();
    }

    public boolean onLineStart (PerlParserTokenManager t) {
        String line = t.getCurrentLine();
        Matcher m;
        m = pod.matcher(line);
        if (m.matches()) {
            if (m.group(1).equals("cut")) {
                t.addToken(this);
                return true;
            }
        }
        buf.append(line);
        buf.append("\n");
        return true;
    }
}