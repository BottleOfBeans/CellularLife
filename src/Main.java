import java.awt.Dimension;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Main {
    public static void main(String args []){
        /*
            Setting up the JFrame Window
            Resizeable --> False
            Close Operation --> Exit On Close
            Window Name --> "Romir's Silly Goofy Little Game Thing :)"
            Window Visibility --> True
         */
        JFrame window = new JFrame();    

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Primordial Particle Life");
        GameWindow gameWindow = new GameWindow();
        window.add(gameWindow);
        window.setUndecorated(true);
        window.pack();
        window.setVisible(true);
        gameWindow.startWindowThread();
        

    }

}
