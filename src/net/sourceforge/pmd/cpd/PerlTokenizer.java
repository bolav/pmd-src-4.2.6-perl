/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */
package net.sourceforge.pmd.cpd;

import java.util.ArrayList;
import java.util.List;
import net.sourceforge.pmd.cpd.perltoken.*;

public class PerlTokenizer extends AbstractTokenizer {

    private int lineNumber = 0;
    private int lineCursor = -1;
    private int lineLength = 0;
	private String currentLine;
	
	private int tokenCursor = 0;
	protected List<String> tokens = new ArrayList<String>();
	private List<String> code;
	private boolean downcaseString = true;
	
	private Token currentToken = new Whitespace();
	
	public String getCurrentLine () {
	    return currentLine;
	}
	
	public char getCurrentChar () {
	    return currentLine.substring(lineCursor, lineCursor + 1).toCharArray()[0];
	}
	
	private boolean processNextChar () {
	    if (lineCursor < lineLength) {
	        lineCursor++;
	        currentToken.onLineChar(this);
	        return true;
	    }
	    return false;
	}
	
	public void setToken (Token t) {
	    this.currentToken = t;
	}

	private boolean processNextLine () {
	    if (lineNumber >= this.code.size()) {
	        return false;
	    }
	    this.currentLine = this.code.get(this.lineNumber);
	    this.lineNumber++;
	    this.lineCursor = -1;
	    this.lineLength = this.currentLine.length();
	    System.out.println(this.currentLine);
	    if (!currentToken.onLineStart(this)) {
	        while (processNextChar()) {}
	        return true;
	    }
	    return true;
	}
	
	public void newToken (String type, String str) {
	    System.out.println("newToken " + type + "("+ str +")");
	    if (type.equals("Whitespace")) {
	    }
	    else if (type.equals("Comment")) {
	    }
	    else if (type.equals("Pod")) {
	    }
	    else {
	        tokens.add(str);
	    }
	    if (!(currentToken instanceof Whitespace)) {
	        currentToken = new Whitespace();
	    }
	}
	
	private String getToken () {
	    while (tokenCursor + 1 > tokens.size()) {
    	    if (!processNextLine()) {
    	        return null;
    	    }
	    }

	    return tokens.get(tokenCursor++);
	}
	
    public void tokenize(SourceCode tokens, Tokens tokenEntries) {
        this.code = tokens.getCode();
        
        String token;
        while ((token = getToken()) != null) {
            tokenEntries.add(new TokenEntry(token, tokens.getFileName(), lineNumber));
        }
        tokenEntries.add(TokenEntry.getEOF());
    }

}
