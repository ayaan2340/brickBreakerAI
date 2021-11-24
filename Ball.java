package brickbreaker;

import java.awt.*;
import java.util.*;

public class Ball extends Rectangle {
    int xVelocity;
    int yVelocity;
    int speed = 2;
    Random random;

    Ball(int width, int height, int DIAMETER) {
        super((width / 2) - (DIAMETER / 2), (height) / 2, DIAMETER, DIAMETER);
        random = new Random();
        
        int randomXDirection = random.nextInt(2) + 1;
        if (randomXDirection <= 1) 
        {
            randomXDirection = -randomXDirection;
        }
        else
        {
            randomXDirection -= 1;
        }
        
        int randomYDirection = random.nextInt(2) + 1;
        if (randomYDirection <= 1) 
        {
            randomYDirection = -randomYDirection;
        }
        else
        {
            randomYDirection -= 1;
        }
        setXDirection(randomXDirection * speed);
        setYDirection(randomYDirection * speed);
    }

    public void setXDirection(int xDirection) {
        xVelocity = xDirection;
    }
    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(x, y, width, height);
    }
}
