/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package test.net.sourceforge.pmd.cpd;

import static org.junit.Assert.assertEquals;
import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.cpd.PerlTokenizer;
import net.sourceforge.pmd.cpd.SourceCode;
import net.sourceforge.pmd.cpd.Tokenizer;
import net.sourceforge.pmd.cpd.Tokens;

import org.junit.Test;

public class PerlTokensTokenizerTest {

    @Test
    public void test1() throws Throwable {
        Tokenizer tokenizer = new PerlTokenizer();
        SourceCode sourceCode = new SourceCode(new SourceCode.StringCodeLoader("sub foo {}"));
        Tokens tokens = new Tokens();
        tokenizer.tokenize(sourceCode, tokens);
        assertEquals(5, tokens.size());
        assertEquals("sub foo {}", sourceCode.getSlice(1, 1));
    }
    
    @Test
    public void test2() throws Throwable {
        Tokenizer t = new PerlTokenizer();
        String data = "sub foo {" + PMD.EOL + "sub bar {}" + PMD.EOL + "sub buz {}" + PMD.EOL + "}";
        SourceCode sourceCode = new SourceCode(new SourceCode.StringCodeLoader(data));
        Tokens tokens = new Tokens();
        t.tokenize(sourceCode, tokens);
        assertEquals("sub foo {" + PMD.EOL + "sub bar {}", sourceCode.getSlice(1, 2));
    }
    
    @Test
    public void testDiscardSemicolons() throws Throwable {
        Tokenizer t = new PerlTokenizer();
        SourceCode sourceCode = new SourceCode(new SourceCode.StringCodeLoader("sub foo {my $x;}"));
        Tokens tokens = new Tokens();
        t.tokenize(sourceCode, tokens);
        assertEquals(7, tokens.size());
    }

    @Test
    public void testVar() throws Throwable {
        Tokenizer t = new PerlTokenizer();
        SourceCode sourceCode = new SourceCode(new SourceCode.StringCodeLoader("my $x = 0;"));
        Tokens tokens = new Tokens();
        t.tokenize(sourceCode, tokens);
        tokens.print();
        assertEquals(5, tokens.size());
    }
    
    @Test
    public void testOperator() throws Throwable {
        Tokenizer t = new PerlTokenizer();
        SourceCode sourceCode = new SourceCode(new SourceCode.StringCodeLoader("if ($x == 0) {};"));
        Tokens tokens = new Tokens();
        t.tokenize(sourceCode, tokens);
        tokens.print();
        assertEquals(8, tokens.size());
    }

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(PerlTokensTokenizerTest.class);
    }
}


