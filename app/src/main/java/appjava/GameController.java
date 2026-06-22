package appjava;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameController {
    
    @FXML
    private ImageView blueSky;
    
    @FXML
    private ImageView road;
    
    @FXML
    private ImageView myGhiniBox;
    
    @FXML
    private ImageView playerView;
    
    @FXML
    private Label score;
    
    @FXML
    private Label highScore;
    
    @FXML
    private StackPane jump;
    
    @FXML
    private VBox resetOption;
    
    @FXML
    private Button reset;
    
    private double height = 915;
    private double width = 412;
    private double scoreCount = 0;
    private AnimationTimer timer;
    private Player tung;
    
    @FXML
    public void initialize() {
        // Load images
        Image skyImage = new Image(getClass().getResourceAsStream("/360_F_105600193_Bq7GrUHfRaaPQeoJW6m9kDWIVuXvPqHQ.jpg"));
        blueSky.setImage(skyImage);
        
        Image roadImage = new Image(getClass().getResourceAsStream("/empty-straight-road-marking-horizontal-260nw-2090504992.jpg"));
        road.setImage(roadImage);
        
        Image playerImage = new Image(getClass().getResourceAsStream("/tung_tungsahur.png"));
        playerView.setImage(playerImage);
        
        Image ghiniImage = new Image(getClass().getResourceAsStream("/4170d4343f6a626b60450f27f097d3fd-removebg-preview.png"));
        myGhiniBox.setImage(ghiniImage);
        
        // Initialize player
        tung = new Player(playerView);
        
        // Set up jump button listener
        jump.setOnMousePressed(e -> {
            if (tung.canJump) {
                tung.isJump();
                tung.canJump = false;
            }
        });
        
        // Set up reset button listener
        reset.setOnMousePressed(e -> {
            tung.ghiniX = 500;
            scoreCount = 0;
            resetOption.setVisible(false);
            startGame();
        });
        
        // Start the game
        startGame();
    }
    
    private void startGame() {
        if (timer != null) {
            timer.stop();
        }
        
        timer = new AnimationTimer() {
            double highScore1 = 0;
            long lastFrame = 0;
            
            @Override
            public void handle(long now) {
                double dt = 0;
                if (lastFrame == 0) {
                    dt = 0;
                } else {
                    dt = (now - lastFrame) / 1_000_000_000.0;
                }
                lastFrame = now;
                
                scoreCount += (dt * 60);
                score.setText("Score: " + (int) scoreCount);
                
                tung.update(dt);
                
                // Collision check
                Bounds playerBounds = playerView.getBoundsInParent();
                Bounds oBounds = myGhiniBox.getBoundsInParent();
                
                if (playerBounds.intersects(oBounds)) {
                    this.stop();
                    if (scoreCount > highScore1) {
                        highScore1 = scoreCount;
                        highScore.setText("High Score: " + (int) highScore1);
                    }
                    resetOption.setVisible(true);
                }
            }
        };
        timer.start();
    }
    
    public class Player {
        double yVel = 0;
        double xVel = 2;
        double xImage;
        double yImage;
        final double gravity = 1;
        boolean canJump = true;
        public double ghiniX = 300;
        ImageView view;
        
        public Player(ImageView view) {
            this.view = view;
            this.xImage = view.getLayoutX();
            this.yImage = view.getLayoutY();
        }
        
        public void isJump() {
            yVel = -25;
        }
        
        public void update(double dt) {
            if (ghiniX < -400) {
                ghiniX = 500;
            }
            
            ghiniX -= ((scoreCount / 250) + 7) * dt * 60;
            myGhiniBox.setLayoutX(ghiniX);
            
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
            
            view.setLayoutX(xImage);
            view.setLayoutY(yImage);
        }
    }
}
