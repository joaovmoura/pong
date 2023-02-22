import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable, KeyListener {

    public static int WIDTH = 480, HEIGHT = 320;
    private Player player;
    private Adversary adversary;

    private Ball ball;
    public Game() {
        this.addKeyListener(this); // Adicionamos eventos de teclado
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT+30));
        this.player = new Player();
        this.adversary = new Adversary();
        Random r = new Random();
        this.ball = new Ball(r.nextInt(15, WIDTH-15), r.nextInt(HEIGHT));

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
        player.tick();
        adversary.tick();
        ball.tick();
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3); // Esse número está relacionado à otimização gráfica
            return;
        }
        Graphics g = getBufferStrategy().getDrawGraphics();


        //Desenhando fundo da tela
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);


        //Pontuação dos players
        g.setColor(Color.blue);
        g.fillRect(0, HEIGHT, WIDTH, 30);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.setColor(Color.white);
        g.drawString("P1:", 5, HEIGHT+20);
        g.drawString(player.points.toString(), 35, HEIGHT+20);
        g.drawString(adversary.points.toString(), (WIDTH) - 20, HEIGHT+20);
        g.drawString("P2:", (WIDTH) - 50, HEIGHT+20);

        //Desenhando linha no centro
        g.setColor(Color.white);
        g.drawLine(WIDTH/2, 0, WIDTH/2, HEIGHT+30);

        player.render(g);
        adversary.render(g);
        ball.render(g);
        bs.show();

    }

    @Override
    public void run() {
            while(true) {
                tick();
                render();
                try {
                    Thread.sleep(1000 / 60);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP)
            this.player.up = true;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            this.player.down = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP)
            this.player.up = false;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            this.player.down = false;
    }
}