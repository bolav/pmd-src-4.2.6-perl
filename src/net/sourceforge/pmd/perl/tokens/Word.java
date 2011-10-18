package net.sourceforge.pmd.perl.tokens;

import net.sourceforge.pmd.perl.PerlParserTokenManager;
import net.sourceforge.pmd.perl.PerlParserConstants;
import net.sourceforge.pmd.perl.Token;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Word extends Token {
    StringBuffer buf = new StringBuffer();
    Pattern bareword = Pattern.compile("(\\w+(?:(?:\\'|::)\\w+)*(?:::)?).*");
    
    public Word (char c, Token t) {
        super(t);
        buf.append(c);
        kind = PerlParserConstants.WORD;
    }
    
    public String getImage () {
        return buf.toString();
    }
    
    public boolean onLineChar (PerlParserTokenManager t) {
        String rest = t.getCurrentLine().substring(t.getLineCursor());
        System.out.println("rest "+rest);
        Matcher m;
        m = bareword.matcher(rest);
        if (m.matches()) {
            buf = new StringBuffer(m.group(1));
            t.incLineCursor(m.group(1).length());
            endColumn = t.getLineCursor();
            t.addToken(this);
            return true;
        }
        return false;
    }
    
    public String toString () {
        return "Word ("+getImage()+")";
    }
}