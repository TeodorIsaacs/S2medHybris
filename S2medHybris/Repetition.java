package S2medHybris;

import java.util.ArrayList;

/**
 * Created by Teodor Isaacs on 16-11-14.
 */
public class Repetition extends Instruction {
    private int noReps;
    private ArrayList<Instruction> repInstructions;
    public static boolean isThisInstruction(String tryString) {
        if (tryString.matches("^#\\d{6}$"))
            return true;
        return false;
    }

    @Override
    public String printableInfo() {
        return null;
    }
}
