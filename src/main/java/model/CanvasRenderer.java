package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CanvasRenderer {

    public static void drawOpeningText(GraphicsContext graphicsContext, boolean gameStarted) {
        if (!gameStarted) {
            graphicsContext.setFont(getFont());
            graphicsContext.setFill(Color.WHITE);
            drawTexts(
                    graphicsContext,
                    "Destroy all bricks to win the game.",
                    "Press 'ENTER' to play.",
                    null
            );
        }
    }
    public static void drawGameState(GraphicsContext graphicsContext) {
        graphicsContext.setFont(getFont());
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillText("SCORE " + Stats.getScore(), 20, 40);
        graphicsContext.fillText("LIVES " + Stats.getLives(), Settings.CANVAS_WIDTH - 110, 40);
    }

    public static void drawEndScreen(GraphicsContext graphicsContext) {
        drawOverlay(graphicsContext);
        drawTexts(
                graphicsContext,
                "GAME OVER",
                "Score: " + Stats.getScore(),
                "Press 'Enter' to play again."
        );
    }

    public static void drawPauseScreen(GraphicsContext graphicsContext) {
        drawOverlay(graphicsContext);
        drawTexts(
                graphicsContext,
                "PAUSED",
                "Press 'P' to resume.",
                null
        );
    }

    public static void drawOverlay(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.rgb(0, 0, 0, 0.6));
        graphicsContext.fillRect(0, 0, Settings.CANVAS_WIDTH, Settings.CANVAS_HEIGHT + Settings.TOP_OFFSET);
        graphicsContext.setFont(getFont());
        graphicsContext.setFill(Color.WHITE);
    }

    public static void drawTexts(GraphicsContext graphicsContext, String s1, String s2, String s3) {
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

        if (s3 != null && !s3.isEmpty()) {
            Text textThird = new Text(s3);
            textThird.setFont(getFont());

            double textThirdWidth = textThird.getLayoutBounds().getWidth();

            double textThirdX = centerX - textThirdWidth / 2;
            double textThirdY = centerY + 190;

            graphicsContext.fillText(s3, textThirdX, textThirdY);
        }
    }

    public static Color[] getColors() {
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

    private static Font getFont() {
        return Font.font("Poppins", FontWeight.NORMAL, 28);
    }
}
