package user_interface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

import objects.Food;
import objects.Snake;
import objects.Tile;
import objects.PowerUp_Effects.PowerUps;
import objects.PowerUp_Effects.PowerUps_BigPoints;
import objects.PowerUp_Effects.PowerUps_DoublePoints;
import objects.PowerUp_Effects.PowerUps_SpeedBoost;
import objects.GameState;
import objects.Obstacle;
import Controls.InputManager;



public class GamePanel extends JPanel implements ActionListener {

    private SnakeGame parent;

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    final int startDelay = 140;
    int delay;
    final int minDelay = 90;
    int level;

    CardLayout layout;
    JPanel mainPanel;
    Random random;

    int score;
    private int highscore;
    BufferedReader br;

    Snake snake;
    Food food;
    Tile tile;
    Obstacle obstacle;
    PowerUps powerUps;
    

    ArrayList<Tile> blockedTiles;

    InputManager input;

    private boolean boostActive = false;
    private int multiplier = 1;
    private long powerUpEndTime = 0;
    private long nextPowerUpSpawnTime = 0;
    private PowerUps currentPowerUp;

    // Game logic
    Timer gameLoop;

    // Constructor
    GamePanel (SnakeGame parent, int boardWidth, int boardHeight) {

        this.parent = parent;

        setFocusable(true);
        requestFocusInWindow();

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        snake = new Snake(12, 5);
        food = new Food(snake, boardWidth, boardHeight, tileSize);
        random = new Random();
        obstacle = new Obstacle(tileSize, boardWidth, boardHeight);
        obstacle.generateObstacles();

        blockedTiles = new ArrayList<Tile>();
        scheduleNextPowerUp();
        input = new InputManager(snake, this);
        input.setUpInput(this);

        loadHighscore();

        level = 1;
        delay = 140;

        gameLoop = new Timer(startDelay, this);
        gameLoop.start();

    }

    //Flow
    @Override 
    public void actionPerformed(ActionEvent e) {

        update();
        repaint();
        
    }

    public boolean gameOver() {

        if (snake.collidedWithSelf(snake)) return true;
        if (snake.collidedWithWall(snake, boardWidth, boardHeight, tileSize)) return true;
        if (obstacle.checkSnakeObstacleCollision(snake)) return true; 

        return false;
    } // also move this to snake

    public void resetGame() {

        score = 0;
        level = 1;
        scheduleNextPowerUp();

        snake.reset();
        food.reset();
        obstacle.reset();
        currentPowerUp = spawnPowerUps();

        gameLoop.start();
        gameLoop.setDelay(startDelay);
        delay = 140;
        
        repaint();

    }

//background initializing

