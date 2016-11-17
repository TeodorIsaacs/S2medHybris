package S2medHybris;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by martin on 2016-11-15.
 */
public class Lexer {
    String input;
    ArrayList<Token> tokenList = new ArrayList<Token>();
    int lineCount=1;
    int index = 0;

    public Lexer(InputStream in) throws java.io.IOException{
        input = read(in).toUpperCase();
        //System.out.println(input);
        Pattern patt = Pattern.compile("\\%.*\\n|\\d{1,5}|FORW|BACK|LEFT|RIGHT|DOWN|UP|COLOR|REP|\\.|\\\"|#[A-F\\d]{6}|[ \\t\\n]");
        Matcher m = patt.matcher(input);
        while (m.find()){
            if(m.group().matches("\\%.*\\n")){
                lineCount++;
                tokenList.add(new Token(TokenType.Space, "space", lineCount, index));
            } else if (m.group().matches("#[A-F\\d]{6}")){
                tokenList.add(new Token(TokenType.Hex, "Hex", lineCount, index, m.group()));
            } else if (m.group().matches("\\d{1,5}")){
                tokenList.add(new Token(TokenType.Data, "Data", lineCount, index, m.group()));
            } else if (m.group().matches("FORW|BACK|LEFT|RIGHT")){
                tokenList.add(new Token(TokenType.Dinstr, m.group(), lineCount, index));
            } else if (m.group().matches("DOWN|UP")){
                tokenList.add(new Token(TokenType.Einstr, m.group(), lineCount, index));
            } else if (m.group().matches("COLOR")){
                tokenList.add(new Token(TokenType.Cinstr, m.group(), lineCount, index));
            } else if (m.group().matches("REP")){
                tokenList.add(new Token(TokenType.Repeat, m.group(), lineCount, index));
            } else if (m.group().matches("\\.")){
                tokenList.add(new Token(TokenType.Dot, m.group(), lineCount, index));
            } else if (m.group().matches("\\\"")){
                tokenList.add(new Token(TokenType.Cit, "cit", lineCount, index));
            } else if (m.group().matches("[ \\t\\n]")){
                tokenList.add(new Token(TokenType.Space, "space", lineCount, index));
                if (m.group().matches("\\n"))
                    lineCount++;
            }
        }
    }

    public ArrayList<Token> getTokenList(){
        return tokenList;
    }

    public String getString() throws java.io.IOException{
        return input;
    }

    public static String read(InputStream in) throws java.io.IOException {
        InputStreamReader reader = new InputStreamReader(in);
        StringBuilder builder = new StringBuilder();
        char input[] = new char[3000];

        int checker = 0;

        //while((checker = reader.read(input)) != -1){
        checker = reader.read(input);
        builder.append(input, 0, checker);
        //}

        String retstr = builder.toString();
        return retstr;
    }

}
