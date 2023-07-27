package model;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game {

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Brick[][] bricks;
    private static final double WINDOW_WIDTH = 960;
    private static final double WINDOW_HEIGHT = 500;

    public void setUp(Stage stage) {
        createCanvas();
        createBall();
        createPaddle();
        createBricks();
        getColors();

        Group group = new Group(canvas);
        Scene scene = new Scene(group, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public Color[] getColors() {
        return new Color[]{
                Color.RED,
                Color.CORAL,
                Color.ORANGE,
                Color.YELLOW,
                Color.GREENYELLOW,
                Color.GREEN,
                Color.AQUAMARINE,
                Color.DEEPSKYBLUE,
                Color.BLUE,
                Color.PURPLE
        };
    }

    private void createCanvas() {
        canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0,0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    private void createBall() {
        Ball ball = new Ball(WINDOW_WIDTH / 2,WINDOW_HEIGHT - 120,8, Color.WHITE);
        ball.draw(graphicsContext);
    }

    private void createPaddle() {
        Paddle paddle = new Paddle(WINDOW_WIDTH / 2, WINDOW_HEIGHT - 40, 150, 20, Color.WHITE);
        paddle.draw(graphicsContext);
    }

    private void createBricks() {
        bricks = new Brick[12][10];
        double w = 75;
        double h = 20;
        double offset = 4.5;
        Color[] colors = getColors();

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 10; j++) {
                double x = offset + i * (w + offset);
                double y = offset + j * (h + offset);
                bricks[i][j] = new Brick(x, y, w, h, colors[j]);
                bricks[i][j].draw(graphicsContext);
            }
        }
    }
}
