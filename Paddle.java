package brickbreaker;

import java.awt.event.*;
import java.awt.*;

public class Paddle extends Rectangle {

    int xVelocity;
    int speed = 22;

    Paddle(int WIDTH, int HEIGHT, int pWidth, int pHeight) {
        super(WIDTH / 2 - 50 , HEIGHT - 40, pWidth, pHeight);
    }

    public void keyPressed(char e) {
        if (e == 'a')
        {
            setXDirection(-speed);
        }
        if (e == 'd')
        {
            setXDirection(speed);
        }
    }

    public void keyReleased(char e) {
        if (e == 'a' || e == 'd')
        {
            setXDirection(0);
        }
    }

    public void setXDirection(int xDirection) {
        xVelocity = xDirection;
    }

    public void move() {
        x += xVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(x, y, width, height);
    }
}
