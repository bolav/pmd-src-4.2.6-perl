package net.sourceforge.pmd.perl;

/**
 * Describes the input token stream.
 */

public abstract class Token {

  public int kind;
  private String image;
  public int beginLine    = 0;
  public int beginColumn  = 0;
  public int endLine      = 0;
  public int endColumn    = 0;
  
  public Token () {
      
  }
  
  public Token (Token t) {
      beginLine   = t.beginLine;
      beginColumn = t.beginColumn;
      endLine     = t.endLine;
      endColumn   = t.endColumn;      
  }
  
  public void setVars (PerlParserTokenManager t) {
      if (beginLine == 0) {
          beginLine = t.getLineNumber();
      }
      endLine = t.getLineNumber();
      if (beginColumn == 0) {
          beginColumn = t.getLineCursor();
      }
      endColumn = t.getLineCursor();
  }
  
  public Token (String s, Token t) {
      image = s;
      beginLine   = t.beginLine;
      beginColumn = t.beginColumn;
      endLine     = t.endLine;
      endColumn   = t.endColumn;
  }
  
  public String getImage () {
      return image;
  }
  
  public void setImage (String s) {
      image = s;
  }
  
  public boolean onLineStart (PerlParserTokenManager t) {
      return false;
  }
  public boolean onLineChar (PerlParserTokenManager t) {
      return false;
  }
  
}