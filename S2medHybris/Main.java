package S2medHybris;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by martin on 2016-11-15.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        //LEXA
        Lexer lex = new Lexer(System.in);
        String str = lex.getString();
        ArrayList<Token> tokenList = lex.getTokenList();
        //PARSA
        Parser parser = new Parser(tokenList, lex.errorLine);
        ArrayList<CompleteInstruction> instructions = parser.parse();
        //EXECUTE
        Executor executor = new Executor();
        executor.execute(instructions);
    }
}
