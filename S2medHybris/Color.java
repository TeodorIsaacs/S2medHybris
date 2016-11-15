package S2medHybris;

/**
 * Created by Teodor Isaacs on 16-11-14.
 */
public class Color extends Instruction{

    public String color;

    public Color(String color){
        this.color = color;
    }

    public static Color isThisInstruction(String tryString) {
        if (tryString.matches("^#[\\dABCDEF]{6}$"))
            return new Color(tryString);
        return null;
    }

    @Override
    public String printableInfo() {
        return color;
    }
}