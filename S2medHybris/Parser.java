package S2medHybris;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> Indata;
    private int Index = 0;

    public Parser(ArrayList<Token> Indata) {
        this.Indata = Indata;
    }

    private ArrayList<CompleteInstruction> Parse() throws Exception {
        // Kika på nästa indata-token för att välja produktionsregel
        ArrayList<CompleteInstruction> out = new ArrayList<>();
        while (Index < Indata.size()) {
            Token t = Next();
            if (t.getType() == TokenType.Einstr) {
                if (Next().getType() != TokenType.Dot)
                    throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                out.add(new CompleteInstruction(InstructionType.Ecomplete));

            } else if (t.getType() == TokenType.Dinstr) {
                if (Next().getType() != TokenType.Space) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                if (Next().getType() != TokenType.Data) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                if (Next().getType() != TokenType.Dot) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                out.add(new CompleteInstruction(InstructionType.Dcomplete, Peek(-1)));

            } else if (t.getType() == TokenType.Cinstr) {
                if (Next().getType() != TokenType.Space) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                if (Next().getType() != TokenType.Hex) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                if (Next().getType() != TokenType.Dot) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                out.add(new CompleteInstruction(InstructionType.Ccomplete, Peek(-1)));

            } else if (t.getType() == TokenType.Repeat) {
                if (Next().getType() != TokenType.Space) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                if (Next().getType() != TokenType.Data) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                if (Next().getType() != TokenType.Space) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                if (Next().getType() != TokenType.Cit) throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                CompleteInstruction inner = new CompleteInstruction(Peek(-2).getIntData(), Parse());
                out.add(inner);
            } else if(t.getType() == TokenType.Cit){
                Next();
                return out;

            }else if(t.getType() == TokenType.Space){
                Next();
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
