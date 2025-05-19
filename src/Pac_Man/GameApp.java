package Pac_Man;

import javax.swing.JFrame;

public class GameApp {
    public static void main(String[] args){
        int rowConut=21;
        int columnCount=19;
        int tilesize= 28;
        int Width=columnCount*tilesize;
        int Height=rowConut*tilesize;

//      Start background music
        SoundLoader bgMusic = new SoundLoader("/sound/pacman_beginning.wav");
        bgMusic.playLoop();

        JFrame window = new JFrame("Pac Man");
        window.setSize(Width,Height);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacMan pacmanGame = new PacMan(bgMusic);

        window.add(pacmanGame);
        window.pack();
        pacmanGame.requestFocus();
        window.setVisible(true);
    }
}
