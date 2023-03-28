import javax.swing.*;


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
        window.setTitle("Romir's Silly Goofy Little Game Thing :)");
        GameWindow gameWindow = new GameWindow();
        window.add(gameWindow);
        window.setUndecorated(true);
        window.pack();
        window.setVisible(true);
        gameWindow.startWindowThread();

    }

}
