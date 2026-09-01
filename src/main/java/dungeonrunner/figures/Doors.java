package dungeonrunner.figures;

import dungeonrunner.Player;
import dungeonrunner.constants.Enums;
import dungeonrunner.interfaces.IPickup;
import dungeonrunner.interfaces.IPowerUp;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Material;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Doors extends Group {

    private final double width;
    private final double height;
    private final double depth;
    private final Enums.AXIS openingAxis;
    private Box doorL, doorR;
    private Timeline opening;
    private final double positionX;
    private final double positionZ;

    private boolean locked = true;
    private Rectangle surfaceRect;

    public Doors(double height, double width, double depth,
                 double positionX, double positionY, double positionZ,
                 Enums.AXIS openingAxis, double openingDuration,
                 Material material) {
        super();

        this.width = width;
        this.height = height;
        this.depth = depth;
        this.openingAxis = openingAxis;

        surfaceRect = new Rectangle(width, height);

        this.positionX = positionX;
        this.positionZ = positionZ;

        makeDoors(material);

        this.getTransforms().add(
                new Translate(positionX, positionY, positionZ)
        );

        setUpOpeningAnimation(openingDuration);

    }

    private void setUpOpeningAnimation(double duration) {

        double distance = width / 2.;

        DoubleProperty propertyL = (openingAxis == Enums.AXIS.X) ?
                doorL.translateXProperty() : doorL.translateZProperty();

        DoubleProperty propertyR = (openingAxis == Enums.AXIS.X) ?
                doorR.translateXProperty() : doorR.translateZProperty();

        KeyValue leftEnd = new KeyValue(
                propertyL,
                propertyL.get() - width / 4. - distance,
                Interpolator.EASE_BOTH
        );
        KeyValue rightEnd = new KeyValue(
                propertyR,
                propertyR.get() + width / 4. + distance,
                Interpolator.EASE_BOTH
        );

        opening = new Timeline(
                new KeyFrame(
                        Duration.seconds(duration),
                        leftEnd,
                        rightEnd
                )
        );
    }

    private void makeDoors(Material material) {
        double w = (openingAxis == Enums.AXIS.X) ? width / 2. : depth;
        double d = (openingAxis == Enums.AXIS.X) ? depth : width / 2.;

        doorL = new Box(w, height, d);
        doorR = new Box(w, height, d);

        if (openingAxis == Enums.AXIS.X) {
            doorL.getTransforms().addAll(
                    new Translate(-width / 4., 0, 0)
            );
            doorR.getTransforms().addAll(
                    new Translate(width / 4., 0, 0)
            );
        }
        else {
            doorL.getTransforms().addAll(
                    new Translate(0, 0, -width / 4.)
            );
            doorR.getTransforms().addAll(
                    new Translate(0, 0, width / 4.)
            );
        }

        doorL.setMaterial(material);
        doorR.setMaterial(material);

        this.getChildren().addAll(doorL, doorR);
    }

    public void unlock() {
        locked = false;
        opening.play();
    }

    public boolean getLocked() {
        return locked;
    }

    public boolean touchesPlayer(Player player) {
        double rectX = positionX;
        double rectZ = positionZ;

        double left = rectX - surfaceRect.getWidth() / 2.;
        double right = rectX + surfaceRect.getWidth() / 2.;
        double up = rectZ - surfaceRect.getHeight() / 2.;
        double down = rectZ + surfaceRect.getHeight() / 2.;

        return player.overlapsRectangle(left, right, up, down);
    }
}
