package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Game {

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Brick[][] bricks;
    private Paddle paddle;
    private Ball ball;
    private int score = 0;
    private int lives = 3;

    public void setUp(Stage stage) {
        createCanvas();
        createBall();
        createPaddle();
        createBricks();
        getColors();

        Font.loadFont(getClass().getResourceAsStream("Poppins-Regular.ttf"), 18);

        Group group = new Group(canvas);
        Scene scene = new Scene(group, Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
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
        canvas = new Canvas(Settings.CANVAS_WIDTH, Settings.CANVAS_HEIGHT + Settings.TOP_OFFSET);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0,0, Settings.CANVAS_WIDTH, Settings.CANVAS_HEIGHT + Settings.TOP_OFFSET);
    }

    private void createBall() {
        double r = Settings.BALL_RADIUS;
        double x = Math.random() * (Settings.CANVAS_WIDTH - 2 * r) + r;
        double y = Settings.CANVAS_HEIGHT / 2;
        double dir = new Random().nextBoolean() ? -1 : 1;
        ball = new Ball(x, y, r, dir, Color.WHITE);
        ball.draw(graphicsContext);
    }

    private void createPaddle() {
        double w = Settings.PADDLE_WIDTH;
        double h = Settings.PADDLE_HEIGHT;
        double x = (Settings.CANVAS_WIDTH / 2) - (w / 2);
        double y = Settings.CANVAS_HEIGHT - Settings.PADDLE_OFFSET;
        paddle = new Paddle(x, y, w, h, Color.WHITE);
        paddle.draw(graphicsContext);
    }

    private void createBricks() {
        bricks = new Brick[Settings.BRICK_ROWS][Settings.BRICK_COLUMNS];
        Color[] colors = getColors();

        for (int i = 0; i < Settings.BRICK_ROWS; i++) {
            for (int j = 0; j < Settings.BRICK_COLUMNS; j++) {
                double x = Settings.BRICK_OFFSET + i * (Settings.BRICK_WIDTH + Settings.BRICK_OFFSET);
                double y = Settings.BRICKS_GRID_OFFSET + Settings.BRICK_OFFSET + j * (Settings.BRICK_HEIGHT + Settings.BRICK_OFFSET);
                bricks[i][j] = new Brick(x, y, Settings.BRICK_WIDTH, Settings.BRICK_HEIGHT, colors[j]);
                bricks[i][j].draw(graphicsContext);
            }
        }
    }

    private void addMouseHandler(Scene scene) {
        scene.setOnMouseMoved(e -> {
            paddle.move(e.getX());
            redraw();
        });
    }

    private void redraw() {
        graphicsContext.clearRect(0, 0, Settings.CANVAS_WIDTH, Settings.CANVAS_HEIGHT);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, Settings.CANVAS_WIDTH, Settings.CANVAS_HEIGHT + Settings.TOP_OFFSET);

        paddle.draw(graphicsContext);
        ball.draw(graphicsContext);
        for (int i = 0; i < Settings.BRICK_ROWS; i++) {
            for (int j = 0; j < Settings.BRICK_COLUMNS; j++) {
                Brick brick = bricks[i][j];
                if (!brick.isDestroyed()) {
                    bricks[i][j].draw(graphicsContext);
                }
            }
        }
        drawGameState();
    }

    private void startGame() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> updateGame()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateGame() {
        ball.move();
        ball.collideWall();
        ball.collidePaddle(paddle.getX(), paddle.getY(), paddle.getW(), paddle.getH());
        ball.collideBrick(bricks);
        redraw();

        if (ball.getY() + ball.getR() >= Settings.CANVAS_HEIGHT) {
            resetGame();
        }
    }

    private void resetGame() {
        ball.reset();
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                bricks[i][j].setDestroyed(false);
            }
        }
    }

    private void drawGameState() {
        Font fontPoppins = Font.font("Poppins", FontWeight.NORMAL, 28);
        graphicsContext.setFont(fontPoppins);
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("SCORE " + score, 20, 40);
        graphicsContext.fillText("LIVES " + lives, Settings.CANVAS_WIDTH - 110, 40);
    }
}