    public void updateHighscore () {
        try (FileWriter fw = new FileWriter("highscore.txt")) {
            fw.write(String.valueOf(score));
            highscore = score;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void loadHighscore () {

        File file = new File("highscore.txt");

        if (!file.exists()) {
                highscore = 0;
                return;
            }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            highscore = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            highscore = 0;
        }
    }

    public void togglePause () {
        if (parent.getGameState() == GameState.PLAYING) {
            parent.setGameState(GameState.PAUSED);
        } else if (parent.getGameState()== GameState.PAUSED) {
            parent.setGameState(GameState.PLAYING);
        }
    }
    
    // Game Logic
    public void update() {

        if (gameOver()) {
                gameLoop.stop();
                parent.showGameOver();
            }

        if (parent.getGameState() != GameState.PLAYING) return;

        if (snake.eats(food)) {
            score = score + (1 * multiplier);
            food.randomizeFood();
            snake.grow();

            level = score / 5;

            if (score % 5 == 0 && delay > minDelay) {
                delay -= 4;
                if (!boostActive) gameLoop.setDelay(delay);
            }
        }

        if (score > highscore) updateHighscore();
        
        checkPowerUpState();

        snake.move();

    }   

    private void checkPowerUpState () {
        if ((nextPowerUpSpawnTime - System.currentTimeMillis() < 0) && currentPowerUp != null) {
            scheduleNextPowerUp(); // schdules when next powerUp spawns
            currentPowerUp = spawnPowerUps(); // chooses poweUp type and position
        }

        if ((System.currentTimeMillis() >=  nextPowerUpSpawnTime) &&  currentPowerUp == null ) {
            scheduleNextPowerUp(); // schdules when next powerUp spawns
            currentPowerUp = spawnPowerUps(); // chooses poweUp type and position
        } 

        if ( currentPowerUp != null && powerUpCollected()) {
            boostActive = true;
            currentPowerUp.applyPowerUp(this);
            currentPowerUp = null;
        }

        if (boostActive && (System.currentTimeMillis() > powerUpEndTime)) {
            boostActive = false;
            multiplier = 1;
            gameLoop.setDelay(delay);
        } // speedBoost
    }

    public void setBoostActive (boolean value) {
        boostActive = value;
    }

    public void setPopowerUpEndTime (long num) {
        powerUpEndTime = num;
    }

    public Timer getGameLoopTimer () {
        return gameLoop;
    }

    public void activateDoublePoints() {
        multiplier = 2;
    }

    public void addPoints(int n) {
        score += n;
    }

    public void scheduleNextPowerUp () {
        long randomSec =  15 + (long) random.nextInt(6);
        nextPowerUpSpawnTime = System.currentTimeMillis() + (randomSec * 1000);
    }

    public boolean powerUpCollected() {
        return snake.getHead().getX() == currentPowerUp.getX() && snake.getHead().getY() == currentPowerUp.getY();
    }

    //PowerUps

    private PowerUps spawnPowerUps () {

        blockedTiles.clear();
        blockedTiles.addAll(snake.getBody());
        blockedTiles.addAll(obstacle.getObstacles());
        blockedTiles.add(food.getFoodTile());

        boolean isValid;
        int attempts = 0;
        int x, y;

        do {

            isValid = true;

            x = random.nextInt(boardWidth / tileSize);
            y = random.nextInt(boardHeight / tileSize);

            for (Tile tile : blockedTiles) {
                if (tile.getX() == x && tile.getY() == y ) {
                        isValid = false;
                        break;
                }
            }

            attempts++;

        } while (!isValid && attempts < 50);

        int type = random.nextInt(3);

        switch (type) {
            case 0 : return new PowerUps_BigPoints(x, y, tileSize, boardWidth, boardHeight);
            case 1 : return new PowerUps_DoublePoints(x, y, tileSize, boardWidth, boardHeight);
            case 2 : return new PowerUps_SpeedBoost(x, y, tileSize, boardWidth, boardHeight);
            default : return null;
        }

    }

    public void render(Graphics g) {

    snake.draw(g, tileSize);
    food.draw(g, tileSize);
    obstacle.drawObstacles(g, tileSize);
    if (currentPowerUp != null) currentPowerUp.draw(g);

    g.setColor(Color.BLUE);
    
    // score
    g.setFont(new Font("Arial", Font.PLAIN, 16));
    drawHudAndScores(g);

    if (parent.getGameState() == GameState.PAUSED) drawPausedOverlay(g);

    }

    // visual painting
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        render(g);
    
    }

    public void drawHudAndScores(Graphics g) {
        for (int i =0; i < boardWidth/tileSize; i++) {
                // x1, y1, x2, y2
                g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
                g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
            }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, tileSize - 16, tileSize);
        g.drawString("Highscore: " + highscore, tileSize - 16 , tileSize + 25);
        g.drawString("Level: " + level, tileSize - 16 , tileSize + 50);
        g.drawString("Speed: " + delay, tileSize - 16 , tileSize + 75);
        g.drawString("Next powerUp: " + (nextPowerUpSpawnTime - System.currentTimeMillis()) / 1000 + "s", tileSize - 16 , tileSize + 100);
        // g.drawString("PowerUp type " + cou, tileSize - 16 , tileSize + 125);
        
    }

    private void drawPausedOverlay(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));

        FontMetrics fm = g.getFontMetrics();

        String text = "PAUSED";
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = getHeight()/2;

        g.drawString(text, x, y);
        g.drawString("Press 'P' To Continue.", getWidth() - 450, getHeight()/2 + 40);
    }

}
