package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle {

    private double x;
    private double y;
    private double w;
    private double h;
    private Color color;

    public Paddle(double x, double y, double w, double h, Color color) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.color = color;
    }

    public void draw(GraphicsContext graphicsContext) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(x, y, w, h);
    }

    public void move(double mouseX) {
        double minX = 0;
        double maxX = Settings.CANVAS_WIDTH - w;
        double newX = Math.min(Math.max(mouseX - w / 2, minX), maxX);
        setX(newX);
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

    public double getH() {
        return h;
    }

    public double getW() {
        return w;
    }
}
