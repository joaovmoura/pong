import java.awt.*;

public class Ball extends Rectangle {

    private int spd = 0;
    private int direction = 1;
    public Ball(int x, int y) {
        super(x, y, 10,10);
    }


    public void tick(){
        x+=(spd * direction);
        y+=(spd * direction);
        if(this.intersects(new Rectangle(10, 70)))
            direction*=-1;

    }
    public void render (Graphics g){
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }
}
