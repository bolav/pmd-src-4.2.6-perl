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
  
  public boolean onLineStart (PerlParserTokenManager t) {
      return false;
  }
  public boolean onLineChar (PerlParserTokenManager t) {
      return false;
  }
  
}