import java.awt.*;

public class Player extends Rectangle {

    public boolean up, down;
    private final int spd = 4;

    public Integer points = 0;
    public Player(){
        super(0, 0, 10, 70);
        up = false;
        down = false;
    }

    public void tick(){
        if(up && y>0)
            y -= spd;
        else if(down && (y+70) < 320)
            y += spd;
    }
    public void render (Graphics g){
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
    }
    // TODO: Pontuação aumenta conforme as rodadas
    public void point(){
        this.points=1;
    }
}
