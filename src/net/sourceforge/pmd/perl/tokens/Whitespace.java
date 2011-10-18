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
        System.out.println("Nothing for "+line);
        return false;
    }
    
    public boolean onLineChar (PerlParserTokenManager t) {
        char c = t.getCurrentChar();
        if ((c >= 'a') && (c <= 'u')) {
            t.setToken(new Word(c, this));
            System.out.println("We have "+c);
        }
        else if (c == ' ') {
            buf.append(c);
            t.incLineCursor();
        }
        else {
            t.incLineCursor();
        }
        return false;
    }
}