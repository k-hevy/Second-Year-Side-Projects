package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Particle extends Entity {

    private float x, y, velX, velY;
    private int size, life, maxLife, type;

    public Particle (float x, float y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;

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

        int r = 0, gr = 0, b = 0;

        switch (type) {
            case 0 : r = 255; gr = 0; b = 255;
                break; 
            case 1 : r = 255; gr = 0; b = 0;
                break;
            case 2 : r = 255; gr = 255; b = 255;
                break;
            case 3 : r = 255; gr = 255; b = 0;
                break;
        }

        int alpha = (int) (255 * ( (float) life / maxLife));

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(r, gr, b, alpha));
        g2d.fillOval((int) x, (int) y, size, size);
    }

}
