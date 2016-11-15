package S2medHybris;

/**
 * Created by Teodor Isaacs on 16-11-14.
 */
public class Left extends Instruction {
    private int degree;
    public static boolean isThisInstruction(String tryString) {
        if (tryString.matches("^#\\d{6}$"))
            return true;
        return false;
    }

    @Override
    public String printableInfo() {
        return String.valueOf(degree);
    }
}
