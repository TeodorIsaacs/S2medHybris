package S2medHybris;

/**
 * Created by martin on 2016-11-15.
 */
public class Pen {
    double xPos, yPos;
    double angle;
    boolean drawing;
    String color;

    public Pen(){
        xPos = 0;
        yPos = 0;
        angle = 0;
        drawing = false;
        color = "#0000FF";
    }

    public String getColor(){
        return color;
    }

    public double getxPos(){
        return xPos;
    }

    public double getyPos(){
        return yPos;
    }

    public double getAngle(){
        return angle;
    }

    public boolean getDrawing(){
        return drawing;
    }

    public void changeDrawing(){
        drawing = !drawing;
    }

    public void draw() { drawing = true; }

    public void dontDraw() { drawing = false; }

    public void changeColor(String color){
        this.color = color;
    }

    public void calcNewXPos(int dist){ xPos = xPos + dist*Math.cos((Math.PI*angle)/180); }

    public void calcNewYPos(int dist){
        yPos = yPos + dist*Math.sin((Math.PI*angle)/180);
    }

    public void nextPos(int dist){
        calcNewXPos(dist);
        calcNewYPos(dist);
    }

    public void calcNewAngle(int ang) {
        angle = angle + ang;
    }
}
