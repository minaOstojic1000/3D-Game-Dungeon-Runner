package dungeonrunner;

import dungeonrunner.constants.Constants;
import dungeonrunner.figures.LifeIndicator;
import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;

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

    public Player ( double startX, double startY, int lives) {
        this.positionX = startX;
        this.positionY = startY;

        this.startX = startX;
        this.startY = startY;

        this.directionX =  1.0;
        this.directionY =  0.0;

        this.startDirX = this.directionX;
        this.startDirY = this.directionY;

        this.currLife = lives - 1;
    }

    public Player ( double startX, double startY, List<LifeIndicator> lives) {
        this(startX, startY, lives.size());
        this.lives = List.copyOf(lives);
    }

    public double getPositionX ( ) { return this.positionX; }
    public double getPositionY ( ) { return this.positionY; }

    public double getPositionWorldX ( ) { return this.positionX * Constants.CELL_SIZE; }
    public double getPositionWorldY ( ) { return this.positionY * Constants.CELL_SIZE; }

    public double getDirectionX ( ) { return this.directionX; }
    public double getDirectionY ( ) { return this.directionY; }

    public void setMoveForward  ( boolean newValue ) { this.moveForward  = newValue; }
    public void setMoveBackward ( boolean newValue ) { this.moveBackward = newValue; }
    public void setRotateLeft   ( boolean newValue ) { this.rotateLeft   = newValue; }
    public void setRotateRight  ( boolean newValue ) { this.rotateRight  = newValue; }

    public boolean getHasTheKey() { return hasTheKey; }
    public void setHasTheKey(boolean hasTheKey) { this.hasTheKey = hasTheKey; }

    public void update ( DungeonMap map ) {
        if ( this.moveForward ) {
            double newX = this.positionX + this.directionX * Constants.PLAYER_MOVE_SPEED;
            double newY = this.positionY + this.directionY * Constants.PLAYER_MOVE_SPEED;

            if ( canMoveTo ( newX, positionY, map ) ) {
                this.positionX = newX;
            }

            if ( canMoveTo ( positionX, newY, map ) ) {
                this.positionY = newY;
            }
        }

        if ( this.moveBackward ) {
            double newX = this.positionX - this.directionX * Constants.PLAYER_MOVE_SPEED;
            double newY = this.positionY - this.directionY * Constants.PLAYER_MOVE_SPEED;

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
        return map.get ( ( int ) this.positionX, ( int ) this.positionY ) == Constants.EXIT;
    }
    private boolean canMoveTo ( double x, double y, DungeonMap map ) {
        return isFree ( ( int ) ( x + Constants.PLAYER_RADIUS ), ( int ) ( y + Constants.PLAYER_RADIUS ), map )
            && isFree ( ( int ) ( x + Constants.PLAYER_RADIUS ), ( int ) ( y - Constants.PLAYER_RADIUS ), map )
            && isFree ( ( int ) ( x - Constants.PLAYER_RADIUS ), ( int ) ( y + Constants.PLAYER_RADIUS ), map )
            && isFree ( ( int ) ( x - Constants.PLAYER_RADIUS ), ( int ) ( y - Constants.PLAYER_RADIUS ), map );
    }

    private boolean isFree ( int x, int y, DungeonMap map ) {
        int tile = map.get ( x, y );
        return Constants.CAN_STEP.contains(tile);
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
            System.out.println("damaged!");
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
}