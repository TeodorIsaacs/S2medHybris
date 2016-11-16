package S2medHybris;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by martin on 2016-11-15.
 */
public class Executor {

    Pen pen;

    public Executor() {
        pen = new Pen();
    }

    public void execute(ArrayList<CompleteInstruction> list) {
        for (CompleteInstruction inst : list) {
            if (inst.getInstructionType() == (InstructionType.Dcomplete)) {
                if (inst.getExactType().matches("FORW")) {
                    DecimalFormat df = new DecimalFormat("#.####");
                    String oldpos = df.format(pen.getxPos()) + " " + df.format(pen.getyPos());
                    pen.nextPos(inst.getData().getIntData());
                    System.out.println(pen.color + " " + oldpos + " " + df.format(pen.getxPos())  + " " + df.format(pen.getyPos()));

                }else if (inst.getExactType().matches("BACK")) {
                    DecimalFormat df = new DecimalFormat("#.####");
                    String oldpos = df.format(pen.getxPos()) + " " + df.format(pen.getyPos());
                    pen.nextPos(-inst.getData().getIntData());
                    System.out.println(pen.color + " " + oldpos + " " + df.format(pen.getxPos())  + " " + df.format(pen.getyPos()));

                }else if (inst.getExactType().matches("LEFT")) {
                    pen.calcNewAngle(inst.getData().getIntData());

                }else if (inst.getExactType().matches("RIGHT")) {
                    pen.calcNewAngle(-inst.getData().getIntData());

                }else{
                    pen.changeColor(inst.getData().getData());
                }
            } else if (inst.getInstructionType() == InstructionType.Ecomplete) {
                if (inst.getExactType().matches("UP"))
                    pen.dontDraw();
                else if (inst.getExactType().matches("DOWN"))
                    pen.draw();
            } else if (inst.getInstructionType() == InstructionType.InnerList) {
                for (int i = 0; i < inst.getRepeats(); i++) {
                    execute(inst.getInner());
                }
            }
        }
    }

    public Pen getPen(){
        return pen;
    }
}
