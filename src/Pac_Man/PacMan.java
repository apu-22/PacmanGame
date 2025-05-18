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

        void reset(){
            this.x = this.startX;
            this.y = this.startY;
        }
    }
    private int rowCount = 21;
    private int columnCount = 19;
    private int tileSize = 28;
    private int Width = columnCount * tileSize;
    private int Height = rowCount * tileSize;
    char nextDirection = ' '; // default: no direction


    private Image wallImage;
    private Image blueGhost;
    private Image redGhost;
    private Image pinkGhost;
    private Image orangeGhost;

    private Image pacmanUp;
    private Image pacmanDown;
    private Image pacmanRight;
    private Image pacmanLeft;
    private Image bonusFruitImage;

    Block bonusFruit = null;
    long lastBonusSpawnTime = System.currentTimeMillis();
    int bonusInterval = 7000;
    long bonusAppearTime = 0;
    int bonusVisibleDuration = 5000;



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

        bonusFruitImage = ImageLoader.load("/picture/bonusFruit.png");


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
        bonusFruit = null;

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

         if (bonusFruit != null) {
             g.drawImage(bonusFruit.image, bonusFruit.x, bonusFruit.y, bonusFruit.width, bonusFruit.height, null);
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
         if (nextDirection != pacman.direction) {
             // Try temporarily changing direction
             char originalDirection = pacman.direction;
             int originalX = pacman.x;
             int originalY = pacman.y;

             pacman.updateDirection(nextDirection);
             pacman.x += pacman.velocityX;
             pacman.y += pacman.velocityY;

             boolean canMove = true;
             for (Block wall : walls) {
                 if (collision(pacman, wall)) {
                     canMove = false;
                     break;
                 }
             }

             // Revert back for now
             pacman.x = originalX;
             pacman.y = originalY;

             if (canMove) {
                 // Apply new direction if no collision
                 pacman.updateDirection(nextDirection);
             } else {
                 // Else keep going in old direction
                 pacman.updateDirection(originalDirection);
             }
         }

         pacman.x += pacman.velocityX;
         pacman.y += pacman.velocityY;

         if (pacman.x < -pacman.width){
            pacman.x = Width;
         } else if (pacman.x > Width) {
            pacman.x = -pacman.width;
         }

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
             if (collision(ghost, pacman)){
                 lives -= 1;

                 if (lives == 0){
                     SoundLoader.play("/sound/pacman_death.wav");
                     gameOver = true;
                     return;
                 } else {
                     SoundLoader.play("/sound/pacman_collisionGhost.wav");
                 }

                 resetPosition();
             }

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
                 SoundLoader.play("/sound/pacman_eatDots.wav");
             }
         }

         foods.remove(foodEaten);
         if (foods.isEmpty()){
             loadMap();
             resetPosition();
         }

         //check bonus food collision
         if (bonusFruit != null && collision(pacman, bonusFruit)) {
             score += 50;
             bonusFruit = null;
             SoundLoader.play("/sound/pacman_eatFruit.wav"); // optional sound
         }

         // BONUS fruit spawn every 7 sec
         if (System.currentTimeMillis() - lastBonusSpawnTime >= bonusInterval) {
             spawnBonusFruit();
             lastBonusSpawnTime = System.currentTimeMillis();
         }

         if (bonusFruit != null && System.currentTimeMillis() - bonusAppearTime >= bonusVisibleDuration){
             bonusFruit = null;
         }

     }

     public boolean collision(Block a, Block b){
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
     }

    public void spawnBonusFruit() {
        if (bonusFruit != null || foods.isEmpty()) return; // Already exists or no place to spawn

        int index = random.nextInt(foods.size());
        int i = 0;
        for (Block food : foods) {
            if (i == index) {
                bonusFruit = new Block(bonusFruitImage, food.x - 14, food.y - 14, tileSize - 8, tileSize - 8);
                foods.remove(food);
                bonusAppearTime = System.currentTimeMillis();
                break;
            }
            i++;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e){ // // গেম আপডেট বা repaint করার জন্য ব্যবহার হয়
        move();
        repaint();
        if (gameOver){
            gameLoap.stop();
        }
    }

    public void resetPosition(){
        pacman.reset();
        pacman.velocityX = 0;
        pacman.velocityY = 0;
        for (Block ghost : ghosts){
            ghost.reset();
            char newDirection = direction[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver){
            loadMap();
            resetPosition();
            lives = 3;
            score = 0;
            gameOver = false;
            gameLoap.start();
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            nextDirection = 'U';
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            nextDirection = 'D';
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            nextDirection = 'L';
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            nextDirection = 'R';
        }

        if(pacman.direction == 'U'){
            pacman.image = pacmanUp;
        }

        if (nextDirection == 'U') {
            pacman.image = pacmanUp;
        } else if (nextDirection == 'D') {
            pacman.image = pacmanDown;
        } else if (nextDirection == 'L') {
            pacman.image = pacmanLeft;
        } else if (nextDirection == 'R') {
            pacman.image = pacmanRight;
        }

    }

}
