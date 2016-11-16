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
                out.add(Ehelp());

            } else if (t.getType() == TokenType.Dinstr) {
                out.add(Dhelp());

            } else if (t.getType() == TokenType.Cinstr) {
                out.add(Chelp());

            } else if (t.getType() == TokenType.Repeat) {
                if (Next().getType() != TokenType.Space)
                    throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                while (Peek(1).getType() == TokenType.Space) {
                    Next();
                }
                if (Next().getType() != TokenType.Data)
                    throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                //Används för edgecaset
                int potrepat = Peek(0).getIntData();
                if (Next().getType() != TokenType.Space)
                    throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                while (Peek(1).getType() == TokenType.Space) {
                    Next();
                }
                //Fallet utan paranteser
                ArrayList<CompleteInstruction> helper = new ArrayList<>();
                if (Peek(1).getType() == (TokenType.Dinstr)) {
                    Next();
                    helper.add(Dhelp());
                    out.add(new CompleteInstruction(potrepat, helper));
                } else if (Peek(1).getType() == TokenType.Cinstr) {
                    Next();
                    out.add(Chelp());
                } else if (Peek(1).getType() == TokenType.Einstr) {
                    Next();
                    helper.add(Ehelp());
                    out.add(new CompleteInstruction(potrepat, helper));
                } else {
                    //Vanliga fallet med "" kommer här
                    if (Next().getType() != TokenType.Cit)
                        throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                    CompleteInstruction inner = new CompleteInstruction(Peek(-2).getIntData(), parse());
                    out.add(inner);
                    if (Peek(0).getType() != TokenType.Cit)
                        throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
                }
            } else if (t.getType() == TokenType.Cit) {
                return out;

            } else if (t.getType() == TokenType.Space) {
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

    private CompleteInstruction Ehelp() throws Exception {
        while (Peek(1).getType() == TokenType.Space) {
            Next();
        }
        if (Next().getType() != TokenType.Dot)
            throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
        return (new CompleteInstruction(InstructionType.Ecomplete, Peek(-1).getExactType()));
    }

    private CompleteInstruction Dhelp() throws Exception {
        if (Next().getType() != TokenType.Space)
            throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
        while (Peek(1).getType() == TokenType.Space) {
            Next();
        }
        if (Next().getType() != TokenType.Data)
            throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
        while (Peek(1).getType() == TokenType.Space) {
            Next();
        }
        if (Next().getType() != TokenType.Dot)
            throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
        return (new CompleteInstruction(InstructionType.Dcomplete, Peek(-1), Peek(-3).getExactType()));
    }

    private CompleteInstruction Chelp() throws Exception {
        if (Next().getType() != TokenType.Space)
            throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
        while (Peek(1).getType() == TokenType.Space) {
            Next();
        }
        if (Next().getType() != TokenType.Hex)
            throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
        while (Peek(1).getType() == TokenType.Space) {
            Next();
        }
        if (Next().getType() != TokenType.Dot)
            throw new Exception("Fel uppstod på rad: " + This().getLine() + " index: " + This().getIndexInLine());
        return (new CompleteInstruction(InstructionType.Ccomplete, Peek(-1), Peek(-3).getExactType()));

    }
}
