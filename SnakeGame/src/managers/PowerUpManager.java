package managers;

import objects.Tile;
import objects.PowerUp_Effects.PowerUps;
import objects.PowerUp_Effects.PowerUps_BigPoints;
import objects.PowerUp_Effects.PowerUps_DoublePoints;
import objects.PowerUp_Effects.PowerUps_SpeedBoost;
import user_interface.GamePanel;

import java.util.ArrayList;
import java.util.Random;

public class PowerUpManager {

    private PowerUps currentPowerUp;
    private GamePanel gamePanel;
    private ScoreManager scoreManager;
    private EntityManager entityManager;

    private boolean boostIsActive;

    private long nextSpawnTime;
    private long powerUpEndTime;

    private int boardHeight;
    private int boardWidth;
    private int tileSize;
    

    private Random random;

    public PowerUpManager (GamePanel gamePanel, int boardWidth, int boardHeight, int tileSize, ScoreManager scoreManager, EntityManager entityManager) {
        this.gamePanel = gamePanel;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileSize = tileSize;
        this.scoreManager = scoreManager;
        this.entityManager = entityManager;
        random = new Random();
        scheduleNextPowerUp();
    }

    public void update () {

        long currentTime = System.currentTimeMillis();

        if ((currentPowerUp ==  null  && (currentTime >= nextSpawnTime)) ||
            (currentPowerUp != null && (nextSpawnTime - currentTime <= 0)) ) {
            spawnPowerUps(boardWidth, boardHeight, tileSize, gamePanel.getBlockedTiles());
            scheduleNextPowerUp();
            // currentPowerUp.resetPosition()
        }

        endEffect();

    }

    private void spawnPowerUps (int boardWidth, int boardHeight, int tileSize, ArrayList<Tile> blockedTiles) {

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
            case 0 : currentPowerUp = new PowerUps_BigPoints(this, x, y, tileSize, boardWidth, boardHeight, scoreManager); break;
            case 1 : currentPowerUp = new PowerUps_DoublePoints(this, x, y, tileSize, boardWidth, boardHeight, scoreManager); break;
            case 2 : currentPowerUp = new PowerUps_SpeedBoost(this, x, y, tileSize, boardWidth, boardHeight, scoreManager); break;
        }

        entityManager.addEntity(currentPowerUp);

    }

    private void scheduleNextPowerUp() {
        long randomSec =  10 + (long) random.nextInt(6);
        nextSpawnTime = System.currentTimeMillis() + (randomSec * 1000);
    }

    public void applyPowerUp() {
        currentPowerUp.applyPowerUp(gamePanel);
        boostIsActive = true;
        currentPowerUp.destroy();
    } // note: Objects already change the end time

    public void endEffect () {
        if (boostIsActive && (System.currentTimeMillis() > powerUpEndTime)) {
            gamePanel.resetPowerUpEffects();
            boostIsActive = false;
        } 
    }

    public void reset() {
        scheduleNextPowerUp();
    }

    public boolean isEffectActive() {
        return boostIsActive;
    }

    public void setEffectState(boolean value) {
        boostIsActive = value;
    }

    public long getNextPowerUpTime() {
        return nextSpawnTime;
    }

    public void setPowerUpEndTime (long num) {
        powerUpEndTime = num;
    }

    public PowerUps getPowerUps() {
        return currentPowerUp;
    }
}
