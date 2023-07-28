package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Game {

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Brick[][] bricks;
    private Paddle paddle;
    private Ball ball;
    private static final double WINDOW_WIDTH = 960;
    private static final double WINDOW_HEIGHT = 550;

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

        addMouseHandler(scene);
        startGame();
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
        double r = 8;
        double x = Math.random() * (WINDOW_WIDTH - 2 * r) + r;
        double y = WINDOW_HEIGHT / 2;
        double dir = new Random().nextBoolean() ? -1 : 1;
        ball = new Ball(x, y, r, dir, Color.WHITE);
        ball.draw(graphicsContext);
    }

    private void createPaddle() {
        double w = 120;
        double h = 40;
        paddle = new Paddle((WINDOW_WIDTH / 2) - (w / 2), WINDOW_HEIGHT - h, w, 15, Color.WHITE);
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

    private void addMouseHandler(Scene scene) {
        scene.setOnMouseMoved(e -> {
            paddle.move(e.getX(), WINDOW_WIDTH);
            redraw();
        });

    }

    private void redraw() {
        graphicsContext.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        paddle.draw(graphicsContext);
        ball.draw(graphicsContext);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 10; j++) {
                Brick brick = bricks[i][j];
                if (!brick.isDestroyed()) {
                    bricks[i][j].draw(graphicsContext);
                }
            }
        }
    }

    private void startGame() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> updateGame()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateGame() {
        ball.move();
        ball.collideWall(WINDOW_WIDTH, WINDOW_HEIGHT);
        ball.collidePaddle(paddle.getX(), paddle.getY(), paddle.getW(), paddle.getH());
        ball.collideBrick(bricks);
        redraw();
    }
}
