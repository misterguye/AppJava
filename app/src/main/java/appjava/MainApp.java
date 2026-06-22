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

        ImageView blueSky = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("/360_F_105600193_Bq7GrUHfRaaPQeoJW6m9kDWIVuXvPqHQ.jpg")));
        blueSky.setFitHeight(715);
        blueSky.setFitWidth(412);
        blueSky.setX(0);
        blueSky.setY(0);

        ImageView road = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("/empty-straight-road-marking-horizontal-260nw-2090504992.jpg")));
        road.setFitWidth(412);
        road.setFitHeight(200);
        road.setX(0);
        road.setY(750);

        // --- Player ImageView ---

        Image playerImage = new Image(getClass().getResourceAsStream("/tung_tungsahur.png"));

        playerView = new ImageView(playerImage);
        playerView.setFitWidth(100);
        playerView.setFitHeight(300);
        playerView.setLayoutX(0);
        playerView.setLayoutY(200);

        // --- Obstacle ImageView ---
        Image ghiniImage = new Image(getClass().getResourceAsStream("/4170d4343f6a626b60450f27f097d3fd-removebg-preview.png"));
        myGhiniBox = new ImageView(ghiniImage);
        myGhiniBox.setFitHeight(50);
        myGhiniBox.setFitWidth(100);
        myGhiniBox.setLayoutX(300);
        myGhiniBox.setLayoutY(600);

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

        Label gameOver = new Label("GAME OVER");
        Font gameOverFont = new Font("Arial", 32);
        gameOver.setFont(gameOverFont);
        gameOver.setLayoutX(110);
        gameOver.setLayoutY(425);

        Rectangle rec = new Rectangle(250, 100);

        rec.setX((width - 250) /2);
        rec.setY((height - 100) /2);
        // Add everything to root (no canvas)
        root.getChildren().addAll(blueSky, road, myGhiniBox, playerView, score, jump, rec, gameOver);

        Player tung = new Player(playerView);



        jump.setOnMousePressed(e -> {
            if (tung.canJump) {
                tung.isJump();
                tung.canJump = false;
            }
        });
        AnimationTimer timer;
         timer = new AnimationTimer() {
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
                        this.stop();

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
        public double ghiniX = 300;
        ImageView view; // reference to the ImageView in the scene

        public Player(ImageView view) {
            this.view = view;
            this.xImage = view.getLayoutX();
            this.yImage = view.getLayoutY();
        }

        public void isJump() {
            yVel = -25;
        }

        public void update() {
            if(ghiniX < -400) {
                ghiniX = 500;
            }

            ghiniX-= (scoreCount/250) + 7;
            myGhiniBox.setX(ghiniX);

            if (xImage > width / 2) {
                xImage = -xImage;
            }

            yVel += gravity;
            yImage += yVel;

            if (yImage >= 400) {
                yImage = 400;
                yVel = 0;
                canJump = true;
            }

            // Move the ImageView — this is what makes collision work
            view.setLayoutX(xImage);
            view.setLayoutY(yImage);
        }
    }

}