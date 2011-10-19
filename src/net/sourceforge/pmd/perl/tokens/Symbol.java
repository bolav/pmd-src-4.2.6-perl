package net.sourceforge.pmd.perl.tokens;

import net.sourceforge.pmd.perl.PerlParserTokenManager;
import net.sourceforge.pmd.perl.PerlParserConstants;
import net.sourceforge.pmd.perl.Token;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Symbol extends Token {
    
    static Pattern bareword = Pattern.compile("([\\w:\\']+).*");
    StringBuffer buf = new StringBuffer();

    public Symbol (String s, Token t) {
        super(t);
        buf.append(s);
    }
    
    public boolean onLineChar (PerlParserTokenManager t) {
        char c = t.getCurrentChar();
        boolean usechar = false;
        
        String rest = t.getCurrentLine().substring(t.getLineCursor());
        Matcher m;
        m = bareword.matcher(rest);
        
        if (m.matches()) {
            buf.append(m.group(1));
            setImage(buf.toString());
            t.incLineCursor(m.group(1).length());
            setVars(t);
            t.addToken(this);
            return false;
        }
        setImage(buf.toString());
        t.addToken(this);
        return false;
    }
    
    public String toString () {
        return "Symbol ("+getImage()+")";
    }
}