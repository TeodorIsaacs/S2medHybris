package S2medHybris;

import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> Indata;
    private int Index = -1;
    private int level = 0;
    private boolean[] repLvs = new boolean[20];
    private boolean innerNoCit = false;
    public int errorRow = -1;

    public Parser(ArrayList<Token> Indata, int errRowLexer) {
        this.Indata = Indata;
        errorRow = errRowLexer;
    }

    public ArrayList<CompleteInstruction> parse() throws Exception {
        // Kika på nästa indata-token för att välja produktionsregel
        ArrayList<CompleteInstruction> out = new ArrayList<>();
        while (Index < Indata.size() - 1) {
            Token t = Next();
            if (t.getType() == TokenType.Einstr) {
                out.add(Ehelp());
                if (returnhelper())
                    return out;

            } else if (t.getType() == TokenType.Dinstr) {
                out.add(Dhelp());
                if (returnhelper())
                    return out;

            } else if (t.getType() == TokenType.Cinstr) {
                out.add(Chelp());
                if (returnhelper())
                    return out;

            } else if (t.getType() == TokenType.Repeat) {
                if (Next().getType() != TokenType.Space) {
                    syntaxFel();
                }
                spacePeek();
                if (Next().getType() != TokenType.Data) {
                    syntaxFel();
                }
                int potrepat = Peek(0).getIntData();
                if (Next().getType() != TokenType.Space) {
                    syntaxFel();
                }
                int skipped = spacePeek();
                //Fallet utan paranteser
                ArrayList<CompleteInstruction> helper = new ArrayList<>();

                if (Peek(1).getType() == (TokenType.Dinstr)) {
                    Next();
                    helper.add(Dhelp());
                    out.add(new CompleteInstruction(potrepat, helper));
                    if (returnhelper())
                        return out;
                } else if (Peek(1).getType() == TokenType.Cinstr) {
                    Next();
                    out.add(Chelp());
                    if (returnhelper())
                        return out;
                } else if (Peek(1).getType() == TokenType.Einstr) {
                    Next();
                    helper.add(Ehelp());
                    out.add(new CompleteInstruction(potrepat, helper));
                    if (returnhelper())
                        return out;
                } else if (Peek(1).getType() == TokenType.Repeat) {
                    repLvs[level + 1] = true;
                    level++;
                    CompleteInstruction inner = new CompleteInstruction(Peek(-skipped).getIntData(), parse());
                    out.add(inner);
                    if (returnhelper())
                        return out;

                } else {
                    //Vanliga fallet med "" kommer här
                    if (Next().getType() != TokenType.Cit) {
                        syntaxFel();
                    }
                    repLvs[level + 1] = false;
                    level++;
                    CompleteInstruction inner = new CompleteInstruction(Peek(-skipped - 1).getIntData(), parse());
                    out.add(inner);
                    if (Peek(0).getType() != TokenType.Cit) {
                        syntaxFel();
                    }
                    if (returnhelper())
                        return out;
                }
            } else if (t.getType() == TokenType.Cit) {
                if (level==0)
                    syntaxFel();
                level--;
                return out;

            } else if (t.getType() == TokenType.Space) {
                /* do nothing */
            } else {
                syntaxFel();
            }
        }
        return out;
    }

    private boolean returnhelper() {
        boolean returning = false;
        if (level >= 1 && repLvs[level]) {
            returning = true;
            level--;
        }
        return returning;
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
        if (This().getLine() < errorRow || errorRow == -1) {
            System.out.println("Syntaxfel på rad " + This().getLine());
        } else System.out.println("Syntaxfel på rad " + errorRow);
        System.exit(0);
    }

    private int spacePeek() throws InterruptedException {
        int i = 0;
        while (Index < Indata.size() - 1 && Peek(1).getType() == TokenType.Space) {
            Next();
            i++;
        }
        if (Index + 1 >= Indata.size()) {
            int errorline = Indata.get(Index - i).getLine();
            syntaxFelSpecial(errorline);
        }
        return i + 1;
    }

    private void syntaxFelSpecial(int row) {
        if (row < errorRow || errorRow == -1) {
            System.out.println("Syntaxfel på rad " + row);
        } else System.out.println("Syntaxfel på rad " + errorRow);
        System.exit(0);
    }

    private CompleteInstruction Ehelp() throws Exception {
        int skipped = spacePeek();
        if (Next().getType() != TokenType.Dot) {
            syntaxFel();
        }
        return (new CompleteInstruction(InstructionType.Ecomplete, Peek(-skipped).getExactType()));
    }

    private CompleteInstruction Dhelp() throws Exception {
        if (Next().getType() != TokenType.Space) {
            syntaxFel();
        }
        int skip1 = spacePeek();
        if (Next().getType() != TokenType.Data) {
            syntaxFel();
        }
        int skip2 = spacePeek();
        if (Next().getType() != TokenType.Dot) {
            syntaxFel();
        }
        return (new CompleteInstruction(InstructionType.Dcomplete, Peek(-skip2), Peek(-skip1 - skip2 - 1).getExactType()));
    }

    private CompleteInstruction Chelp() throws Exception {
        if (Next().getType() != TokenType.Space) {
            syntaxFel();
        }
        int skip1 = spacePeek();
        if (Next().getType() != TokenType.Hex) {
            syntaxFel();
        }
        int skip2 = spacePeek();
        if (Next().getType() != TokenType.Dot) {
            syntaxFel();
        }
        return (new CompleteInstruction(InstructionType.Ccomplete, Peek(-skip2), Peek(-skip1 - skip2 - 1).getExactType()));

    }
}
