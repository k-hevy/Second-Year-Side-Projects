package objects.PowerUp_Effects;

import java.util.Random;

import objects.Tile;
import user_interface.GamePanel;

import java.awt.Color;
import java.awt.Graphics;

public abstract class PowerUps {

    protected int tileSize;
    protected int boardWidth, boardHeight;
    protected Tile powerUp;
    protected Random random;
    protected int x, y;

    public PowerUps (int x, int y, int tileSize, int boardWidth, int boardHeight) {
        this.x = x;
        this.y = y;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.tileSize = tileSize;
        random = new Random();
        powerUp = new Tile(x, y);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(powerUp.getX() * tileSize, powerUp.getY() * tileSize, tileSize, tileSize);
    }

    

    public void resetPosition() { 
        powerUp = new Tile(-1, -1);
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public abstract void applyPowerUp(GamePanel gamePanel);

}
