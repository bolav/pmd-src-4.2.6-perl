package net.sourceforge.pmd.perl.tokens;

import net.sourceforge.pmd.perl.PerlParserTokenManager;
import net.sourceforge.pmd.perl.PerlParserConstants;
import net.sourceforge.pmd.perl.Token;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Number extends Token {
    
    StringBuffer buf = new StringBuffer();
    // static Pattern special = Pattern.compile("-?0_*");

    public Number (Token t) {
        super(t);
        kind = PerlParserConstants.DECIMAL_LITERAL;
    }
    
    public boolean onLineChar (PerlParserTokenManager t) {
        char c = t.getCurrentChar();
        boolean usechar = false;
        if (c == '_') {
            usechar = true;
        }
        
        if ((c >= '0') && (c <= '9')) {
            usechar = true;
        }

        if (usechar) {
            t.incLineCursor();
            buf.append(c);
            return true;            
        }
        else {
            setImage(buf.toString());
            t.addToken(this);
            return false;
        }
    }
    
    public String toString () {
        return "Number ("+getImage()+")";
    }
}