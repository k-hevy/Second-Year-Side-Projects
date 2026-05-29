package objects.PowerUp_Effects;

import java.util.Random;

import managers.PowerUpManager;
import managers.ScoreManager;
import objects.Entity;
import objects.Tile;
import user_interface.GamePanel;

public abstract class PowerUps extends Entity {

    protected int tileSize;
    protected int boardWidth, boardHeight;
    protected Tile powerUp;
    protected Random random;
    protected int x, y;

    protected PowerUpManager powerUpManager;
    protected ScoreManager scoreManager;

    public PowerUps (PowerUpManager powerUpManager, int x, int y, int tileSize, int boardWidth, int boardHeight, ScoreManager scoreManager) {
        this.powerUpManager = powerUpManager;
        this.scoreManager = scoreManager;
        this.x = x;
        this.y = y;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileSize = tileSize;
        random = new Random();
        powerUp = new Tile(x, y);
    }

    public Tile getPowerUp() {
        return powerUp;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public void resetPosition() {
        x = -1;
        y = -1;
    }

    public abstract void applyPowerUp(GamePanel gamePanel);

    public abstract int getPowerUpType();

}
