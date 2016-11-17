package S2medHybris;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> Indata;
    private int Index = -1;
    private boolean doingInstruction;

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
                if (Next().getType() != TokenType.Space) { syntaxFel(); }
                spacePeek();
                if (Next().getType() != TokenType.Data) { syntaxFel(); }
                int potrepat = Peek(0).getIntData();
                if (Next().getType() != TokenType.Space) { syntaxFel(); }
                spacePeek();
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
                } else if (Peek(1).getType() == TokenType.Repeat) {
                    CompleteInstruction inner = new CompleteInstruction(Peek(-1).getIntData(), parse());
                    out.add(inner);



                } else {
                    //Vanliga fallet med "" kommer här
                    if (Next().getType() != TokenType.Cit) {
                        syntaxFel();
                    }
                    CompleteInstruction inner = new CompleteInstruction(Peek(-2).getIntData(), parse());
                    out.add(inner);
                    if (Peek(0).getType() != TokenType.Cit) {
                        syntaxFel();
                    }
                }
            } else if (t.getType() == TokenType.Cit) {
                return out;

            } else if (t.getType() == TokenType.Space) {
            } else {
                syntaxFel();
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

    private void syntaxFel() {
        System.out.println("Syntaxfel på rad " + This().getLine());
        System.exit(0);
    }

    private void spacePeek() throws InterruptedException {
        int i = 0;
        while (Peek(1).getType() == TokenType.Space && Index + i + 1 < Indata.size()) {
            Next();
            i++;
        }
        if (Index + i + 2 >= Indata.size()) {
            //syntaxFel();
        }
    }

    private CompleteInstruction Ehelp() throws Exception {
        spacePeek();
        if (Next().getType() != TokenType.Dot) {
            syntaxFel();
        }
        return (new CompleteInstruction(InstructionType.Ecomplete, Peek(-1).getExactType()));
    }

    private CompleteInstruction Dhelp() throws Exception {
        if (Next().getType() != TokenType.Space) {
            syntaxFel();
        }
        spacePeek();
        if (Next().getType() != TokenType.Data) {
            syntaxFel();
        }
        spacePeek();
        if (Next().getType() != TokenType.Dot) {
            syntaxFel();
        }
        return (new CompleteInstruction(InstructionType.Dcomplete, Peek(-1), Peek(-3).getExactType()));
    }

    private CompleteInstruction Chelp() throws Exception {
        if (Next().getType() != TokenType.Space) {
            syntaxFel();
        }
        spacePeek();
        if (Next().getType() != TokenType.Hex) {
            syntaxFel();
        }
        spacePeek();
        if (Next().getType() != TokenType.Dot) {
            syntaxFel();
        }
        return (new CompleteInstruction(InstructionType.Ccomplete, Peek(-1), Peek(-3).getExactType()));

    }
}
