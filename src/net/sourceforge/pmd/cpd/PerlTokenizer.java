/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.cpd;

import net.sourceforge.pmd.perl.PerlParserTokenManager;
import net.sourceforge.pmd.perl.PerlParserConstants;
import net.sourceforge.pmd.perl.Token;

import java.io.StringReader;
import java.util.Properties;

public class PerlTokenizer implements Tokenizer {

    public static final String IGNORE_LITERALS = "ignore_literals";
    public static final String IGNORE_IDENTIFIERS = "ignore_identifiers";

    private boolean ignoreLiterals;
    private boolean ignoreIdentifiers;

    public void setProperties(Properties properties) {
        ignoreLiterals = Boolean.parseBoolean(properties.getProperty(IGNORE_LITERALS, "false"));
        ignoreIdentifiers = Boolean.parseBoolean(properties.getProperty(IGNORE_IDENTIFIERS, "false"));
    }

    public void tokenize(SourceCode tokens, Tokens tokenEntries) {
        /*
        I'm doing a sort of State pattern thing here where
        this goes into "discarding" mode when it hits an import or package
        keyword and goes back into "accumulate mode" when it hits a semicolon.
        This could probably be turned into some objects.
        */
        PerlParserTokenManager tokenMgr = new PerlParserTokenManager(tokens.getCode());
        Token currentToken = tokenMgr.getNextToken();
        boolean inDiscardingState = false;
        while (currentToken.getImage().length() > 0) {
            if (currentToken.kind == PerlParserConstants.USE || currentToken.kind == PerlParserConstants.PACKAGE) {
                inDiscardingState = true;
                currentToken = tokenMgr.getNextToken();
                continue;
            }

            if (inDiscardingState && currentToken.kind == PerlParserConstants.SEMICOLON) {
                inDiscardingState = false;
            }

            if (inDiscardingState) {
                currentToken = tokenMgr.getNextToken();
                continue;
            }

            if (currentToken.kind != PerlParserConstants.SEMICOLON) {
                String image = currentToken.getImage();
                if (ignoreLiterals && (currentToken.kind == PerlParserConstants.STRING_LITERAL 
                        || currentToken.kind == PerlParserConstants.DECIMAL_LITERAL || currentToken.kind == PerlParserConstants.FLOATING_POINT_LITERAL)) {
                    image = String.valueOf(currentToken.kind);
                }
                if (ignoreIdentifiers && currentToken.kind == PerlParserConstants.IDENTIFIER) {
                    image = String.valueOf(currentToken.kind);
                }
                tokenEntries.add(new TokenEntry(image, tokens.getFileName(), currentToken.beginLine));
            }

            currentToken = tokenMgr.getNextToken();
        }
        tokenEntries.add(TokenEntry.getEOF());
    }

    public void setIgnoreLiterals(boolean ignore) {
        this.ignoreLiterals = ignore;
    }

    public void setIgnoreIdentifiers(boolean ignore) {
        this.ignoreIdentifiers = ignore;
    }
}
