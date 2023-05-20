import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class GameWindow extends JPanel implements Runnable {
    /*
     * Game Tile Size shows the tile that each of the images on screen should be in
     * pixels, this is not the amount that are displayed in each tile but rather the
     * orignal one
     * Scalable value shows the value that is used to upscale the original images on
     * the screen
     * Game Column and Row amounts show the amount of rows and columns that can be
     * displayed on screen at one time
     */
    static int GameTileSize = 16;
    static int ScalableValue = 3;
    static int gameColumnAmount = 24;
    static int gameRowAmount = 20;

    /*
     * ActualTileSize uses the variables from above to calculate how big each tile
     * actually should be
     * Game Width and Height takes into account the columns and rows and calculates
     * the window heights and widths using all the factors provided above
     */
    static int ActualTileSize = GameTileSize * ScalableValue;
    static int gameWidth = 1000; // gameColumnAmount*ActualTileSize;
    static int gameHeight = 1000; // gameRowAmount*ActualTileSize;

    Thread gameThread;

    // Game Values
    int FPS = 60;
    int steps = 7;

    static double zoomX = 0.5;
    static double zoomY = 0.5;

    double zoomXOffset = 1.3;
    double zoomYOffset = 1.3;
    double deltaTime = 0;

    // Creating the game windows and setting up the settings
    public GameWindow() {
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    // Starting thread, managing frame updates
    public void startWindowThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /*
     * Particles!
     */
    public double MaximumVelocity = 5;
    public double MaxParticle = 500; // Maximum particle amount
    public static ArrayList<Particle> particles = new ArrayList<Particle>(); // List containing all of the particles


    /*
     * Constraints :)
     */
    double diameter = 600/zoomX;

    public Ellipse2D CircularContainer = new Ellipse2D.Double(gameWidth/2/zoomX - diameter/2, gameHeight/2/zoomY - diameter/2, diameter, diameter);


    // Loop that runs the thread, allows for it to sleep and start and ensures
    // proper frame speed
    @Override
    public void run() {
        /*
         * Add code here that runs before he game starts
         */
        Random rand = new Random();

        // Creating a bunch of random particles with adjustment to zoom
        for (int i = 0; i < MaxParticle; i++) {
            double randomXPos =  rand.nextDouble(0 + gameWidth*zoomXOffset/2, gameWidth / zoomX - gameWidth*zoomXOffset/2); // gameWidth/zoomX / 2 ; (For Center)
            double randomYPos =  rand.nextDouble(0 + gameHeight*zoomYOffset/2, gameHeight / zoomY - gameHeight*zoomYOffset/2); // gameHeight/zoomY / 2 ; (For Center)
            particles.add(new Particle(new Vector2(randomXPos, randomYPos)));
        }

        // Managing updates and FPS
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;
            if (delta >= 1) {
                update(); // Sending the deltaTime through the update function
                deltaTime = delta;
                repaint();
                delta--;
            }

        }
    }

    public void update() {
        ;
    }

    // Function that paints the updated version of the frame {FPS} times a second.
    public void paintComponent(Graphics g) {
        
        // Quick definition of varibles to use with the G2D library
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;

        AffineTransform oldTransform = graphics.getTransform();
        graphics.scale(zoomX, zoomY); // Zoomgin on two axis'!

        graphics.setColor(Color.DARK_GRAY);
        graphics.fill(CircularContainer);

        graphics.setColor(Color.white);

        for (Particle p : particles) {
            p.update(deltaTime);
            graphics.fill(p.getParticle());   
        }

        graphics.setTransform(oldTransform); // Putting the locations of the objects to what they should be post zoom.

        graphics.dispose();
    }

}
