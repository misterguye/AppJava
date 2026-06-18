package appjava;

import java.util.*;

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
public class MainApp extends Application {
    int height = 1080;
    int width = 1920;

    final int GRAVITY = -5;
    int gravity = GRAVITY;

    boolean canJump = false;
    double elapsedSeconds = 0;

    public static enum move {
        Left,

        Right,
        Jump,
        None;
    }


    move Dir = move.None;


    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(); //allows the arrangement of things on the app
        Canvas canva = new Canvas(width, height); //creates grid
        GraphicsContext gc = canva.getGraphicsContext2D(); //allows creation of objects
        StickFigure stick = new StickFigure();

        AnimationTimer timer = new AnimationTimer() {


            long startTime = 0;

            @Override
            public void handle(long now) {
                if (startTime == 0) {
                    startTime = now;

                    return;
                }
                // elapsedSeconds = (now - startTime) / 1_000_000_000.0;

                // Update Loop
                stick.update(gc);

            }
        };
        timer.start();
        stick.draw(gc);
        root.getChildren().add(canva);
        Scene scene = new Scene(root, width, height);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.W) {
                Dir = move.Jump;
            } else if (key.getCode() == KeyCode.D) {
                Dir = move.Right;
            } else if (key.getCode() == KeyCode.A) {
                Dir = move.Left;
            } else if (key.getCode() == KeyCode.S) {

            }

        });
        scene.addEventFilter(KeyEvent.KEY_RELEASED, key -> {
            if (key.getCode() == KeyCode.BACK_SPACE || key.getCode() == KeyCode.D || key.getCode() == KeyCode.A || key.getCode() == KeyCode.W) {
                Dir = move.None;
            }

        });
        // Showing Everything
        stage.setTitle("JavaFX and Gradle");
        stage.setScene(scene);
        stage.show();

    }
    public class Player {
        javafx.scene.image.Image playerImage;
        int yVel = 0;
        int xVel = 2; //always moving right;
        int xImage;
        int yImage;
        int pastX = 0;
        int pastY = 0;
        final int width = 500;
        final int height = 400;

    public static void main(String[] args) {
        launch(args);
    }

    //classes
    public class Line {
        int x1;
        int y1;
        int x2;
        int y2;

        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }
    }

    public class StickFigure {
        ArrayList<Line> stickFigure = new ArrayList<Line>();
        int yVel = 0;
        int xVel = 0;


        public StickFigure() {
            stickFigure.add(new Line(200, 200, 200, 300));
            stickFigure.add(new Line(200, 300, 150, 400));
            stickFigure.add(new Line(200, 300, 250, 400));
            stickFigure.add(new Line(200, 250, 275, 175));
            stickFigure.add(new Line(200, 250, 125, 175));

        }

        public void draw(GraphicsContext gc) {
            for (int i = 0; i < stickFigure.size(); i++) {
                Line temp = stickFigure.get(i);
                gc.strokeLine(temp.x1, temp.y1, temp.x2, temp.y2);
            }
        }

        public void update(GraphicsContext gc) {
            if (Dir == move.Right) {
                xVel = 1;
            }
            else if (Dir == move.Left) {
                xVel = -1;
            }
            else {
                xVel = 0;
            }

            // Legs end at y2 on indices 1 and 2
            int lowestY = stickFigure.get(1).y2;

            if (lowestY >= 800) {
                canJump = true;
                yVel = 0;

                // Clamp: push figure back up if it sank past floor
                int overlap = lowestY - 800;
                for (Line l : stickFigure) {
                    l.y1 -= overlap;
                    l.y2 -= overlap;
                }
            }

            // Jump: negative yVel moves UP (y decreases)
            if (Dir == move.Jump && canJump) {
                yVel = -20;
                canJump = false;
            }

            // Gravity pulls DOWN (y increases)
            yVel += 1;

            for (Line l : stickFigure) {
                l.x1 += xVel;
                l.x2 += xVel;
                l.y1 += yVel;
                l.y2 += yVel;
            }

            gc.clearRect(0, 0, width, height);
            draw(gc);
        }

    }
}