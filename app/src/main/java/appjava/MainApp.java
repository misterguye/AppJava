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
    double scoreCount = 0;

    ImageView myGhiniBox;
    ImageView playerView;

    boolean continues = false;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GameUI.fxml"));
        Scene scene = new Scene(loader.load(), 412, 915);

        stage.setTitle("JavaFX Game");
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

        public void update(double dt) {
            if(ghiniX < -400) {
                ghiniX = 500;
            }

            ghiniX -= ((scoreCount / 250) + 7) * dt * 60;
            myGhiniBox.setX(ghiniX);

            if (xImage > width / 2) {
                xImage = -xImage;
            }

            yVel += gravity * dt * 60;
            yImage += yVel * dt * 60;

            if (yImage >= 450) {
                yImage = 450;
                yVel = 0;
                canJump = true;
            }

            // Move the ImageView — this is what makes collision work
            view.setLayoutX(xImage);
            view.setLayoutY(yImage);
        }
    }

}