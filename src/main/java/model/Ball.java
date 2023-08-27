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
        if (x <= r || x >= Settings.CANVAS_WIDTH - r) {
            dirX *= -1;
        }
        if (y - Settings.TOP_OFFSET <= r) {
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

        boolean collisionTop = ballBottom >= brickTop && ballLeft <= brickRight - Settings.BRICK_OFFSET && ballRight >= brickLeft + Settings.BRICK_OFFSET && ballBottom <= brickBottom;
        boolean collisionLeft = ballRight >= brickRight && ballTop >= brickBottom + Settings.BRICK_OFFSET && ballBottom <= brickTop - Settings.BRICK_OFFSET && ballRight <= brickRight;
        boolean collisionBottom = ballTop <= brickBottom && ballLeft <= brickRight - Settings.BRICK_OFFSET && ballRight >= brickLeft + Settings.BRICK_OFFSET && ballTop >= brickTop;
        boolean collisionRight = ballLeft <= brickRight && ballTop >= brickBottom + Settings.BRICK_OFFSET && ballBottom <= brickTop - Settings.BRICK_OFFSET && ballLeft >= brickLeft;

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
        for (Brick[] value : bricks) {
            for (Brick brick : value) {
                if (checkBrick(brick)) {
                    brick.setDestroyed(true);
                    Stats.increaseScore(brick.getScore());
                }
            }
        }
    }

    public void reset() {
        x = Math.random() * (Settings.CANVAS_WIDTH - 2 * r) + r;
        y = Settings.CANVAS_HEIGHT / 2;
        dirX = new Random().nextBoolean() ? -1 : 1;
        dirY = 1;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }
}
