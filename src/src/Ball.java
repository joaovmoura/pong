import java.awt.*;

public class Ball extends Rectangle {

    private final int spd = 4;
    public Ball(int x, int y) {
        super(x, y, 10,10);
    }


    public void tick(){
    }
    public void render (Graphics g){
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
    }
}
