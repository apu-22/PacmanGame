package Pac_Man;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.HashSet;
import java.awt.event.*;
public class PacMan extends JPanel {

    private int rowCount = 21;
    private int columnCount = 19;
    private int tileSize = 32;
    private int Width = columnCount * tileSize;
    private int Height = rowCount * tileSize;

    private Image wall;
    private Image blueGhost;
    private Image redGhost;
    private Image pinkGhost;
    private Image orangeGhost;

    private Image pacmanUp;
    private Image pacmanDown;
    private Image pacmanRight;
    private Image pacmanLeft;

    public PacMan() {
        setPreferredSize(new Dimension(Width,Height));
        setBackground(Color.BLACK);

        // Load images using ImageLoader
        wall = ImageLoader.load("/picture/wall.png");
        blueGhost = ImageLoader.load("/picture/blueGhost.png");
        redGhost = ImageLoader.load("/picture/redGhost.png");
        pinkGhost = ImageLoader.load("/picture/pinkGhost.png");
        orangeGhost = ImageLoader.load("/picture/orangeGhost.png");

        pacmanUp = ImageLoader.load("/picture/pacmanUp.png");
        pacmanDown = ImageLoader.load("/picture/pacmanDown.png");
        pacmanRight = ImageLoader.load("/picture/pacmanRight.png");
        pacmanLeft = ImageLoader.load("/picture/pacmanLeft.png");
    }
}
