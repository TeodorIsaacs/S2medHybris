package S2medHybris;

/**
 * Created by Teodor Isaacs on 16-11-14.
 */
public class Up extends Instruction{
    public static boolean isThisInstruction(String tryString) {
        if (tryString.matches("^#\\d{6}$"))
            return true;
        return false;
    }

    @Override
    public String printableInfo() {
        return "";
    }
}
