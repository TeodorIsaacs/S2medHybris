package S2medHybris;

import java.util.ArrayList;

/**
 * Created by Teodor Isaacs on 16-11-14.
 */
public class Main {
    public static void main(String[] args) {
        Instruction inst = whichInstruction("#101010");
        System.out.println(inst.printableInfo());
    }

    private static Instruction whichInstruction(String line){
        Color mbyColor = Color.isThisInstruction(line);
        if (mbyColor != null)
            return mbyColor;
        Back mbyBack = Back.isThisInstruction(line);
        if (mbyBack != null)
            return mbyBack;
        return null;
    }
}
