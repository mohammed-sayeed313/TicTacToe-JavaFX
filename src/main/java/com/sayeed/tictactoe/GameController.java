package com.sayeed.tictactoe;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameController {

    private final GameLogic game;
    private final Button[][] cells;

    private Label turnLabel;
    private Label statusLabel;
    private Label xScoreLabel;
    private Label oScoreLabel;
    private Label drawScoreLabel;

    private int xScore;
    private int oScore;
    private int drawScore;

    public GameController() {
        game = new GameLogic();
        cells = new Button[3][3];
    }

    public VBox createUI() {
        VBox root = new VBox(25);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(35));
        root.getStyleClass().add("root");

        VBox header = createHeader();
        HBox scoreboard = createScoreboard();
        GridPane board = createBoard();

        statusLabel = new Label("Make your move");
        statusLabel.getStyleClass().add("status-label");

        HBox controls = createControls();

        root.getChildren().addAll(
                header, scoreboard, board, statusLabel, controls
        );

        playEntranceAnimation(root);
        return root;
    }

    private VBox createHeader() {
        Label title = new Label("TIC • TAC • TOE");
        title.getStyleClass().add("title");

        Label subtitle = new Label("Classic strategy. Modern experience.");
        subtitle.getStyleClass().add("subtitle");

        turnLabel = new Label("PLAYER X • YOUR TURN");
        turnLabel.getStyleClass().add("turn-x");

        VBox header = new VBox(8);
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(title, subtitle, turnLabel);

        return header;
    }

    private HBox createScoreboard() {
        VBox xCard = createScoreCard("PLAYER X", "x-score");
        VBox drawCard = createScoreCard("DRAWS", "draw-score");
        VBox oCard = createScoreCard("PLAYER O", "o-score");

        xScoreLabel = (Label) xCard.getChildren().get(1);
        drawScoreLabel = (Label) drawCard.getChildren().get(1);
        oScoreLabel = (Label) oCard.getChildren().get(1);

        HBox box = new HBox(15);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(xCard, drawCard, oCard);
        return box;
    }

    private VBox createScoreCard(String title, String scoreStyle) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("score-title");

        Label score = new Label("0");
        score.getStyleClass().addAll("score-value", scoreStyle);

        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(145, 90);
        card.getStyleClass().add("score-card");
        card.getChildren().addAll(titleLabel, score);

        return card;
    }

    private GridPane createBoard() {
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(12);
        board.setVgap(12);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button cell = createCell(row, col);
                cells[row][col] = cell;
                board.add(cell, col, row);
            }
        }

        return board;
    }

    private Button createCell(int row, int col) {
        Button cell = new Button();
        cell.setPrefSize(150, 150);
        cell.setFocusTraversable(false);
        cell.getStyleClass().add("game-cell");

        cell.setOnMouseEntered(event -> {
            if (!game.isGameOver()
                    && game.getCell(row, col).equals(GameLogic.EMPTY)) {
                cell.getStyleClass().add("game-cell-hover");
                animateScale(cell, 1.04);
            }
        });

        cell.setOnMouseExited(event -> {
            cell.getStyleClass().remove("game-cell-hover");
            animateScale(cell, 1.0);
        });

        cell.setOnAction(event -> playMove(row, col));
        return cell;
    }

    private void playMove(int row, int col) {
        if (game.isGameOver()) {
            return;
        }

        String player = game.getCurrentPlayer();

        if (!game.makeMove(row, col)) {
            return;
        }

        updateCell(cells[row][col], player);

        int[][] winningCells = game.getWinningCells();

        if (winningCells != null) {
            game.setGameOver(true);
            updateWinnerScore(player);
            highlightWinner(winningCells);

            statusLabel.setText("PLAYER " + player + " WINS!");
            statusLabel.getStyleClass().removeAll(
                    "status-label", "draw-status", "winner-x", "winner-o"
            );
            statusLabel.getStyleClass().add(
                    player.equals("X") ? "winner-x" : "winner-o"
            );
            return;
        }

        if (game.isDraw()) {
            game.setGameOver(true);
            drawScore++;
            drawScoreLabel.setText(String.valueOf(drawScore));

            statusLabel.setText("IT'S A DRAW!");
            statusLabel.getStyleClass().removeAll(
                    "status-label", "winner-x", "winner-o"
            );
            statusLabel.getStyleClass().add("draw-status");
            return;
        }

        game.switchPlayer();
        updateTurnLabel();
    }

    private void updateCell(Button cell, String player) {
        Label symbol = new Label(player);
        symbol.getStyleClass().add(
                player.equals("X") ? "x-symbol" : "o-symbol"
        );

        cell.setGraphic(symbol);

        FadeTransition fade = new FadeTransition(
                Duration.millis(250), symbol
        );
        fade.setFromValue(0);
        fade.setToValue(1);

        ScaleTransition scale = new ScaleTransition(
                Duration.millis(300), symbol
        );
        scale.setFromX(0.2);
        scale.setFromY(0.2);
        scale.setToX(1);
        scale.setToY(1);

        new ParallelTransition(fade, scale).play();
    }

    private void updateTurnLabel() {
        String player = game.getCurrentPlayer();

        turnLabel.setText("PLAYER " + player + " • YOUR TURN");
        turnLabel.getStyleClass().removeAll("turn-x", "turn-o");
        turnLabel.getStyleClass().add(
                player.equals("X") ? "turn-x" : "turn-o"
        );
    }

    private void updateWinnerScore(String player) {
        if (player.equals("X")) {
            xScore++;
            xScoreLabel.setText(String.valueOf(xScore));
            animateScore(xScoreLabel);
        } else {
            oScore++;
            oScoreLabel.setText(String.valueOf(oScore));
            animateScore(oScoreLabel);
        }
    }

    private void highlightWinner(int[][] winningCells) {
        for (int[] position : winningCells) {
            Button cell = cells[position[0]][position[1]];
            cell.getStyleClass().add("winning-cell");

            ScaleTransition animation = new ScaleTransition(
                    Duration.millis(500), cell
            );
            animation.setFromX(1);
            animation.setFromY(1);
            animation.setToX(1.08);
            animation.setToY(1.08);
            animation.setAutoReverse(true);
            animation.setCycleCount(4);
            animation.play();
        }
    }

    private HBox createControls() {
        Button restart = new Button("RESTART ROUND");
        restart.getStyleClass().add("control-button");

        Button reset = new Button("RESET SCORES");
        reset.getStyleClass().add("control-button");

        restart.setOnAction(event -> restartRound());
        reset.setOnAction(event -> resetScores());

        HBox controls = new HBox(15);
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().addAll(restart, reset);

        return controls;
    }

    private void restartRound() {
        game.resetGame();

        turnLabel.setText("PLAYER X • YOUR TURN");
        turnLabel.getStyleClass().removeAll("turn-o");
        turnLabel.getStyleClass().add("turn-x");

        statusLabel.setText("Make your move");
        statusLabel.getStyleClass().removeAll(
                "winner-x", "winner-o", "draw-status"
        );
        statusLabel.getStyleClass().add("status-label");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button cell = cells[row][col];
                cell.setGraphic(null);
                cell.getStyleClass().removeAll(
                        "winning-cell", "game-cell-hover"
                );
                cell.setScaleX(1);
                cell.setScaleY(1);
            }
        }
    }

    private void resetScores() {
        xScore = 0;
        oScore = 0;
        drawScore = 0;

        xScoreLabel.setText("0");
        oScoreLabel.setText("0");
        drawScoreLabel.setText("0");

        restartRound();
    }

    private void animateScore(Label label) {
        ScaleTransition animation = new ScaleTransition(
                Duration.millis(350), label
        );
        animation.setFromX(0.7);
        animation.setFromY(0.7);
        animation.setToX(1.2);
        animation.setToY(1.2);
        animation.setAutoReverse(true);
        animation.setCycleCount(2);
        animation.play();
    }

    private void animateScale(Button button, double scale) {
        ScaleTransition animation = new ScaleTransition(
                Duration.millis(120), button
        );
        animation.setToX(scale);
        animation.setToY(scale);
        animation.play();
    }

    private void playEntranceAnimation(VBox root) {
        root.setOpacity(0);
        root.setTranslateY(25);

        FadeTransition fade = new FadeTransition(
                Duration.millis(700), root
        );
        fade.setToValue(1);

        TranslateTransition translate = new TranslateTransition(
                Duration.millis(700), root
        );
        translate.setToY(0);

        new ParallelTransition(fade, translate).play();
    }
}
