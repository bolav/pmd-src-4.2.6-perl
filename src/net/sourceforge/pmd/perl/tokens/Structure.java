package net.sourceforge.pmd.perl.tokens;

import net.sourceforge.pmd.perl.Token;
import net.sourceforge.pmd.perl.PerlParserConstants;

public class Structure extends Token {
    
    public Structure (char c, Token t) {
        super(String.valueOf(c), t);
        kind = PerlParserConstants.STRUCTURE;
    }
    
}