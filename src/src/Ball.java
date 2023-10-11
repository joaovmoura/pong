import java.awt.*;

public class Ball extends Rectangle {

//    TODO: Velocidade aumenta com o passar do jogo
    private int spd = 0;
    private int yDirection = 1, xDirection = 1;
    public Ball(int x, int y) {
        super(x, y, 10,10);
    }


    // TODO: Melhorar encapsulamento de player e player2
    public void tick(){
        x+=(spd * xDirection);
        y+=(spd * yDirection);
        if(this.intersects(Game.player1)|| this.intersects(Game.player2)) {
            xDirection *= -1;
        }
        if(this.intersectsLine(0, 320, 480, 320) ||
           this.intersectsLine(0, 0, 480, 0)) {
            yDirection *= -1;
        }
        if(this.intersectsLine(0, 0, 0, 320)) {
            Game.player2.point();
        }

        if(this.intersectsLine(480, 0, 480, 320)) {
            Game.player1.point();
        }
    }
    public void render (Graphics g){
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
    }

    public void setSpd(int spd) {
        this.spd = spd;
    }

    public void setxDirection(int xDirection) {
        this.xDirection = xDirection;
    }
}
