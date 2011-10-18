package net.sourceforge.pmd.cpd.perltoken;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import net.sourceforge.pmd.cpd.PerlTokenizer;

public class Whitespace extends Token {
    
    Pattern whitespace = Pattern.compile("^\\s*$");
    Pattern comment    = Pattern.compile("\\s*#.*");
    Pattern pod        = Pattern.compile("^=(\\w+).*");
    public boolean onLineStart (PerlTokenizer t) {
        String line = t.getCurrentLine();
        Matcher m;
        m = whitespace.matcher(line);
        if (m.matches()) {
            t.newToken("Whitespace", line);
            return true;
        }
        m = comment.matcher(line);
        if (m.matches()) {
            t.newToken("Comment", line);
            return true;
        }
        m = pod.matcher(line);
        if (m.matches()) {
            if (m.group(1).equals("cut")) {
                t.newToken("Pod", line);
                return true;
            }
            else {
                t.setToken(new Pod(line));
                return true;                
            }
        }
        System.out.println("Nothing for "+line);
        return false;
    }
    
    public boolean onLineChar (PerlTokenizer t) {
        char c = t.getCurrentChar();
        
        return false;
    }
}