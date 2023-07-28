package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Ball {

    private double x;
    private double y;
    private double r;
    private double dirX;
    private double dirY;
    private Color color;

    public Ball(double x, double y, double r, double dir, Color color) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.dirX = dir;
        this.dirY = 1;
        this.color = color;
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(color);
        graphicsContext.fillOval(x - r, y - r, 2 * r, 2 * r);
    }

    public void move() {
        x += dirX * Settings.BALL_SPEED;
        y += dirY * Settings.BALL_SPEED;
    }

    public void collideWall() {
        if (x <= r || x >= Settings.WINDOW_WIDTH - r) {
            dirX *= -1;
        }
        if (y <= r) {
            dirY = 1;
        }
        if (y >= Settings.WINDOW_HEIGHT - r) {
            x = Math.random() * (Settings.WINDOW_WIDTH - 2 * r) + r;
            y = Settings.WINDOW_HEIGHT / 2;
            dirX = new Random().nextBoolean() ? -1 : 1;
            dirY = 1;
        }
    }

    public void collidePaddle(double paddleX, double paddleY, double paddleWidth, double paddleHeight) {
        if (y + r >= paddleY && y <= paddleY + paddleHeight) {
            if (x + r >= paddleX && x <= paddleX + paddleWidth) {
                dirY = -1;
            }
        }
    }

    public boolean checkBrick(Brick brick) {
        if (brick.isDestroyed()) {
            return false;
        }

        double brickTop = brick.getY();
        double brickLeft = brick.getX();
        double brickBottom = brick.getY() + brick.getH();
        double brickRight = brick.getX() + brick.getW();

        double ballTop = y - r;
        double ballLeft = x - r;
        double ballBottom = y + r;
        double ballRight = x + r;

        boolean collisionTop = ballBottom >= brickTop && ballLeft <= brickRight - 4.5 && ballRight >= brickLeft + 4.5 && ballBottom <= brickBottom;
        boolean collisionLeft = ballRight >= brickRight && ballTop >= brickBottom + 4.5 && ballBottom <= brickTop - 4.5 && ballRight <= brickRight;
        boolean collisionBottom = ballTop <= brickBottom && ballLeft <= brickRight - 4.5 && ballRight >= brickLeft + 4.5 && ballTop >= brickTop;
        boolean collisionRight = ballLeft <= brickRight && ballTop >= brickBottom + 4.5 && ballBottom <= brickTop - 4.5 && ballLeft >= brickLeft;

        boolean collided = collisionTop || collisionLeft || collisionBottom || collisionRight;

        if (collided) {
            if (collisionTop || collisionBottom) {
                dirY *= -1;
            }
            if (collisionLeft || collisionRight) {
                dirX *= -1;
            }
        }

        return collided;
    }

    public void collideBrick(Brick[][] bricks) {
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                Brick brick = bricks[i][j];
                if (checkBrick(brick)) {
                    brick.setDestroyed(true);
                }
            }
        }
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getDirX() {
        return dirX;
    }

    public void setDirX(double dirX) {
        this.dirX = dirX;
    }

    public double getDirY() {
        return dirY;
    }

    public void setDirY(double dirY) {
        this.dirY = dirY;
    }
}
