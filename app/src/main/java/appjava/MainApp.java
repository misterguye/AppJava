package appjava;

import java.util.*;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;

public class MainApp extends Application {
    double height = 915;
    double width = 412;
    int scoreCount = 0;

    ImageView myGhiniBox;
    ImageView playerView;

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane(); // No Canvas needed

        // --- Player ImageView ---
        Image playerImage = new Image(getClass().getResourceAsStream("/rwew-removebg-preview.png"));
        playerView = new ImageView(playerImage);
        playerView.setFitWidth(250);
        playerView.setFitHeight(500);
        playerView.setLayoutX(150);
        playerView.setLayoutY(200);

        // --- Obstacle ImageView ---
        Image ghiniImage = new Image(getClass().getResourceAsStream("/lamborghini.png"));
        myGhiniBox = new ImageView(ghiniImage);
        myGhiniBox.setFitWidth(300);
        myGhiniBox.setFitHeight(250);
        myGhiniBox.setLayoutX(300);
        myGhiniBox.setLayoutY(300);

        // --- Score Label ---
        Label score = new Label("Score: 0");
        score.setLayoutX(275);
        score.setLayoutY(20);
        score.setFont(new Font("Arial", 24));
        score.setTextFill(Color.RED);

        // --- Jump Button ---
        Circle circle = new Circle(40);
        circle.setFill(Color.LIGHTGRAY);
        Label jumpLabel = new Label("Jump");
        StackPane jump = new StackPane(circle, jumpLabel);
        jump.setLayoutX(20);
        jump.setLayoutY(700);

        // Add everything to root (no canvas)
        root.getChildren().addAll(myGhiniBox, playerView, score, jump);

        Player tung = new Player(playerView);

        
        jump.setOnMousePressed(e -> {
            if (tung.canJump) {
                tung.isJump();
                tung.canJump = false;
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            int counter = 0;
            @Override
            public void handle(long now) {
                scoreCount++;
                score.setText("Score: " + scoreCount);

                tung.update(); // No gc needed

                // Collision check — now works correctly!
                Bounds playerBounds = playerView.getBoundsInParent();
                Bounds oBounds = myGhiniBox.getBoundsInParent();

                
                if (playerBounds.intersects(oBounds)) {
                    counter++;
                    System.out.println("Collision detected at (" + playerBounds.getCenterX() + "," + playerBounds.getCenterY() + ") and [" + oBounds.getCenterX() + "," + oBounds.getCenterY() + "] " + counter);
                    // e.g. timer.stop(), show game over screen, etc.
                }
            }
        };
        timer.start();

        Scene scene = new Scene(root, width, height);
        stage.setTitle("JavaFX and Gradle");
        stage.setScene(scene);
        stage.show();
    }

    public class Player {
        double yVel = 0;
        double xVel = 2;
        double xImage;
        double yImage;
        final double gravity = 1;
        boolean canJump = true;
        ImageView view; // reference to the ImageView in the scene

        public Player(ImageView view) {
            this.view = view;
            this.xImage = view.getLayoutX();
            this.yImage = view.getLayoutY();
        }

        public void isJump() {
            yVel = -20;
        }

        public void update() {
            xImage += xVel / 10;

            if (xImage > width / 2) {
                xImage = -xImage;
            }

            yVel += gravity;
            yImage += yVel;

            if (yImage >= 200) {
                yImage = 200;
                yVel = 0;
                canJump = true;
            }

            // Move the ImageView — this is what makes collision work
            view.setLayoutX(xImage);
            view.setLayoutY(yImage);
        }
    }
}