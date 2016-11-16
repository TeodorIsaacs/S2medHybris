package S2medHybris;

import java.util.ArrayList;

enum InstructionType{
    Ecomplete, Dcomplete, Ccomplete, InnerList
}
public class CompleteInstruction {
    private InstructionType instructionType;
    private String exactType;
    private Token data;
    private ArrayList<CompleteInstruction> inner;
    private int repeats;
    private boolean penGoDown;

    public CompleteInstruction(InstructionType type, String exactType){
        instructionType = type;
        this.exactType = exactType;
    }

    public CompleteInstruction(InstructionType type, Token data, String exactType){
        instructionType = type;
        this.data = data;
        this.exactType = exactType;
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

    public int printableInfo(){
        return data.getIntData();
    }

    public String getExactType() {
        return exactType;
    }

    public int getRepeats() {
        return repeats;
    }

    public ArrayList<CompleteInstruction> getInner() {
        return inner;
    }
}
