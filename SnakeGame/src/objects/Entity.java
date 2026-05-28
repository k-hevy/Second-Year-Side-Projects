package objects;

import java.awt.Graphics;

public abstract class Entity {

    protected boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void destroy () {
        active = false;
    }

    public abstract void update();
    public abstract void draw(Graphics g);

}
