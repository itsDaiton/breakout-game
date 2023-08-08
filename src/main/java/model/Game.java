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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Game {

    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private Brick[][] bricks;
    private Paddle paddle;
    private Ball ball;
    private boolean gameStarted = false;
    private boolean isPaused = false;

    public void setUp(Stage stage) {
        createCanvas();
        createBall();
        createPaddle();
        createBricks();
        getColors();
        drawOpeningText();

        Font.loadFont(getClass().getResourceAsStream("Poppins-Regular.ttf"), 18);

        Group group = new Group(canvas);
        Scene scene = new Scene(group, Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();

        addMouseHandler(scene);
        addKeyHandler(scene);
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

        for (int i = 0; i < Settings.BRICK_ROWS; i++) {
            for (int j = 0; j < Settings.BRICK_COLUMNS; j++) {
                Brick brick = bricks[i][j];
                if (!brick.isDestroyed()) {
                    bricks[i][j].draw(graphicsContext);
                }
            }
        }
        drawGameState();

        if (isPaused) {
            drawOverlay();
        }
    }

    private void startGame() {
        if (gameStarted) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> updateGame()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
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
                resetGame();
            }
        }
    }

    private void resetGame() {
        ball.reset();
        for (Brick[] brick : bricks) {
            for (Brick value : brick) {
                value.setDestroyed(false);
            }
        }
        Stats.decreaseLives();
    }

    private void drawGameState() {
        graphicsContext.setFont(getFont());
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("SCORE " + Stats.getScore(), 20, 40);
        graphicsContext.fillText("LIVES " + Stats.getLives(), Settings.CANVAS_WIDTH - 110, 40);
    }

    private void drawOpeningText() {
        if (!gameStarted) {
            graphicsContext.setFont(getFont());
            graphicsContext.setFill(Color.WHITE);
            drawTexts("BREAKOUT GAME", "Press 'ENTER' to start.");
        }
    }

    private void drawOverlay() {
        graphicsContext.setFill(Color.rgb(0, 0, 0, 0.6));
        graphicsContext.fillRect(0, 0, Settings.CANVAS_WIDTH, Settings.CANVAS_HEIGHT + Settings.TOP_OFFSET);
        graphicsContext.setFont(getFont());
        graphicsContext.setFill(Color.WHITE);
        drawTexts("PAUSED", "Press 'P' to resume.");
    }

    private void drawTexts(String s1, String s2) {
        Text textTop = new Text(s1);
        textTop.setFont(getFont());

        Text textBottom = new Text(s2);
        textBottom.setFont(getFont());

        double textTopWidth = textTop.getLayoutBounds().getWidth();
        double textBottomWidth = textBottom.getLayoutBounds().getWidth();

        double centerX = Settings.CANVAS_WIDTH / 2;
        double centerY = (Settings.CANVAS_HEIGHT + Settings.TOP_OFFSET) / 2;

        double textTopX = centerX - textTopWidth / 2;
        double textTopY = centerY + 50;

        double textBottomX = centerX - textBottomWidth / 2;
        double textBottomY = centerY + 120;

        graphicsContext.fillText(s1, textTopX, textTopY);
        graphicsContext.fillText(s2, textBottomX, textBottomY);
    }

    private Font getFont() {
        return Font.font("Poppins", FontWeight.NORMAL, 28);
    }
}
