package model;

public class Settings {

    private static Settings settings;
    public static final double WINDOW_WIDTH = 960;
    public static final double WINDOW_HEIGHT = 550;
    public static final double BALL_RADIUS = 8;
    public static final double BALL_SPEED = 2.5;
    public static final double PADDLE_WIDTH = 120;
    public static final double PADDLE_HEIGHT = 15;
    public static final double PADDLE_OFFSET = 40;
    public static final double BRICK_WIDTH = 75;
    public static final double BRICK_HEIGHT = 20;
    public static final double BRICK_OFFSET = 4.5;
    public static final int BRICK_ROWS = 12;
    public static final int BRICK_COLUMNS = 10;

    private Settings() {

    }

    private static Settings getInstance() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }
}
