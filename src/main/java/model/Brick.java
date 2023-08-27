package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Brick {

    private double x;
    private double y;
    private double w;
    private double h;
    private Color color;
    private boolean destroyed = false;
    private int score;

    public Brick(double x, double y, double w, double h, Color color, int row) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
        this.score = Settings.BRICK_ROWS - row;
    }

    public void draw(GraphicsContext graphicsContext) {
        if (!destroyed) {
            graphicsContext.setFill(color);
            graphicsContext.fillRect(x, y, w, h);
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public int getScore() {
        return score;
    }
}
