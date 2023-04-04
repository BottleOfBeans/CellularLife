import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

public class GameWindow extends JPanel  implements Runnable{
    /*
        Game Tile Size shows the tile that each of the images on screen should be in pixels, this is not the amount that are displayed in each tile but rather the orignal one
        Scalable value shows the value that is used to upscale the original images on the screen
        Game Column and Row amounts show the amount of rows and columns that can be displayed on screen at one time
     */
    static int GameTileSize = 16;
    static int ScalableValue = 3;
    static int gameColumnAmount = 24;
    static int gameRowAmount = 20;


    /*
        ActualTileSize uses the variables from above to calculate how big each tile actually should be
        Game Width and Height takes into account the columns and rows and calculates the window heights and widths using all the factors provided above
     */
    static int ActualTileSize = GameTileSize * ScalableValue;
    static int gameWidth = 1920; //gameColumnAmount*ActualTileSize;
    static int gameHeight = 1080; //gameRowAmount*ActualTileSize;

    /*
        Offsets
        These allow for the screen to always be focusing on the main body at all times :)
     */
    static double xOffset;
    static double yOffset;


    Thread gameThread;

    //Game Values
    int FPS = 144;
    double zoomX = .2;
    double zoomY = .2;

    //Creating the game windows and setting up the settings
    public GameWindow(){
        this.setPreferredSize(new Dimension(gameWidth, gameHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    //Starting thread, managing frame updates
    public void startWindowThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    /*
        Particles!
     */
    public double MaxParticle = 500;
    public static ArrayList<Particle> particles = new ArrayList<Particle>();

    //Loop that runs the thread, allows for it to sleep and start and ensures proper frame speed
    @Override
    public void run(){
        /*
            Add code here that runs before he game starts
         */
        Random rand = new Random();
        
        for(int i = 0; i < MaxParticle; i++){
            double randomAngle = rand.nextInt(0,360);
            double randomXPos = rand.nextDouble(0, gameWidth / zoomX);
            double randomYPos = rand.nextDouble(0, gameHeight / zoomY);
            particles.add(new Particle(new Vector2(randomAngle), new Vector2(randomXPos, randomYPos)));
        }


        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;


        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;
            if(delta >= 1){
                update(delta);
                repaint();
                delta--;
            }

        }
    }

    public void update(double deltaTime){
        for(Particle p : particles){
            p.updateParticle(particles);
            p.updateLocation(deltaTime);
        }
    }

    //Function that paints the updated version of the frame {FPS} times a second.
    public void paintComponent(Graphics g){

        //Quick definition of varibles to use with the G2D library
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D)g;

        AffineTransform oldTransform = graphics.getTransform();
        graphics.scale(zoomX, zoomY);

        // for(Particle p:particles){
        //     graphics.setColor(Color.gray);
        //     graphics.fill(p.getEffectArea());
        // }

        for(Particle p : particles){
            graphics.setColor(p.color);
            graphics.fill(p.getParticle());
            //graphics.setColor(Color.BLACK);
            //graphics.draw(p.directionLine());
        }

        graphics.setTransform(oldTransform);

        //Stopping the use of the library to ensure that no more processing power than needed is used
        graphics.dispose();
    }

}

