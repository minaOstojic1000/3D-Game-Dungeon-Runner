package dungeonrunner.figures;

import dungeonrunner.Player;
import dungeonrunner.constants.Constants;
import dungeonrunner.constants.Enums;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Doors extends Group {

    private final double width;
    private final double height;
    private final double depth;
    private final Enums.AXIS openingAxis;
    private Group doorL, doorR;
    private Timeline opening;
    private final double positionX;
    private final double positionZ;

    private boolean locked = true;
    private Rectangle surfaceRect;

    public Doors(double height, double width, double depth,
                 double positionX, double positionY, double positionZ,
                 Enums.AXIS openingAxis, double openingDuration) {
        super();

        this.width = width;
        this.height = height;
        this.depth = depth;
        this.openingAxis = openingAxis;

        surfaceRect = new Rectangle(width, height);

        this.positionX = positionX;
        this.positionZ = positionZ;

        makeDoors();

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

    private void makeDoors() {

        doorL = new Group();
        doorR = new Group();

        setUpDoorsSides();

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

        this.getChildren().addAll(doorL, doorR);
    }

    private void setUpDoorsSides() {

        double boxW = (openingAxis == Enums.AXIS.X) ? width / 2. : depth / 2.;
        double boxD = (openingAxis == Enums.AXIS.X) ? depth / 2. : width / 2.;

        Box doorLBoxF = new Box(boxW, height, boxD);
        Box doorLBoxB = new Box(boxW, height, boxD);
        doorL.getChildren().addAll(doorLBoxF, doorLBoxB);

        Box doorRBoxF = new Box(boxW, height, boxD);
        Box doorRBoxB = new Box(boxW, height, boxD);
        doorR.getChildren().addAll(doorRBoxF, doorRBoxB);

        if (openingAxis == Enums.AXIS.X) {
            doorLBoxF.getTransforms().addAll(
                    new Translate(0, 0, -boxD / 2.)
            );
            doorRBoxF.getTransforms().addAll(
                    new Translate(0, 0, -boxD / 2.)
            );
            doorLBoxB.getTransforms().addAll(
                    new Translate(0, 0, boxD / 2.)
            );
            doorRBoxB.getTransforms().addAll(
                    new Translate(0, 0, boxD / 2.)
            );
        }
        else {
            doorLBoxF.getTransforms().addAll(
                    new Translate(boxW / 2., 0, 0)
            );
            doorRBoxF.getTransforms().addAll(
                    new Translate(boxW / 2., 0, 0)
            );
            doorLBoxB.getTransforms().addAll(
                    new Translate(-boxW / 2., 0, 0)
            );
            doorRBoxB.getTransforms().addAll(
                    new Translate(-boxW / 2., 0, 0)
            );
        }

        PhongMaterial leftMaterial = new PhongMaterial();
        leftMaterial.setDiffuseMap(Constants.DOOR_LEFT_IMG);

        PhongMaterial rightMaterial = new PhongMaterial();
        rightMaterial.setDiffuseMap(Constants.DOOR_RIGHT_IMG);

        doorLBoxF.setMaterial(leftMaterial);
        doorRBoxF.setMaterial(rightMaterial);
        doorLBoxB.setMaterial(rightMaterial);
        doorRBoxB.setMaterial(leftMaterial);
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
