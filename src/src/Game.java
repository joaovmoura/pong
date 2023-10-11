import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable, KeyListener {

    //TODO: Jogo acaba quando um dos player1s fazem 10 pontos
    public static int WIDTH = 480, HEIGHT = 320;
    public static Player player1; // alterado para público e estático

    //TODO: Tirar a classe player2 e transformar o objeto em "player12" para melhorar o reuso de código
    public static Player player2; // alterado para público e estático
    private Boolean isPaused;

    public static Player lastToPoint = null;
    public static Boolean pointed = false;
    private Ball ball;
    public Game() {
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT+30));
        player1 = new Player(0, 0); // removido "this."
        player2 = new Player(WIDTH-10, 0); // removido "this."
        this.isPaused = true;
        Random r = new Random();
        this.ball = new Ball(235, r.nextInt(HEIGHT));
    }
    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame();

        frame.add(game);
        frame.setTitle("Pong");

        frame.pack(); // Empacotar o que foi feito antes e calcular o tamanho da janela

        frame.setLocationRelativeTo(null); // Janela centralizada
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Quando fechar a janela, o processo será encerrado

        frame.setVisible(true);
        game.start();
    }
    public void start(){
        Thread t = new Thread(this);
        t.start();
    }

    private void tick() {
        if(pointed)
            restart();
        player1.tick();
        player2.tick();
        ball.tick();
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3); // Esse número está relacionado à otimização gráfica
            return;
        }
        Graphics g = getBufferStrategy().getDrawGraphics();


        renderScenario(g);

        player1.render(g);
        player2.render(g);
        ball.render(g);
        bs.show();

    }

    private void renderScenario(Graphics g) {
        //Desenhando fundo da tela
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);


        //Pontuação dos player1s
        g.setColor(Color.blue);
        g.fillRect(0, HEIGHT, WIDTH, 30);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.setColor(Color.white);
        g.drawString("P1:", 5, HEIGHT+20);
        g.drawString(player1.points.toString(), 35, HEIGHT+20);
        g.drawString(player2.points.toString(), (WIDTH) - 20, HEIGHT+20);
        g.drawString("P2:", (WIDTH) - 50, HEIGHT+20);

        //Desenhando linha no centro
        g.setColor(Color.white);
        g.drawLine(WIDTH/2, 0, WIDTH/2, HEIGHT+30);
    }

    @Override
    public void run() {
            while(true) {
                tick();
                render();
                try {
                    Thread.sleep(1000 / 75);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

//  TODO: Parar movimentação dos player1s quando o jogo estiver pausado
    private void pause() {
        this.ball.setSpd(0);
        this.isPaused = true;
    }
    private void unPause() {
        this.ball.setSpd(2);
        this.isPaused = false;
    }

    public void restart(){

        this.isPaused = true;
        Random r = new Random();
        if(lastToPoint.equals(player1)){
            this.ball = new Ball(WIDTH-21, (int) player2.getY() + 30);
            ball.setxDirection(1);
        }
        if(lastToPoint.equals(player2)){
            this.ball = new Ball(11, (int) player1.getY() + 30);
            ball.setxDirection(-1);
        }

        pointed = false;

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP)
            this.player1.up = true;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            this.player1.down = true;

        if(e.getKeyCode() == KeyEvent.VK_W)
            this.player2.up = true;
        else if (e.getKeyCode() == KeyEvent.VK_S)
            this.player2.down = true;

        if(e.getKeyCode() == KeyEvent.VK_SPACE && isPaused)
            unPause();
        else if(e.getKeyCode() == KeyEvent.VK_SPACE && !isPaused)
            pause();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP)
            this.player1.up = false;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            this.player1.down = false;

        if(e.getKeyCode() == KeyEvent.VK_W)
            this.player2.up = false;
        else if (e.getKeyCode() == KeyEvent.VK_S)
            this.player2.down = false;
    }
}