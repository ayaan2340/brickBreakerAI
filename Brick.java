package brickbreaker;

import java.awt.*;
public class Brick extends Rectangle {
    boolean visible = true;
    String id;
    Brick(int x, int y, int width, int height, String num)
    {
        super(x, y, width, height);
        id = num;
    }

    public Brick hit() {
        visible = false;
        return this;
    }

    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(x, y, width, height);
    }
}
