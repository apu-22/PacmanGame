package Pac_Man;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.HashSet;
import java.awt.event.*;
public class PacMan extends JPanel implements ActionListener, KeyListener {

    class Block{
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;
        char direction = 'U';
        int velocityX=0;
        int velocityY=0;

        Block(Image image, int x,int y,int width,int height){
            this.x=x;
            this.y=y;
            this.height=height;
            this.image=image;
            this.width=width;

            this.startX=x;
            this.startY=y;

        }
        void updateDirection(char direction){
            char prevDirection = this.direction;
            this.direction=direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;
            for(Block wall : walls){
                if(collision(this, wall)){
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = prevDirection;
                    updateVelocity();
                }
            }

        }
        void updateVelocity(){
            if(this.direction == 'U'){
                this.velocityX = 0;
                this.velocityY = -tileSize/4;
            }
            else if (this.direction == 'D') {
                this.velocityX = 0;
                this.velocityY = tileSize/4;
            }
            else if (this.direction == 'L') {
                this.velocityX = -tileSize/4;
                this.velocityY = 0;
            }
            else if (this.direction == 'R') {
                this.velocityX = tileSize/4;
                this.velocityY = 0;
            }

        }
    }
    private int rowCount = 21;
    private int columnCount = 19;
    private int tileSize = 28;
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

    Timer gameLoap;
    char[] direction = {'U', 'D', 'L', 'R'};
    Random random = new Random();
    int score = 0;
    int lives = 3;
    boolean gameOver = false;


    public PacMan() {
        setPreferredSize(new Dimension(Width,Height));
        setBackground(Color.BLACK);

        addKeyListener(this);
        setFocusable(true);

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
        for(Block ghost : ghosts){
            char newDirection = direction[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
        //how long it takes to start. milliseconds gone between frames
        gameLoap = new Timer(50,this);
        gameLoap.start();
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
                    pacman = new Block(pacmanRight,x,y,tileSize-4,tileSize-4);
                } else if (tileMapChar == ' ') {
                    Block food = new Block(null,x+14,y+14,4,4);
                    foods.add(food);
                }
            }
        }
    }

     public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
     }
     public void draw(Graphics g){
        g.drawImage(pacman.image,pacman.x,pacman.y,pacman.width,pacman.height,null);

         for(Block ghost : ghosts){
             g.drawImage(ghost.image,ghost.x,ghost.y,ghost.width,ghost.height,null);
         }
         for(Block wall : walls){
             g.drawImage(wall.image,wall.x,wall.y,wall.width,wall.height,null);
         }
         g.setColor(Color.WHITE);
         for(Block food : foods){
             g.fillRect(food.x,food.y,food.width,food.height);
         }

         //score & lives
         g.setFont(new Font("Arial", Font.PLAIN, 18));
         if (gameOver){
             g.drawString("Game Over: " + String.valueOf(score), tileSize/2, tileSize/2);
         }
         else {
             g.drawString("x" + String.valueOf(lives) + "Score: " +String.valueOf(score), tileSize/2, tileSize/2);
         }
     }

     public void move(){
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        //check wall collisions
         for (Block wall : walls){
             if(collision(pacman, wall)){
                 pacman.x -=pacman.velocityX;
                 pacman.y -= pacman.velocityY;
                 break;
             }
         }

         //check ghost collision
         for(Block ghost : ghosts){
             if (ghost.y == tileSize*9 && ghost.direction != 'U' && ghost.direction != 'D'){
                 ghost.updateDirection('U');
             }
             ghost.x += ghost.velocityX;
             ghost.y += ghost.velocityY;
             for (Block wall : walls){
                 if (collision(ghost, wall) || ghost.x <= 0 || ghost.x + ghost.width >= Width){
                     ghost.x -= ghost.velocityX;
                     ghost.y -= ghost.velocityY;
                     char newDirection = direction[random.nextInt(4)];
                     ghost.updateDirection(newDirection);
                 }
             }
         }

         //check food collision
         Block foodEaten = null;
         for (Block food : foods){
             if (collision(pacman, food)){
                 foodEaten = food;
                 score += 10;
             }
         }
         foods.remove(foodEaten);
     }

     public boolean collision(Block a, Block b){
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
     }

     @Override
    public void actionPerformed(ActionEvent e){ // // গেম আপডেট বা repaint করার জন্য ব্যবহার হয়
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println("KeyEvent: " + e.getKeyCode());
        if(e.getKeyCode() == KeyEvent.VK_UP){
            pacman.updateDirection('U');
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            pacman.updateDirection('D');
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            pacman.updateDirection('L');
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            pacman.updateDirection('R');
        }

        if(pacman.direction == 'U'){
            pacman.image = pacmanUp;
        }
        else if(pacman.direction == 'D'){
            pacman.image = pacmanDown;
        }
        else if(pacman.direction == 'R'){
            pacman.image = pacmanRight;
        }
        else if(pacman.direction == 'L'){
            pacman.image = pacmanLeft;
        }
    }

}
