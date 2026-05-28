package objects.PowerUp_Effects;

import java.awt.Color;
import java.awt.Graphics;

import managers.PowerUpManager;
import managers.ScoreManager;
import user_interface.GamePanel;

public class PowerUps_BigPoints extends PowerUps   {

    public PowerUps_BigPoints (PowerUpManager powerUpManager, int x, int y, int tileSize, int boardWidth, int boardHeight, ScoreManager scoreManager) {
        super(powerUpManager, x, y, tileSize, boardWidth, boardHeight, scoreManager);
    }

    @Override
    public void applyPowerUp(GamePanel gamePanel) {

        powerUpManager.setPowerUpEndTime(System.currentTimeMillis() + 300);
        scoreManager.addPoints(5);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(powerUp.getX() * tileSize, powerUp.getY() * tileSize, tileSize, tileSize);
    }

    @Override
    public void update() {
        
    }

}
