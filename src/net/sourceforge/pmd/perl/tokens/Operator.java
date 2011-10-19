package net.sourceforge.pmd.perl.tokens;

import net.sourceforge.pmd.perl.Token;
import net.sourceforge.pmd.perl.PerlParserConstants;
import net.sourceforge.pmd.perl.PerlParserTokenManager;

import java.util.*;

public class Operator extends Token {
    
    private static final Map<String, Boolean> OPERATOR;
    static {
        Map<String, Boolean> aMap = new HashMap<String, Boolean>();
        aMap.put("->",true);
        aMap.put("++",true);
        aMap.put("--",true);
        aMap.put("**",true);
        aMap.put("!",true);
        aMap.put("~",true);
        aMap.put("+",true);
        aMap.put("-",true);
        aMap.put("=~",true);
        aMap.put("!~",true);
        aMap.put("*",true);
        aMap.put("/",true);
        aMap.put("%",true);
        aMap.put("x",true);
        aMap.put(".",true);
        aMap.put("<<",true);
        aMap.put(">>",true);
        aMap.put("<",true);
        aMap.put(">",true);
        aMap.put("<=",true);
        aMap.put(">=",true);
        aMap.put("lt",true);
        aMap.put("gt",true);
        aMap.put("le",true);
        aMap.put("ge",true);
        aMap.put("==",true);
        aMap.put("!=",true);
        aMap.put("<=>",true);
        aMap.put("eq",true);
        aMap.put("ne",true);
        aMap.put("cmp",true);
        aMap.put("~~",true);
        aMap.put("&",true);
        aMap.put("|",true);
        aMap.put("^",true);
        aMap.put("&&",true);
        aMap.put("||",true);
        aMap.put("//",true);
        aMap.put("..",true);
        aMap.put("...",true);
        aMap.put("?",true);
        aMap.put(":",true);
        aMap.put("=",true);
        aMap.put("+=",true);
        aMap.put("-=",true);
        aMap.put("*=",true);
        aMap.put(".=",true);
        aMap.put("/=",true);
        aMap.put("//=",true);
        aMap.put("=>",true);
        aMap.put("<>",true);
        aMap.put("and",true);
        aMap.put("or",true);
        aMap.put("xor",true);
        aMap.put("not",true);
        aMap.put(",",true);
        OPERATOR = Collections.unmodifiableMap(aMap);
    }
    StringBuffer buf = new StringBuffer();
    
    public Operator (char c, Token t) {
        super(t);
        buf.append(c);
        kind = PerlParserConstants.OPERATOR;
    }
    
    public boolean onLineChar (PerlParserTokenManager t) {
        char c = t.getCurrentChar();
        boolean usechar = false;
        buf.append(c);

        if (OPERATOR.get(buf.toString()) != null) {
            t.incLineCursor();
            return true;            
        }
        else {
            buf.deleteCharAt(buf.length()-1);
            setImage(buf.toString());
            t.addToken(this);
            return false;
        }
    }
    
    public String toString () {
        return "Operator ("+getImage()+")";
    }
    
}