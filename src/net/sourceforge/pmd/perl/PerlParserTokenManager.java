package net.sourceforge.pmd.perl;

import net.sourceforge.pmd.ast.CharStream;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.pmd.perl.tokens.*;

/** Token Manager. */
public class PerlParserTokenManager implements PerlParserConstants
{
    private int lineNumber = 0;
    private int lineCursor = -1;
    private int lineLength = 0;
	private String currentLine;

    private List<String> code;
	private int tokenCursor = 0;
	protected List<Token> tokens = new ArrayList<Token>();
	
	private Token currentToken = new Whitespace();
	
    public PerlParserTokenManager (List code){
       this.code = code;
    }
    
    public String getCurrentLine () {
	    return currentLine;
	}
	
	public int getLineNumber () {
	    return lineNumber;
	}
	
	public char getCurrentChar () {
	    return currentLine.substring(lineCursor, lineCursor + 1).toCharArray()[0];
	}
	
	public int getLineCursor () {
	    return lineCursor;
	}
	
	public void incLineCursor () {
	    lineCursor++;
	}
	
	public void incLineCursor (int i) {
	    System.out.println("Adding "+i);
	    lineCursor += i;
	}
	
	
	public void addToken(Token t) {
	    System.out.println("Adding "+t);
	    tokens.add(t);
	    setToken(new Whitespace());
	}

	public void setToken (Token t) {
	    this.currentToken = t;
	}

    private boolean processNextChar () {
	    if (lineCursor + 1 < lineLength) {
	        currentToken.onLineChar(this);
	        return true;
	    }
	    return false;
	}

    private boolean processNextLine () {
        if (lineNumber >= this.code.size()) {
	        return false;
	    }
	    System.out.println("Parsing with "+currentToken);
	    this.currentLine = this.code.get(this.lineNumber);
	    this.lineNumber++;
	    this.lineCursor = -1;
	    this.lineLength = this.currentLine.length();
	    System.out.println(this.currentLine);
	    if (!currentToken.onLineStart(this)) {
	        incLineCursor();
	        while (processNextChar()) {}
	        return true;
	    }
	    return true;
    }
    
    public Token getNextToken () {
        while (tokenCursor + 1 > tokens.size()) {
    	    if (!processNextLine()) {
    	        return null;
    	    }
	    }
	    return tokens.get(tokenCursor++);
    }
}