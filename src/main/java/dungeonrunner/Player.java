package dungeonrunner;

import dungeonrunner.constants.Constants;
import dungeonrunner.generation.DungeonMap;
import dungeonrunner.figures.LifeIndicator;
import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.Region;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.List;

public class Player {
    private double positionX;
    private double positionY;
    private double directionX;
    private double directionY;
    private boolean moveForward;
    private boolean moveBackward;
    private boolean rotateLeft;
    private boolean rotateRight;

    private final double startX;
    private final double startY;
    private final double startDirX;
    private final double startDirY;

    private int currLife;
    private List<LifeIndicator> lives;
    private boolean hasTheKey = false;

    private boolean immune = false;
    private Group shieldViewGroup = null;
    private PauseTransition immuneTimer;
    private Group cameraGroup;

    private boolean directionSwitched = false;
    private PauseTransition switchDirectionTimer;

    private double moveSpeed = Constants.PLAYER_MOVE_SPEED;

    public Player (double startX, double startY, int lives, Group cameraGroup) {
        this.positionX = startX;
        this.positionY = startY;

        this.startX = startX;
        this.startY = startY;

        this.directionX =  1.0;
        this.directionY =  0.0;

        this.startDirX = this.directionX;
        this.startDirY = this.directionY;

        this.currLife = lives - 1;
        this.cameraGroup = cameraGroup;
    }

    public Player ( double startX, double startY, List<LifeIndicator> lives, Group cameraGroup) {
        this(startX, startY, lives.size(), cameraGroup);
        this.lives = List.copyOf(lives);
    }

    public double getPositionX ( ) { return this.positionX; }
    public double getPositionY ( ) { return this.positionY; }

    public double getPositionWorldX ( ) { return this.positionX * Constants.CELL_SIZE; }
    public double getPositionWorldY ( ) { return this.positionY * Constants.CELL_SIZE; }

    public double getDirectionX ( ) { return this.directionX; }
    public double getDirectionY ( ) { return this.directionY; }

    public void setMoveForward(boolean newValue) {
        if (!directionSwitched)
            this.moveForward = newValue;
        else {
            this.moveBackward = newValue;
            this.moveForward = false;
        }
    }
    public void setMoveBackward(boolean newValue) {
        if (!directionSwitched)
            this.moveBackward  = newValue;
        else {
            this.moveForward = newValue;
            this.moveBackward = false;
        }
    }
    public void setRotateLeft(boolean newValue) {
        if (!directionSwitched)
            this.rotateLeft  = newValue;
        else {
            this.rotateRight = newValue;
            this.rotateLeft = false;
        }
    }
    public void setRotateRight(boolean newValue) {
        if (!directionSwitched)
            this.rotateRight  = newValue;
        else {
            this.rotateLeft = newValue;
            this.rotateRight = false;
        }
    }

    public boolean getHasTheKey() { return hasTheKey; }
    public void setHasTheKey(boolean hasTheKey) { this.hasTheKey = hasTheKey; }

    public void update ( DungeonMap map ) {
        if ( this.moveForward ) {
            double newX = this.positionX + this.directionX * moveSpeed;
            double newY = this.positionY + this.directionY * moveSpeed;

            if ( canMoveTo ( newX, positionY, map ) ) {
                this.positionX = newX;
            }

            if ( canMoveTo ( positionX, newY, map ) ) {
                this.positionY = newY;
            }
        }

        if ( this.moveBackward ) {
            double newX = this.positionX - this.directionX * moveSpeed;
            double newY = this.positionY - this.directionY * moveSpeed;

            if ( canMoveTo ( newX, positionY, map ) ) {
                this.positionX = newX;
            }

            if ( canMoveTo ( positionX, newY, map ) ) {
                this.positionY = newY;
            }
        }

        if ( this.rotateLeft ) {
            rotate ( Constants.PLAYER_ROTATION_SPEED );
        }
        if ( this.rotateRight ) {
            rotate ( -Constants.PLAYER_ROTATION_SPEED );
        }
    }

    public boolean isAtExit ( DungeonMap map ) {
        return map.get ((int) this.positionX,(int) this.positionY) == Constants.EXIT;
    }
    private boolean canMoveTo ( double x, double y, DungeonMap map ) {
        return isFree ( ( int ) ( x + Constants.PLAYER_RADIUS ), ( int ) ( y + Constants.PLAYER_RADIUS ), map )
            && isFree ( ( int ) ( x + Constants.PLAYER_RADIUS ), ( int ) ( y - Constants.PLAYER_RADIUS ), map )
            && isFree ( ( int ) ( x - Constants.PLAYER_RADIUS ), ( int ) ( y + Constants.PLAYER_RADIUS ), map )
            && isFree ( ( int ) ( x - Constants.PLAYER_RADIUS ), ( int ) ( y - Constants.PLAYER_RADIUS ), map );
    }

