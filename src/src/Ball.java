import java.awt.*;

public class Ball extends Rectangle {

    private int spd = 0;
    private int yDirection = 1, xDirection = 1;
    public Ball(int x, int y) {
        super(x, y, 10,10);
    }


    // TODO: Melhorar encapsulamento de player e adversary
    public void tick(){
        x+=(spd * xDirection);
        y+=(spd * yDirection);
        if(this.intersects(Game.player)|| this.intersects(Game.adversary)) {
            xDirection *= -1;
        }
        if(this.intersectsLine(0, 320, 480, 320) ||
           this.intersectsLine(0, 0, 480, 0)) {
            yDirection *= -1;
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
