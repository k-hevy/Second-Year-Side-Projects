package user_interface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Random;

import objects.Event;
import objects.Food;
import objects.Snake;
import objects.Tile;
import objects.Particle;
import objects.GameState;
import objects.Obstacle;

import managers.CollisionManager;
import managers.GameStateManager;
import managers.InputManager;
import managers.PowerUpManager;
import managers.ScoreManager;
import managers.SoundManager;
import managers.EntityManager;
import managers.EventManager;

public class GamePanel extends JPanel implements ActionListener {

    // For Panels
    private SnakeGame parent;

    CardLayout layout;
    JPanel mainPanel;

    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    //Timers
    long lastTime;
    double deltaTime;

    double moveTimer;
    double moveInterval;
    double baseMoveInterval;
    double speedLevel;

    // Objects
    public Snake snake;
    Food food;
    Tile tile;
    Obstacle obstacle;
    ArrayList<Tile> blockedTiles;

    // Managers
    GameStateManager gameStateManager;
    InputManager input;
    PowerUpManager powerUpManager;
    CollisionManager collisionManager;
    ScoreManager scoreManager;
    SoundManager soundManager;
    EntityManager entityManager;
    EventManager eventManager;

    // Game logic
    Timer gameLoop;

    // Others
    Random random;
    BufferedReader br;

    // Constructor
    GamePanel (SnakeGame parent, int boardWidth, int boardHeight, GameStateManager gameStateManager) {

        // boardSize
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        // For Panel / Frames
        this.parent = parent;
        setFocusable(true);
        requestFocusInWindow();
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);

        // For objects
        snake = new Snake(12, 5);
        food = new Food(snake, boardWidth, boardHeight, tileSize);
        random = new Random();
        obstacle = new Obstacle(tileSize, boardWidth, boardHeight);

        // Background prep
        obstacle.generateObstacles();
        blockedTiles = new ArrayList<Tile>();

        // Managers
        eventManager = new EventManager();
        this.gameStateManager = gameStateManager;
        soundManager = new SoundManager();
        input = new InputManager(snake, this);
        input.setUpInput(this);
        scoreManager = new ScoreManager();
        entityManager = new EntityManager();
        powerUpManager = new PowerUpManager(this, boardWidth, boardHeight, tileSize, scoreManager, entityManager);
        collisionManager = new CollisionManager(soundManager, this, powerUpManager, obstacle, snake, food, boardWidth, boardHeight, tileSize);
        
        entityManager.addEntity(food);

        // game Timer
        
        gameLoop = new Timer(16, this);
        gameLoop.start();

        lastTime = System.nanoTime();
        moveTimer = 0;
        baseMoveInterval = 0.15;
        moveInterval = baseMoveInterval;
        speedLevel = 1;

    }

    //Flow
    @Override 
    public void actionPerformed(ActionEvent e) {

        update();
        repaint();
        
    }

    public void gameOver() {
        gameLoop.stop();
        parent.showGameOver();

    } 

    public void resetGame() {

        speedLevel = 1;
        moveInterval = baseMoveInterval;
        snake.reset();
        food.reset();
        obstacle.reset();
        powerUpManager.reset();
        scoreManager.reset();

        gameLoop.start();
        gameLoop.setDelay(16);
        
        repaint();

    }

//background initializing

    public void togglePause () {
        if (gameStateManager.getGameState() == GameState.PLAYING) {
            gameStateManager.setGameState(GameState.PAUSED);
        } else if (gameStateManager.getGameState() == GameState.PAUSED) {
            gameStateManager.setGameState(GameState.PLAYING);
        }
    }
    
    // Game Logic
    public void update() {

        if (gameStateManager.getGameState() != GameState.PLAYING) return;

        long now = System.nanoTime();
        deltaTime = (now - lastTime) / 1_000_000_000.0;
        lastTime = now;

        moveTimer = moveTimer + deltaTime;

        if (moveTimer >= moveInterval) {
            snake.move();
            moveTimer = 0;
        }

        for (Event e : eventManager.getEvents()) {
            switch (e.getType()) {
                case FOOD_EATEN :
                    handleFoodEaten();
                    SoundManager.playSound(SoundManager.SoundEffect.EAT_FOOD);
                    break;

                case GAME_OVER :
                    gameOver(); 
                    SoundManager.playSound(SoundManager.SoundEffect.COLLISION);
                    break;

                case POWERUP_COLLECTED :
                    powerUpManager.applyPowerUp();
                    SoundManager.playSound(SoundManager.SoundEffect.POWERUP);
                    break;
            }
        }

        eventManager.clearEvent();
        scoreManager.update();
        collisionManager.update();
        powerUpManager.update(); // 
        entityManager.update();

    }   

    public void handleFoodEaten() {

        for (int i = 0; i < 10; i++) {
            entityManager.addEntity(new Particle(
                (float) food.getFoodTile().getX() * tileSize + 12, 
                (float) food.getFoodTile().getY() * tileSize + 12, 
                0));
        }

        scoreManager.setScore(scoreManager.getScore() + (1 * scoreManager.getMultiplier()));
        food.randomizeFood();
        snake.grow();

        if (scoreManager.getScore() % 5 == 0 && moveInterval > 0.05) {
            speedLevel += 0.05;
            if (speedLevel >= 2.5) speedLevel = 2.5;
            if (!powerUpManager.isEffectActive()) moveInterval = baseMoveInterval / speedLevel;
        }

    }

    public void resetPowerUpEffects () {
        scoreManager.setMultiplier(1);
        moveInterval = baseMoveInterval / speedLevel;
    }

    public double getBaseMoveInterval() {
        return baseMoveInterval / speedLevel;
    }

    public void setMoveInterval(double num) {
        moveInterval = num;
    }

    public ArrayList<Tile> getBlockedTiles() {
        blockedTiles.clear();
        blockedTiles.addAll(snake.getBody());
        blockedTiles.addAll(obstacle.getObstacles());
        blockedTiles.add(food.getFoodTile());
        if (powerUpManager.getPowerUps() != null) blockedTiles.add(powerUpManager.getPowerUps().getPowerUp());
        return blockedTiles;
    }

    public void render(Graphics g) {

    snake.draw(g, tileSize);
    obstacle.drawObstacles(g, tileSize);
    entityManager.draw(g);

    g.setColor(Color.BLUE);
    
    // score
    g.setFont(new Font("Arial", Font.PLAIN, 16));
    drawHudAndScores(g);

    if (gameStateManager.getGameState() == GameState.PAUSED) drawPausedOverlay(g);

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
        g.drawString("Score: " + scoreManager.getScore(), tileSize - 16, tileSize);
        g.drawString("Highscore: " + scoreManager.getHighscore(), tileSize - 16 , tileSize + 25);
        g.drawString("Level: " + scoreManager.getLevel(), tileSize - 16 , tileSize + 50);
        g.drawString("Speed: " + moveInterval, tileSize - 16 , tileSize + 75);
        g.drawString("Next powerUp: " + (powerUpManager.getNextPowerUpTime() - System.currentTimeMillis()) / 1000 + "s", tileSize - 16 , tileSize + 100);
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
