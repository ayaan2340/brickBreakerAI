package brickbreaker;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GameJPanel extends JPanel implements Runnable
{
    final public int WIDTH = 1000;
    final public int HEIGHT = 700;
    final int BRICK_ROWS = 13;
    final int BRICK_COLUMNS = 6;
    Thread gameThread;
    Image image;
    Ball ball;
    Paddle paddle;
    Score score;
    Brick[][] bricks = new Brick[BRICK_ROWS][BRICK_COLUMNS];
    Graphics graphics;
    LinkedList<Brick> lastHit = new LinkedList<Brick>();
    Boolean AIon = false;
    Boolean gameOver = false;
    final int PADDLE_WIDTH = 100;
    final int PADDLE_HEIGHT = 20;
    final int BALL_DIAMETER = 20;


    GameJPanel() {
        paddle = new Paddle(WIDTH, HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
        ball = new Ball(WIDTH, HEIGHT, BALL_DIAMETER);
        newBricks();
        score = new Score(WIDTH, HEIGHT);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.addKeyListener(new AL());
        this.setFocusable(true);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBricks() {
        for (int i = 0; i < BRICK_ROWS; i++)
        {
            for (int j = 0; j < BRICK_COLUMNS; j++)
            {
                bricks[i][j] = new Brick((i * 70) + 50, (j * 40) + 20, 60, 30, String.valueOf(i) + String.valueOf(j));
            }
        }
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0 , 0, this);
    }

    public void draw(Graphics g) {
        if (!gameOver)
        {
            paddle.draw(g);
            ball.draw(g);
            score.draw(g);

            for (int i = 0; i < BRICK_ROWS; i++)
            {
                for (int j = 0; j < BRICK_COLUMNS; j++)
                {
                    if(bricks[i][j].visible)
                        bricks[i][j].draw(g);
                }
            }
        }
        else 
        {
            gameOver();
            gameThread.stop();
        }
    }

    public void gameOver() 
    {
        graphics.clearRect(0, 0, WIDTH, HEIGHT);
        graphics.setColor(Color.red);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        score.draw(graphics);
    }

    public void move() {
        paddle.move();
        ball.move();
    }

    public void checkCollisions() {
        if (ball.y + ball.height >= HEIGHT)
            gameOver = true;
        if (paddle.x < 0)
            paddle.x = 0;

        if (paddle.x > WIDTH-PADDLE_WIDTH)
            paddle.x = WIDTH-PADDLE_WIDTH;

        if (ball.x < 0)
            ball.setXDirection(-ball.xVelocity);;
        
        if (ball.x > WIDTH-BALL_DIAMETER)
            ball.setXDirection(-ball.xVelocity);
        
        if (ball.y < 0)
            ball.setYDirection(-ball.yVelocity);

        if (ball.y > HEIGHT-BALL_DIAMETER)
            ball.setYDirection(-ball.yVelocity);

        if (ball.intersects(paddle)) {
            ball.yVelocity = Math.abs(ball.yVelocity);
            if (ball.xVelocity > 0 && Math.abs(ball.xVelocity) < 30)
                ball.xVelocity += Math.log(2 * ball.xVelocity);
            if (ball.yVelocity > 0 && ball.yVelocity < 30)
                ball.yVelocity += Math.log(2 * ball.yVelocity);

            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(-ball.yVelocity);
        }

        for (int i = 0; i < BRICK_ROWS; i++)
        {
            for (int j = 0; j < BRICK_COLUMNS; j++)
            {
                if (ball.intersects(bricks[i][j]) && bricks[i][j].visible)
                {
                    score.score++;
                    if (((ball.x + ball.width >= bricks[i][j].x) || (ball.x <= bricks[i][j].x + bricks[i][j].width)) && (ball.y >= bricks[i][j].y) && (ball.y + ball.height <= bricks[i][j].y + bricks[i][j].height))
                    {
                       ball.setXDirection(-ball.xVelocity);
                    }
                    if (((ball.y + ball.height >= bricks[i][j].y || ball.y <= bricks[i][j].y + bricks[i][j].height) && (ball.x >= bricks[i][j].x) && (ball.x + ball.width <= bricks[i][j].x + bricks[i][j].width)))
                    {
                       ball.setYDirection(-ball.yVelocity);
                    }

                    lastHit.addFirst(bricks[i][j].hit());
                    for (int h = 25; h < lastHit.size(); h++)
                    {
                        Brick restoreBrick = lastHit.get(h);
                        restoreBrick.visible = true;
                        lastHit.remove(h);
                    }
                }
            }
        }
    }

    public void runAI()
    {
        if (ball.x + BALL_DIAMETER / 2 + ball.xVelocity * 2 > paddle.x + paddle.width / 2)
        {
            paddle.keyPressed('d');
        }
        else if (ball.x + BALL_DIAMETER / 2  + ball.xVelocity * 2 < paddle.x + paddle.width / 2)
        {
            paddle.keyPressed('a');
        }
        else
        {
            paddle.keyReleased('d');
            paddle.keyReleased('a');
        }
    }
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1) {
                move();
                if (AIon)
                {
                    runAI();
                }
                checkCollisions();
                repaint();
                delta--;
            }
        }
    }

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e)
        {
            paddle.keyPressed(e.getKeyChar());
            if (e.getKeyChar() == 'g') {AIon = !AIon;}   
        }
        public void keyReleased(KeyEvent e)
        {
            paddle.keyReleased(e.getKeyChar());
        }
    }
}
