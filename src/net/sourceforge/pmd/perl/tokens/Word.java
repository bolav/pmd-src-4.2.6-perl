package net.sourceforge.pmd.perl.tokens;

import net.sourceforge.pmd.perl.PerlParserTokenManager;
import net.sourceforge.pmd.perl.PerlParserConstants;
import net.sourceforge.pmd.perl.Token;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Word extends Token {
    static Pattern bareword = Pattern.compile("(\\w+(?:(?:\\'|::)\\w+)*(?:::)?).*");
    
    public Word (Token t) {
        super(t);
        kind = PerlParserConstants.WORD;
    }
    
    public void checkSpecial (PerlParserTokenManager m) {
        // TODO: Check that previous is a semicolon! (or nothing)
        // Token prev = m.getPrevious();
        if (getImage().equals("use")) {
            kind = PerlParserConstants.USE;
        }
        else if (getImage().equals("package")) {
            kind = PerlParserConstants.PACKAGE;
        }
    }
    
    public boolean onLineChar (PerlParserTokenManager t) {
        String rest = t.getCurrentLine().substring(t.getLineCursor());
        Matcher m;
        m = bareword.matcher(rest);
        if (m.matches()) {
            setImage(m.group(1));
            t.incLineCursor(m.group(1).length());
            checkSpecial(t);
            setVars(t);
            t.addToken(this);
            return true;
        }
        System.out.println("Problem");
        return false;
    }
    
    public String toString () {
        return "Word ("+getImage()+")";
    }
}