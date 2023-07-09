import java.awt.*;

public class Ball extends Rectangle {

    private int spd = 0;
    private int yDirection = 0, xDirection = 1;
    public Ball(int x, int y) {
        super(x, y, 10,10);
    }


    public void tick(){
        x+=(spd * xDirection);
        y+=(spd * yDirection);
        if(this.intersects(new Rectangle(10, 70))) {
            System.out.println("Bateu");
            xDirection *= -1;
        }
    }
    public void render (Graphics g){
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }
}
