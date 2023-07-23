import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable, KeyListener {

    //TODO: Jogo acaba quando um dos players fazem 10 pontos
    public static int WIDTH = 480, HEIGHT = 320;
    public static Player player; // alterado para público e estático

    //TODO: Tirar a classe adversary e transformar o objeto em "player2" para melhorar o reuso de código
    public static Adversary adversary; // alterado para público e estático
    private Boolean isPaused;

    public static Boolean pointed = false;
    private Ball ball;
    public Game() {
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT+30));
        player = new Player(); // removido "this."
        adversary = new Adversary(); // removido "this."
        this.isPaused = true;
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
        if(pointed)
            restart();
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


        renderScenario(g);

        player.render(g);
        adversary.render(g);
        ball.render(g);
        bs.show();

    }

    private void renderScenario(Graphics g) {
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

//  TODO: Parar movimentação dos players quando o jogo estiver pausado
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
        this.ball = new Ball(r.nextInt(15, WIDTH-15), r.nextInt(HEIGHT));
        pointed = false;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP)
            this.player.up = true;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            this.player.down = true;

        if(e.getKeyCode() == KeyEvent.VK_W)
            this.adversary.up = true;
        else if (e.getKeyCode() == KeyEvent.VK_S)
            this.adversary.down = true;

        if(e.getKeyCode() == KeyEvent.VK_SPACE && isPaused)
            unPause();
        else if(e.getKeyCode() == KeyEvent.VK_SPACE && !isPaused)
            pause();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP)
            this.player.up = false;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            this.player.down = false;

        if(e.getKeyCode() == KeyEvent.VK_W)
            this.adversary.up = false;
        else if (e.getKeyCode() == KeyEvent.VK_S)
            this.adversary.down = false;
    }
}