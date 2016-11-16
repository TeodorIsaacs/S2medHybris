package S2medHybris;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> Indata;
    private int Index = -1;

    public Parser(ArrayList<Token> Indata) {
        this.Indata = Indata;
    }

    public ArrayList<CompleteInstruction> parse() throws Exception {
        // Kika på nästa indata-token för att välja produktionsregel
        ArrayList<CompleteInstruction> out = new ArrayList<>();
        while (Index < Indata.size() - 1) {
            Token t = Next();
            if (t.getType() == TokenType.Einstr) {
                while (Peek(1).getType() == TokenType.Space){Next();}
                if (Next().getType() != TokenType.Dot)
                    throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                out.add(new CompleteInstruction(InstructionType.Ecomplete, Peek(-1).getExactType()));

            } else if (t.getType() == TokenType.Dinstr) {
                if (Next().getType() != TokenType.Space) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                while (Peek(1).getType() == TokenType.Space){Next();}
                if (Next().getType() != TokenType.Data) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                while (Peek(1).getType() == TokenType.Space){Next();}
                if (Next().getType() != TokenType.Dot) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                out.add(new CompleteInstruction(InstructionType.Dcomplete, Peek(-1), Peek(-3).getExactType()));

            } else if (t.getType() == TokenType.Cinstr) {
                if (Next().getType() != TokenType.Space) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                while (Peek(1).getType() == TokenType.Space){Next();}
                if (Next().getType() != TokenType.Hex) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                while (Peek(1).getType() == TokenType.Space){Next();}
                if (Next().getType() != TokenType.Dot) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                out.add(new CompleteInstruction(InstructionType.Ccomplete, Peek(-1), Peek(-3).getExactType()));

            } else if (t.getType() == TokenType.Repeat) {
                if (Next().getType() != TokenType.Space) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                while (Peek(1).getType() == TokenType.Space){Next();}
                if (Next().getType() != TokenType.Data) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                if (Next().getType() != TokenType.Space) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                while (Peek(1).getType() == TokenType.Space){Next();}
                if (Next().getType() != TokenType.Cit) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                CompleteInstruction inner = new CompleteInstruction(Peek(-2).getIntData(), parse());
                int i = -1;
                out.add(inner);
                while (Peek(i).getType() == TokenType.Space){
                    i--;
                }
                if (Peek(i).getType() != TokenType.Cit) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
            } else if(t.getType() == TokenType.Cit){
                Next();
                return out;

            }else if(t.getType() == TokenType.Space){
            } else {
                throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
            }
        }
        return out;
    }

    private Token Peek(int amount) {
        return Indata.get(Index + amount);
    }

    private Token Next() {
        Index++;
        return Indata.get(Index);
    }

    private Token This() {
        return Indata.get(Index);
    }
}
