package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Particle extends Entity {

    private float x, y, velX, velY;
    private int size, life, maxLife;

    public Particle (float x, float y) {
        this.x = x;
        this.y = y;

        velX = (float) (Math.random() * 8 -2);
        velY = (float) (Math.random() * 8 -2);

        size = 5;
        maxLife = 15;
        life = maxLife;
    }

    @Override
    public void update() {

        x += velX + Math.sin(life * 0.2) * 0.5;
        y += velY;

        life--;

        if (life <= 0) destroy();
    }

    @Override
    public void draw(Graphics g) {

        int alpha = (int) (255 * ( (float) life / maxLife));

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(255, 255, 0, alpha));
        g2d.fillOval((int) x, (int) y, size, size);
    }

}
