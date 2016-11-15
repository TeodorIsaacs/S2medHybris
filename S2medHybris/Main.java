package S2medHybris;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by martin on 2016-11-15.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Lexer lex = new Lexer(System.in);
        String str = lex.getString();
        ArrayList<Token> tokenList = lex.getTokenList();
        System.out.println(tokenList);
        for (Token token: tokenList) {
            System.out.println(token.getExactType());

        }
    }
}
