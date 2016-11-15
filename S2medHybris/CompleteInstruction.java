package S2medHybris;

import java.util.ArrayList;

enum InstructionType{
    Ecomplete, Dcomplete, Ccomplete, InnerList
}
public class CompleteInstruction {
    private InstructionType instructionType;
    private Token data;
    private ArrayList<CompleteInstruction> inner;
    private int repeats;

    public CompleteInstruction(InstructionType type){
        instructionType = type;
    }
    public CompleteInstruction(InstructionType type, Token data){
        instructionType = type;
        this.data = data;
    }
    public CompleteInstruction(int repeats, ArrayList<CompleteInstruction> list){
        instructionType = InstructionType.InnerList;
        this.repeats = repeats;
        this.inner = list;
    }

    public Token getData() {
        return data;
    }

    public InstructionType getInstructionType() {
        return instructionType;
    }
}
