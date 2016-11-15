package S2medHybris;
/**
 * Created by martin on 2016-11-14.
 */
public class Back extends Instruction{
    private int dist;

    public static boolean isThisInstruction(String tryString) {
        if (tryString.matches("^#\\d{6}$"))
            return true;
        return false;
    }

    @Override
    public String printableInfo() {
        return String.valueOf(dist);
    }
}