    private boolean isFree ( int x, int y, DungeonMap map ) {
        return map.canStep(x, y);
    }

    private void rotate ( double angle ) {
        Point2D newDirection = new Rotate ( Math.toDegrees ( angle ) ).transform ( this.directionX, this.directionY );
        this.directionX = newDirection.getX ( );
        this.directionY = newDirection.getY ( );
    }

    public void resetPosition() {
        this.positionX = startX;
        this.positionY = startY;
        this.directionX = startDirX;
        this.directionY = startDirY;
    }

    public int getCurrLife() { return currLife; }

    public int getRestLives() { return currLife + 1; }

    public void setUpLives(List<LifeIndicator> lives) { this.lives = List.copyOf(lives); }

    public double getRadius() { return Constants.PLAYER_RADIUS; }

    public void loseLives(int numOfLives) {
        for (int i = 0; i < numOfLives; i++) {
            if (currLife < 0)
                return;
            lives.get(currLife).loseColor();
            currLife--;
        }
    }

    public void addLives(int numOfLives) {
        for (int i = 0; i < numOfLives; i++) {
            if (currLife == lives.size() - 1)
                return;
            currLife++;
            lives.get(currLife).getColored();
        }
    }

    public void takeDamageDefault(int numOfLives) {
        resetPosition();
        loseLives(numOfLives);
    }

    public boolean overlapsRectangle(double left, double right, double up, double down) {

        double playerX = getPositionWorldX();
        double playerY = getPositionWorldY();

        double closestX = Math.max(left, Math.min (playerX, right));
        double closestY = Math.max(up, Math.min(playerY, down));

        double dx = playerX - closestX;
        double dy = playerY - closestY;

        return dx * dx + dy * dy <= getRadius() * getRadius();
    }

    public void makeImmune(double immunityDuration) {
        immune = true;
        if (shieldViewGroup == null) {
            createShield();
            immuneTimer = new PauseTransition(Duration.seconds(immunityDuration));
            immuneTimer.setOnFinished(event -> {
                immune = false;
                shieldViewGroup.setVisible(false);
            });
        }
        shieldViewGroup.setVisible(true);
        immuneTimer.playFromStart();
    }

    private void createShield() {
        shieldViewGroup = new Group();
        Group root = (Group) cameraGroup.getScene().getRoot();
        root.getChildren().add(shieldViewGroup);

        Region shieldView = new Region();
        shieldView.setPrefSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        shieldView.setMouseTransparent(true);
        shieldView.setBackground(Constants.SHIELD_VIEW);

        Region shieldFlash = new Region();
        shieldFlash.setMouseTransparent(true);
        shieldFlash.setBackground(Constants.SHIELD_FLASH);
        shieldFlash.setPrefWidth(250);
        shieldFlash.setPrefSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        root.getChildren().add(shieldFlash);
        shieldFlash.toFront();
        TranslateTransition flashAnimation =
                new TranslateTransition(Duration.seconds(0.65), shieldFlash);

        flashAnimation.setFromX(-250);
        flashAnimation.setToX(Constants.SCREEN_WIDTH + 250);

        PauseTransition pause =
                new PauseTransition(Duration.seconds(1.2));

        SequentialTransition flashLoop =
                new SequentialTransition(
                        flashAnimation,
                        pause
                );

        flashLoop.setCycleCount(Animation.INDEFINITE);
        flashLoop.play();

        shieldViewGroup.getChildren().addAll(shieldView, shieldFlash);
    }

    public boolean overlapsCircle(double radius, double positionX, double positionZ) {
        double centersD = java.awt.geom.Point2D.distance(
                positionX, positionZ,
                getPositionWorldX(), getPositionWorldY()
        );
        return centersD <= radius + getRadius();
    }

    public boolean getImmune() {
        return immune;
    }

    public void switchDirection(double effectDuration) {

        if (switchDirectionTimer == null) {
            switchDirectionTimer = new PauseTransition(Duration.seconds(effectDuration));
            switchDirectionTimer.setOnFinished(event -> {
                directionSwitched = false;
            });
        }

        directionSwitched = true;
        switchDirectionTimer.playFromStart();
    }

    public void setMoveSpeed(double moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public double getMoveSpeed() {
        return moveSpeed;
    }

    public void resetMoveSpeed() {
        setMoveSpeed(Constants.PLAYER_MOVE_SPEED);
    }
}