package Pac_Man;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.HashSet;
import java.awt.event.*;
public class PacMan extends JPanel {

    class Block{
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;

        Block(Image image, int x,int y,int width,int height){
            this.x=x;
            this.y=y;
            this.height=height;
            this.image=image;
            this.width=width;

            this.startX=x;
            this.startY=y;
        }
    }
    private int rowCount = 21;
    private int columnCount = 19;
    private int tileSize = 32;
    private int Width = columnCount * tileSize;
    private int Height = rowCount * tileSize;

    private Image wallImage;
    private Image blueGhost;
    private Image redGhost;
    private Image pinkGhost;
    private Image orangeGhost;

    private Image pacmanUp;
    private Image pacmanDown;
    private Image pacmanRight;
    private Image pacmanLeft;

    //X = wall, O = skip, P = pac man, ' ' = food
    //Ghosts: b = blue, o = orange, p = pink, r = red
    private String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
    };


    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;


    public PacMan() {
        setPreferredSize(new Dimension(Width,Height));
        setBackground(Color.BLACK);

        // Load images using ImageLoader
        wallImage = ImageLoader.load("/picture/wall.png");
        blueGhost = ImageLoader.load("/picture/blueGhost.png");
        redGhost = ImageLoader.load("/picture/redGhost.png");
        pinkGhost = ImageLoader.load("/picture/pinkGhost.png");
        orangeGhost = ImageLoader.load("/picture/orangeGhost.png");

        pacmanUp = ImageLoader.load("/picture/pacmanUp.png");
        pacmanDown = ImageLoader.load("/picture/pacmanDown.png");
        pacmanRight = ImageLoader.load("/picture/pacmanRight.png");
        pacmanLeft = ImageLoader.load("/picture/pacmanLeft.png");

        loadMap();

    }

    public void loadMap(){
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for(int r=0;r<rowCount;r++){
            for(int c=0;c<columnCount;c++){
                String row = tileMap[r];
                char tileMapChar = row.charAt(c);

                int x = c*tileSize;
                int y = r*tileSize;

                if(tileMapChar == 'X'){
                    Block wall = new Block(wallImage,x,y,tileSize,tileSize);
                    walls.add(wall);
                }
                else if (tileMapChar == 'b'){
                    Block ghost = new Block(blueGhost,x,y,tileSize,tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'o'){
                    Block ghost = new Block(orangeGhost,x,y,tileSize,tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'p'){
                    Block ghost = new Block(pinkGhost,x,y,tileSize,tileSize);
                    ghosts.add(ghost);
                }
                else if (tileMapChar == 'r'){
                    Block ghost = new Block(redGhost,x,y,tileSize,tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'P') {
                    pacman = new Block(pacmanRight,x,y,tileSize,tileSize);
                } else if (tileMapChar == ' ') {
                    Block food = new Block(null,x+14,y+14,4,4);
                    foods.add(food);
                }
            }
        }
    }

}
