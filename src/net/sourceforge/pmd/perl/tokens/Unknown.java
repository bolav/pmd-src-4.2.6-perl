package net.sourceforge.pmd.perl.tokens;

import net.sourceforge.pmd.perl.PerlParserTokenManager;
import net.sourceforge.pmd.perl.PerlParserConstants;
import net.sourceforge.pmd.perl.Token;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Unknown extends Token {
    
    StringBuffer buf = new StringBuffer();
    // static Pattern special = Pattern.compile("-?0_*");

    public Unknown (char c, Token t) {
        super(t);
        buf.append(c);
    }
    
    public boolean onLineChar (PerlParserTokenManager t) {
        char c = t.getCurrentChar();
        String s = buf.toString();
        
        if (s.equals("$")) {
            if (((c >= 'a') && (c <= 'z')) || 
                ((c >= 'A') && (c <= 'Z'))
               ) {
                // Yes.
                t.setToken(new Symbol(s, this));
                return true;
            }
        }
        setImage(s);
        t.addToken(this);
        return false;
    }
    
    public String toString () {
        return "Unknown ("+getImage()+")";
    }
}