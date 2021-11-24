package brickbreaker;

import java.awt.*;
public class Score extends Rectangle {
    int score;
    int width; 
    int height;
    Score (int WIDTH, int HEIGHT)
    {
        width = WIDTH;
        height = HEIGHT;
    }
    
    public void draw(Graphics g)
    {
        g.setColor(Color.blue);
        g.fillRect(width - 150, height - 150, 120, 60);

        g.setColor(Color.white);
        g.setFont(new Font("Consolas", Font.PLAIN, 60));
        g.drawString(String.valueOf(score / 100) + String.valueOf((score / 10) % 10) + String.valueOf(score % 10), width - 140, height - 100);
    }
}
