package dungeonrunner.figures;

import dungeonrunner.Player;
import dungeonrunner.constants.Constants;
import dungeonrunner.constants.Enums;
import dungeonrunner.interfaces.IEnemy;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class Guard extends Group implements IEnemy {

    private final double width;
    private final double height;
    private double radius;
    private Box head, body, armL, armR, legs;
    private final Enums.AXIS movingAxis;
    private final double startX;
    private final double startY;
    private final double startZ;
    private final double endX;
    private final double endY;
    private final double endZ;

    public Guard(double width, double height,
                 double positionX, double positionY, double positionZ,
                 Enums.AXIS movingAxis, Enums.DIRECTION direction,
                 double speed, double distance) {
        super();
        this.width = width;
        this.height = height;
        this.movingAxis = movingAxis;
        this.radius = this.width / 2.;

        this.startX = positionX;
        this.startY = positionY;
        this.startZ = positionZ;

        this.endX = (movingAxis == Enums.AXIS.X) ?
                positionX + distance * direction.getValue() :
                positionX;
        this.endY = positionY;
        this.endZ = (movingAxis == Enums.AXIS.Z) ?
                positionZ + distance * direction.getValue() :
                positionZ;

        makeGuard();

        setUpMovementAnimation(speed, distance);

        IEnemy.addEnemy(this);
    }

    private void setUpMovementAnimation(double speed, double distance) {
        double movingDuration = distance / speed;
        double rotationDuration = 1;

        Rotate rotation = new Rotate(0, Enums.AXIS.Y.getValue());
        rotation.setPivotY(startY);
        this.getTransforms().add(rotation);

        double t1 = movingDuration;
        double t2 = t1 + rotationDuration;
        double t3 = t2 + movingDuration;
        double t4 = t3 + rotationDuration;

        KeyFrame start = new KeyFrame(
                Duration.ZERO,

                new KeyValue(translateXProperty(), startX),
                new KeyValue(translateYProperty(), startY),
                new KeyValue(translateZProperty(), startZ),

                new KeyValue(rotation.angleProperty(), 0)
        );

        KeyFrame atEnd = new KeyFrame(
                Duration.seconds(t1),

                new KeyValue(translateXProperty(), endX),
                new KeyValue(translateYProperty(), endY),
                new KeyValue(translateZProperty(), endZ),

                new KeyValue(rotation.angleProperty(), 0)
        );

        KeyFrame rotatedAtEnd = new KeyFrame(
                Duration.seconds(t2),

                new KeyValue(translateXProperty(), endX),
                new KeyValue(translateYProperty(), endY),
                new KeyValue(translateZProperty(), endZ),

                new KeyValue(rotation.angleProperty(), 180)
        );

        KeyFrame backAtStart = new KeyFrame(
                Duration.seconds(t3),

                new KeyValue(translateXProperty(), startX),
                new KeyValue(translateYProperty(), startY),
                new KeyValue(translateZProperty(), startZ),

                new KeyValue(rotation.angleProperty(), 180)
        );

        KeyFrame rotatedAtStart = new KeyFrame(
                Duration.seconds(t4),

                new KeyValue(translateXProperty(), startX),
                new KeyValue(translateYProperty(), startY),
                new KeyValue(translateZProperty(), startZ),

                new KeyValue(rotation.angleProperty(), 360)
        );

        Timeline moving = new Timeline(
                start,
                atEnd,
                rotatedAtEnd,
                backAtStart,
                rotatedAtStart
        );

        moving.setCycleCount(Animation.INDEFINITE);
        moving.play();
    }

    private void makeGuard() {
        double headPercentageH = 0.2;
        double legsPercentageH = 0.4 * (1 - headPercentageH);
        double bodyPercentageH = 1 - headPercentageH - legsPercentageH;

        double headH = height * headPercentageH;
        double bodyH = height * bodyPercentageH;
        double legsH = height * legsPercentageH;
        double armH = bodyH;

        double bodyPercentageW = 0.8;
        double bodyW = width * bodyPercentageW;
        double headW = bodyW * 0.4;
        double armW = width * (1 - bodyPercentageW) * 0.5;
        double bodyD = armW * 1.5;

        double legsW = bodyW * 0.8;
        double legsD = bodyD;

        if(movingAxis == Enums.AXIS.X) {
            double temp = bodyW;
            bodyW = bodyD;
            bodyD = temp;

            temp = legsW;
            legsW = legsD;
            legsD = temp;
        }

        head = new Box(headW, headH, headW);
        body = new Box(bodyW, bodyH, bodyD);
        legs = new Box(legsW, legsH, legsD);
        armL = new Box(armW, armH, armW);
        armR = new Box(armW, armH, armW);

        setMaterials();
        placeBodyParts();
        addArmAnimation(armL, 1);
        addArmAnimation(armR, -1);

        this.getChildren().addAll(legs, body, head, armL, armR);
    }

    private void placeBodyParts() {
        head.getTransforms().add(
                new Translate(
                        0,
                        -(body.getHeight() + head.getHeight()) / 2.,
                        0
                )
        );

        legs.getTransforms().add(
                new Translate(
                        0,
                        (body.getHeight() + legs.getHeight()) / 2.,
                        0
                )
        );

        double armDispY = -(body.getHeight() - armL.getHeight()) / 1.9;

        if (movingAxis == Enums.AXIS.Z) {
            double armDisp = (armL.getWidth() + body.getWidth()) / 2.;
            armL.getTransforms().addAll(
                    new Translate(-armDisp, armDispY, 0)
            );
            armR.getTransforms().addAll(
                    new Translate(armDisp, armDispY, 0)
            );
        }
        else {
            double armDisp = (armL.getWidth() + body.getDepth()) / 2.;
            armL.getTransforms().addAll(
                    new Translate(0, armDispY, -armDisp)
            );
            armR.getTransforms().addAll(
                    new Translate(0, armDispY, armDisp)
            );
        }
    }

    private void addArmAnimation(Box arm, double direction) {
        double duration = 1;
        Enums.AXIS rotationAxis = (movingAxis == Enums.AXIS.X) ? Enums.AXIS.Z : Enums.AXIS.X;
        Rotate rotation = new Rotate(0, rotationAxis.getValue());
        rotation.setPivotY(-arm.getHeight() / 2.2);
        arm.getTransforms().add(rotation);

        double angle = 30;

        KeyFrame startFrame = new KeyFrame(
                Duration.ZERO,
                new KeyValue(rotation.angleProperty(), -direction * angle)
        );
        KeyFrame endFrame = new KeyFrame(
                Duration.seconds(duration),
                new KeyValue(rotation.angleProperty(), direction * angle)
        );
        Timeline timeline = new Timeline(startFrame, endFrame);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void setMaterials() {
        PhongMaterial headMaterial = new PhongMaterial();
        headMaterial.setDiffuseMap(Constants.GUARD_IMAGE_HEAD);
        head.setMaterial(headMaterial);

        PhongMaterial bodyMaterial = new PhongMaterial();
        bodyMaterial.setDiffuseMap(Constants.GUARD_IMAGE_BODY);
        body.setMaterial(bodyMaterial);

        PhongMaterial legsMaterial = new PhongMaterial();
        legsMaterial.setDiffuseMap(Constants.GUARD_IMAGE_LEGS);
        legs.setMaterial(legsMaterial);

        PhongMaterial armMaterial = new PhongMaterial();
        armMaterial.setDiffuseMap(Constants.GUARD_IMAGE_ARM);
        armL.setMaterial(armMaterial);
        armR.setMaterial(armMaterial);
    }

    @Override
    public boolean touchesPlayer(Player player) {
        return player.overlapsCircle(radius, getTranslateX(), getTranslateZ());
    }
}
