package S2medHybris;

/**
 * Created by martin on 2016-11-14.
 */
public class Right extends Instruction {
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
