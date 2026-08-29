package dungeonrunner.figures;

import dungeonrunner.Player;
import dungeonrunner.constants.Enums;
import dungeonrunner.interfaces.IEnemy;
import javafx.animation.*;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.effect.Light;
import javafx.scene.paint.Material;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CircularSaw extends Group implements IEnemy {

    private double width, depth;
    private double startX, startY, startZ;
    private double endX, endY, endZ;
    private Enums.AXIS rotationAxis;

    private Rectangle surfaceRect;

    public CircularSaw(double width, double depth,
                       double positionX, double positionY, double positionZ,
                       Enums.AXIS axis, Enums.DIRECTION direction, double distance,
                       double speed, double period,
                       Material material) {
        super();
        this.width = width;
        this.depth = depth;

        this.startX = positionX;
        this.startY = positionY;
        this.startZ = positionZ;

        this.endX = (axis == Enums.AXIS.X) ? positionX + distance * direction.getValue() : positionX;
        this.endY = getTranslateY();
        this.endZ = (axis == Enums.AXIS.Z) ? positionZ + distance * direction.getValue() : positionZ;

        rotationAxis = (axis == Enums.AXIS.Z) ? Enums.AXIS.X : Enums.AXIS.Z;

        makeFigure(material, direction);

        setupAnimation(speed, distance, period);

        IEnemy.addEnemy(this);
    }
    private void makeFigure(Material material, Enums.DIRECTION direction) {
        double boxSize = width / Math.sqrt(2);

        double w = (rotationAxis == Enums.AXIS.Z) ? boxSize : depth;
        double d = (rotationAxis == Enums.AXIS.Z) ? depth : boxSize;

        Box box1 = new Box(w, boxSize, d);
        Box box2 = new Box(w, boxSize, d);

        box2.getTransforms().add(
                new Rotate(direction.getValue() * 45, rotationAxis.getValue())
        );
        box1.setMaterial(material);
        box2.setMaterial(material);
        this.getChildren().addAll(box1, box2);

        surfaceRect = new Rectangle(w, d);
    }

    private void setupAnimation(double speed, double distance, double periodTime) {

        double spinningTime = distance / speed;
        setRotationAxis(this.rotationAxis.getValue());

        List<KeyValue> startState = List.of(
                new KeyValue(translateXProperty(), startX),
                new KeyValue(translateYProperty(), startY),
                new KeyValue(translateZProperty(), startZ),
                new KeyValue(rotateProperty(), 0),

                new KeyValue(surfaceRect.translateXProperty(), startX),
                new KeyValue(surfaceRect.translateZProperty(), startZ)
        );

        List<KeyValue> endState = List.of(
                new KeyValue(translateXProperty(), endX),
                new KeyValue(translateYProperty(), endY),
                new KeyValue(translateZProperty(), endZ),
                new KeyValue(rotateProperty(), 180),

                new KeyValue(surfaceRect.translateXProperty(), endX),
                new KeyValue(surfaceRect.translateZProperty(), endZ)
        );

        KeyFrame startFrame = new KeyFrame(
                Duration.ZERO,
                startState.toArray(new KeyValue[0])
        );

        KeyFrame endFrame = new KeyFrame(
                Duration.seconds(spinningTime),
                endState.toArray(new KeyValue[0])
        );

        KeyFrame pauseFrame = new KeyFrame(
                Duration.seconds(periodTime + spinningTime * 2),
                startState.toArray(new KeyValue[0])
        );

        Timeline timeline = new Timeline(pauseFrame, startFrame, endFrame);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public boolean touchesPlayer(Player player) {

        double rectX = surfaceRect.getTranslateX();
        double rectZ = surfaceRect.getTranslateZ();

        double left = rectX - surfaceRect.getWidth() / 2.;
        double right = rectX + surfaceRect.getWidth() / 2.;
        double up = rectZ - surfaceRect.getHeight() / 2.;
        double down = rectZ + surfaceRect.getHeight() / 2.;

        return player.overlapsRectangle(left, right, up, down);
    }
}
