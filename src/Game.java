import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

class Game extends JPanel implements KeyListener {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int FPS = 60;
    World world;

    boolean restartTracker = false;

    class Runner implements Runnable {
        public void run() {
            //Play game music
            AudioPlayer.playMusic();
            while (true) {
                //Update methods
                world.updateBalls(1.0 / (double) FPS);
                world.updatePlayer(1.0 / (double) FPS);
                world.updateShot(1.0 / (double) FPS);
                world.updateSquares(1.0/(double) FPS);
                repaint();
                //Break loop if object collides with player in order to stop objects moving and proceed to Game Over laugh track
                if(world.collideWithPlayer){
                    break;
                }
                try {
                    Thread.sleep(1000 / FPS);
                } catch (InterruptedException e) {
                }
            }
            repaint();
            AudioPlayer.playLaugh();
        }
    }

    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
    }

    public void keyReleased(KeyEvent e) {
        char c = e.getKeyChar();

    }
    //KeyTyped is where we track what letter the user is pressing in order to control the player
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        String direction= "wasd";

        //w -> up
        char up= direction.charAt(0);
        //a -> left
        char left = direction.charAt(1);
        //d -> right
        char right = direction.charAt(3);

        //Create pairs of each new velocity for direction of player and shot
        Pair changeRight= new Pair(200,0);
        Pair changeLeft= new Pair(-200,0);
        Pair changeUp= new Pair(0,-450);
        //Press d to move player right
        if(c==right){
            world.player.setVelocity(changeRight);
            //Check to see if shot is released in order to keep shot and player moving together
            if (!world.shot.isReleased) {
                world.shot.setVelocity(changeRight);
            }
        }
        //Press w to fire the shot
        if(c==up){
            world.shot.isReleased = true;
            world.shot.setVelocity(changeUp);
        }
        //press a to move player left
        if (c==left){
            world.player.setVelocity(changeLeft);
            //Check to see if shot is released in order to keep shot and player moving together
            if (!world.shot.isReleased) {
                world.shot.setVelocity(changeLeft);
            }
        }

        String restart = "g";
        //g -> restart
        char restartChar = restart.charAt(0);
        if (c == restartChar){
            restartTracker = true;
            System.out.println(restartTracker);
            //System.out.println("test");
        }
        //System.out.println("You typed: " + c);

    }
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    //Game Constructor
    public Game() {
        world = new World(WIDTH, HEIGHT, 1, 1);
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Thread mainThread = new Thread(new Runner());
        mainThread.start();
    }

    //public Game(Game g){
    //    g.world = new World(WIDTH, HEIGHT, 1, 1);
    //    g.world.collideWithPlayer = false;
    //    addKeyListener(this);
    //    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    //    Thread mainThread = new Thread(new Runner());
    //    mainThread.start();
    //}

    public static void main(String[] args) {
        JFrame frame = new JFrame("CBC's Splendid Shape Shooter!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game mainInstance = new Game();
        frame.setContentPane(mainInstance);
        frame.pack();
        frame.setVisible(true);
        //while(frame.isActive()){
        //    System.out.println("1");
        //    if (mainInstance.restartTracker){
        //        System.out.println("2");
        //        new Game(mainInstance);
        //        mainInstance.restartTracker = false;
        //    }
        //}
    }

    public void paintComponent(Graphics bg) {
        //Set Background
        Graphics2D g = (Graphics2D) bg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint background = new GradientPaint(0, 0, Color.BLACK, 0, HEIGHT, new Color(255, 153, 51));
        g.setPaint(background);
        g.fill(new Rectangle2D.Double(0, 0, WIDTH, HEIGHT));

        //World Draw Calls
        world.drawShot(g);
        world.drawPlayer(g);
        world.drawBalls(g);
        world.drawSquares(g);
        world.checkCollision(g);
        world.drawScore(world.score, g);
        world.drawLevel(world.level, g);
        world.updateNewLevel(g);
        world.drawGameOver(world.score, g);
    }

}