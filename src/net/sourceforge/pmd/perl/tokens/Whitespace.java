package net.sourceforge.pmd.perl.tokens;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import net.sourceforge.pmd.perl.PerlParserTokenManager;
import net.sourceforge.pmd.perl.Token;
import net.sourceforge.pmd.perl.PerlParserConstants;

public class Whitespace extends Token {
    
    Pattern whitespace = Pattern.compile("^\\s*$");
    Pattern comment    = Pattern.compile("\\s*#.*");
    Pattern pod        = Pattern.compile("^=(\\w+).*");
    StringBuffer buf   = new StringBuffer();
    boolean hastoken   = false;
    
    public Whitespace() {
        kind = PerlParserConstants.WHITESPACE;        
    }

    public Whitespace(String s, Token t) {
        super(s, t);
        kind = PerlParserConstants.WHITESPACE;
    }
    
    public boolean onLineStart (PerlParserTokenManager t) {
        String line = t.getCurrentLine();
        beginLine   = t.getLineNumber();
        endLine     = t.getLineNumber();
        beginColumn = 0;
 
        Matcher m;
        m = whitespace.matcher(line);
        if (m.matches()) {
            t.addToken(new Whitespace(line, this));
            return true;
        }
        m = comment.matcher(line);
        if (m.matches()) {
            t.addToken(new Comment(line, this));
            return true;
        }
        m = pod.matcher(line);
        if (m.matches()) {
            if (m.group(1).equals("cut")) {
                t.addToken(new Pod(line, this));
                return true;
            }
            else {
                t.setToken(new Pod(line, this));
                return true;                
            }
        }
        return false;
    }
    
    public boolean onLineChar (PerlParserTokenManager t) {
        setVars(t);
        char c = t.getCurrentChar();
        if (((c >= 'a') && (c <= 'z')) ||
            ((c >= 'A') && (c <= 'Z'))
           ) {
            setToken(t,new Word(this));
        }
        else if ((c == ' ') || (c == 9)) {
            buf.append(c);
            hastoken = true;
            t.incLineCursor();
        }
        else if (c == ';') {
            addToken(t,new Semicolon(this));
            t.incLineCursor();
        }
        else {
            t.incLineCursor();
        }
        return false;
    }
    
    private void finishMe (PerlParserTokenManager m) {
        if (hastoken) {
            m.addToken(new Whitespace(buf.toString(), this));
        }
        hastoken = false;
        buf = new StringBuffer();
    }
    
    private void setToken (PerlParserTokenManager m, Token t) {
        finishMe(m);
        m.setToken(t);
    }
    
    private void addToken (PerlParserTokenManager m, Token t) {
        finishMe(m);
        m.addToken(t);
    }
}