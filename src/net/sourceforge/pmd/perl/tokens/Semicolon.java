package net.sourceforge.pmd.perl.tokens;

import net.sourceforge.pmd.perl.Token;
import net.sourceforge.pmd.perl.PerlParserConstants;

public class Semicolon extends Token {
    
    public Semicolon (Token t) {
        super(";", t);
        kind = PerlParserConstants.SEMICOLON;
    }
    
}