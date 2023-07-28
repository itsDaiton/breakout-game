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

    public Brick(double x, double y, double w, double h, Color color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
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

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
