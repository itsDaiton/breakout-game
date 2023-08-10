package model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class  Game {

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Brick[][] bricks;
    private Paddle paddle;
    private Ball ball;
    private boolean gameStarted = false;
    private boolean isPaused = false;
    private Timeline gameLoop;

    public void setUp(Stage stage) {
        createCanvas();
        createBall();
        createPaddle();
        createBricks();
        CanvasRenderer.drawOpeningText(graphicsContext, gameStarted);

        Font.loadFont(getClass().getResourceAsStream("Poppins-Regular.ttf"), 18);

        Group group = new Group(canvas);
        Scene scene = new Scene(group, Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();

        addMouseHandler(scene);
        addKeyHandler(scene);
        startGame();
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
        if (gameStarted) {
            ball.draw(graphicsContext);
        }
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
        bricks = new Brick[Settings.BRICK_COLUMNS][Settings.BRICK_ROWS];
        Color[] colors = CanvasRenderer.getColors();

        for (int i = 0; i < Settings.BRICK_COLUMNS; i++) {
            for (int j = 0; j < Settings.BRICK_ROWS; j++) {
                double x = Settings.BRICK_OFFSET + i * (Settings.BRICK_WIDTH + Settings.BRICK_OFFSET);
                double y = Settings.BRICKS_GRID_OFFSET + Settings.BRICK_OFFSET + j * (Settings.BRICK_HEIGHT + Settings.BRICK_OFFSET);
                bricks[i][j] = new Brick(x, y, Settings.BRICK_WIDTH, Settings.BRICK_HEIGHT, colors[j], j);
                bricks[i][j].draw(graphicsContext);
            }
        }
    }

    private void addMouseHandler(Scene scene) {
        scene.setOnMouseMoved(e -> {
            if (gameStarted && !isPaused) {
                paddle.move(e.getX());
                redraw();
            }
        });
    }

    private void addKeyHandler(Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && !gameStarted) {
                gameStarted = true;
                resetGame();
                stopGameLoop();
                redraw();
                startGame();
            }
            if (e.getCode() == KeyCode.P && gameStarted) {
                isPaused = !isPaused;
                redraw();
            }
        });
    }

    private void redraw() {
        graphicsContext.clearRect(0, 0, Settings.CANVAS_WIDTH, Settings.CANVAS_HEIGHT);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, Settings.CANVAS_WIDTH, Settings.CANVAS_HEIGHT + Settings.TOP_OFFSET);
        paddle.draw(graphicsContext);
        ball.draw(graphicsContext);

        for (int i = 0; i < Settings.BRICK_COLUMNS; i++) {
            for (int j = 0; j < Settings.BRICK_ROWS; j++) {
                Brick brick = bricks[i][j];
                if (!brick.isDestroyed()) {
                    bricks[i][j].draw(graphicsContext);
                }
            }
        }
        CanvasRenderer.drawGameState(graphicsContext);

        if (isPaused) {
            CanvasRenderer.drawPauseScreen(graphicsContext);
        }
    }

    private void startGame() {
        if (gameStarted) {
            gameLoop = new Timeline(new KeyFrame(Duration.millis(10), e -> updateGame()));
            gameLoop.setCycleCount(Timeline.INDEFINITE);
            gameLoop.play();
        }
    }

    private void updateGame() {
        if (gameStarted && !isPaused)  {
            ball.move();
            ball.collideWall();
            ball.collidePaddle(paddle.getX(), paddle.getY(), paddle.getW(), paddle.getH());
            ball.collideBrick(bricks);
            redraw();

            if (ball.getY() + ball.getR() >= Settings.CANVAS_HEIGHT) {
                Stats.decreaseLives();
                ball.reset();
            }

            if (Stats.getLives() <= 0) {
                endGame();
            }
         }
    }

    private void endGame() {
        gameStarted = false;
        CanvasRenderer.drawEndScreen(graphicsContext);
    }

    private void resetGame() {
        ball.reset();
        for (Brick[] brick : bricks) {
            for (Brick value : brick) {
                value.setDestroyed(false);
            }
        }
        Stats.reset();
    }

    private void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
}
