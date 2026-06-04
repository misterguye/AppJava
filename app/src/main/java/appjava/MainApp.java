package appjava;

import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.geometry.*;

public class MainApp extends Application {
    private int height = 400;
    private int width = 400;
    private double scale = 20d;

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        
        Scene scene = new Scene(root, height, width);

        StickFigure myFigure = new StickFigure(List.of(
            new Line(10,10,11,8),  
            new Line(12,10,11,8),
            new Line(11,8,11,5),
            new Line(11,6,13,7),
            new Line(11,6,9,7)
        ));

        root.getChildren().addAll(myFigure.figure);
        root.getChildren().add(new Circle(11d * scale,5d * scale,1 * scale));
        

        // Showing Everything
        stage.setTitle("JavaFX and Gradle");
        stage.setScene(scene);
        stage.show();
    }

    public class StickFigure {
        List<Line> figure = new ArrayList<Line>();
        
        public StickFigure(List<Line> elements) {
            for (Line a : elements) {
                figure.add(a);
                a.setStartX(a.startXProperty().doubleValue() * scale);
                a.setEndX(a.endXProperty().doubleValue() * scale);
                a.setStartY(a.startYProperty().doubleValue() * scale);
                a.setEndY(a.endYProperty().doubleValue() * scale);
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}