package net.sourceforge.pmd.cpd.perltoken;

import net.sourceforge.pmd.cpd.PerlTokenizer;

public abstract class Token {
    public boolean onLineStart (PerlTokenizer t) {
        return false;
    }
    public boolean onLineChar (PerlTokenizer t) {
        return false;
    }
}