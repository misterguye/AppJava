package appjava;

import java.util.*;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
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
    double height = 915;
    double width = 412;
    double elapsedSeconds = 0;
    int scoreCount = 0;
    String conversion = "0";


    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane(); //allows the arrangement of things on the app
        Canvas canva = new Canvas(screenWidth, screenHeight); //creates grid
        GraphicsContext gc = canva.getGraphicsContext2D(); //allows creation of objects
        Player p = new Player(0,600);
        Canvas canva = new Canvas(width, height); //creates grid
        GraphicsContext gc = canva.getGraphicsContext2D(); //allows creation of objects
        Player tung = new Player(0,200);

        Label score = new Label("0"); //creates score
        score.setFont(new Font("Arial", 24));
        Button jump = new Button("Jump");
        jump.setOnAction(e -> {
            if(tung.canJump){
                tung.isJump();
                tung.canJump = false;
            }
                });
        root.getChildren().add(canva);
        root.getChildren().add(score);
        root.getChildren().add(jump);
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
                tung.update(gc);

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
        int xImage;
        int yImage;
        int pastX = 0;
        int pastY = 0;
        final int width1 = 300;
        final int height1 = 500;
        final int gravity = 1;
        boolean canJump = true;
        public Player (int xImage, int yImage){
            playerImage = new javafx.scene.image.Image(getClass().getResourceAsStream("/09417f97e1834efdba30f4619691a6e4-removebg-preview.png"));
            this.xImage = xImage;
            this.yImage = yImage;
        }
        public void isJump (){
            yVel = -22; //makes yImage go up by 20;

        }
        public void update (GraphicsContext gc) {
            pastX = xImage; //clears old place where the obj was
            pastY = yImage;
            gc.clearRect(pastX,pastY, width1, height1);

            xImage+=xVel;
            if(xImage > width/2){
                xImage = -xImage;
            }



            yVel+= gravity; //if jumping, gravity decreases yVel from -20 to 0 overtime

            yImage += yVel; //gravity, pulling down, disabled when jumping

            if (yImage >= 200) { //if gravity pulls it below the line, then resets yVel to 0;
                yImage = 200;
                yVel = 0;
                canJump = true;
            }

            xImage = (xImage + 1) % (screenWidth);
          //obj always moving right
            gc.drawImage(playerImage, xImage, yImage, width1, height1);
        }
    }

        //classes

}