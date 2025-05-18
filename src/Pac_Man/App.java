package Pac_Man;

import javax.swing.JFrame;

public class App {
    public static void main(String[] args){
        int rowConut=20;
        int columnCount=19;
        int tilesize= 32;
        int Width=columnCount*tilesize;
        int Height=rowConut*tilesize;

        // 🔊 Start background music
        SoundLoader music = new SoundLoader("/sound/pacman_beginning.wav");
        music.playLoop();

        JFrame window = new JFrame("Pac Man");
        window.setSize(Width,Height);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacMan pacmanGame = new PacMan();
        window.add(pacmanGame);
        window.pack();
        pacmanGame.requestFocus();
        window.setVisible(true);
    }
}
