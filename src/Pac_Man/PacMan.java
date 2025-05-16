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
    public PacMan() {
        setPreferredSize(new Dimension(Width,Height));
        setBackground(Color.BLACK);
    }
}
