package appjava;

import java.util.*;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MainApp extends Application {
    double screenHeight = 915;
    double screenWidth = 412;

    final int GRAVITY = -5;
    int gravity = GRAVITY;

    boolean canJump = false;
    double elapsedSeconds = 0;
    int scoreCount = 0;
    String conversion = "0";


    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane(); //allows the arrangement of things on the app
        Canvas canva = new Canvas(screenWidth, screenHeight); //creates grid
        GraphicsContext gc = canva.getGraphicsContext2D(); //allows creation of objects
        Player p = new Player(0,600);

        Label score = new Label("0"); //creates score
        score.setFont(new Font("Arial", 24));
        root.getChildren().add(score);

        //make it top right for the score
        score.setLayoutX(350);
        score.setLayoutY(20);

        AnimationTimer timer = new AnimationTimer() {


            long startTime = 0;

            @Override
            public void handle(long now) {
                if (startTime == 0) {
                    startTime = now;

                    return;
                }
                scoreCount++; //each frame, increment score by 1;
                conversion = Integer.toString(scoreCount);
                score.setText(conversion); //changes the more you play.
                p.update(gc);

                // elapsedSeconds = (now - startTime) / 1_000_000_000.0;

                // Update Loop


            }
        };
        timer.start();

        root.getChildren().add(canva);
        Scene scene = new Scene(root, screenWidth, screenHeight);


        // Showing Everything
        stage.setTitle("JavaFX and Gradle");
        stage.setScene(scene);
        stage.show();

    }

    public class Player {
        javafx.scene.image.Image playerImage;
        int yVel = 0;
        int xVel = 2; //always moving right;
        double xImage;
        double yImage;
        double pastX = 0;
        double pastY = 0;
        final int width = 200;
        final int height = 300;
        public Player (int xImage, int yImage){
            playerImage = new javafx.scene.image.Image(getClass().getResourceAsStream("/09417f97e1834efdba30f4619691a6e4-removebg-preview.png"));
            this.xImage = xImage;
            this.yImage = yImage;
        }

        public void update (GraphicsContext gc) {
            pastX = xImage; //clears old place where the obj was
            pastY = yImage;
            gc.clearRect(pastX,pastY, screenWidth, screenHeight);

            xImage = (xImage + 1) % (screenWidth);
          //obj always moving right
            gc.drawImage(playerImage, xImage, yImage, width, height);
        }
    }

        //classes

}