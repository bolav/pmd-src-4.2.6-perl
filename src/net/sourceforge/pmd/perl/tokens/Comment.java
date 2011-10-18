package net.sourceforge.pmd.perl.tokens;

import net.sourceforge.pmd.perl.Token;
import net.sourceforge.pmd.perl.PerlParserConstants;

public class Comment extends Token {
    
    public Comment (String s, Token t) {
        super(s, t);
        kind = PerlParserConstants.COMMENT;
    }
    
}