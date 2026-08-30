package dungeonrunner.figures;

import dungeonrunner.Player;
import dungeonrunner.constants.Enums;
import dungeonrunner.infoPanes.EndOfGame;
import dungeonrunner.interfaces.IEnemy;
import dungeonrunner.interfaces.IPowerUp;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.paint.Material;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.awt.geom.Point2D;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Key extends Group implements IPowerUp {

    private final double height;
    private final double positionX;
    private final double positionY;
    private final double positionZ;
    private double radius;

    static private List<Key> toClean = new ArrayList<>();

    public Key(double height,
               double positionX, double positionY, double positionZ,
               double duration, Material material) {
        super();

        this.height = height;
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;

        makeFigures(material);

        this.getTransforms().add(
                new Translate(positionX, positionY, positionZ)
        );

        setUpAnimation(duration);

        IPowerUp.addPowerUp(this);

    }

    private void makeFigures(Material material) {

        double height1 = height / 2.;
        double width1 = height1 / 5.;
        double depth1 = width1 / 2.;

        this.radius = height1 / 2. + width1 / 2.;

        Box box1 = new Box(height1, width1, depth1);
        box1.getTransforms().add(
                new Translate(
                        0,
                        -(height1 + width1 / 2.))
        );
        Box box2 = new Box(width1, height1, depth1);
        box2.getTransforms().add(
                new Translate(
                        height1 / 2.,
                        -(height1 / 2.))
        );
        Box box3 = new Box(width1, height1, depth1);
        box3.getTransforms().add(
                new Translate(
                        -height1 / 2.,
                        -(height1 / 2.))
        );
        Box box4 = new Box(width1, height1, depth1);
        box4.getTransforms().add(
                new Translate(
                        0,
                        height1 / 2.)
        );
        Box box5 = new Box(height1 / 2., width1, depth1);
        box5.getTransforms().add(
                new Translate(
                        box5.getWidth() / 2. + width1 / 2.,
                        height1 / 2.
                )
        );
        Box box6 = new Box(height1 / 3., width1, depth1);
        box6.getTransforms().add(
                new Translate(
                        box6.getWidth() / 2. + width1 / 2.,
                        height1 - box6.getHeight() / 2.)
        );

        box1.setMaterial(material);
        box2.setMaterial(material);
        box3.setMaterial(material);
        box4.setMaterial(material);
        box5.setMaterial(material);
        box6.setMaterial(material);

        this.getChildren().addAll(box1, box2, box3, box4, box5, box6);
    }

    private void setUpAnimation(double duration) {
        Rotate rotation = new Rotate(0, Enums.AXIS.Y.getValue());
        rotation.setPivotY(positionY);
        this.getTransforms().add(rotation);

        Timeline timelineR = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(rotation.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(duration),
                        new KeyValue(rotation.angleProperty(), 360)
                )
        );

        double disp = 0.2 * height;

        Timeline timelineT = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(
                                this.translateYProperty(),
                                positionY - disp,
                                Interpolator.EASE_BOTH)
                        ),
                new KeyFrame(
                        Duration.seconds(duration / 2.),
                        new KeyValue(
                                this.translateYProperty(),
                                positionY + disp,
                                Interpolator.EASE_BOTH
                        )
                )
        );
        timelineR.setCycleCount(Animation.INDEFINITE);
        timelineR.play();
        timelineT.setCycleCount(Animation.INDEFINITE);
        timelineT.setAutoReverse(true);
        timelineT.play();
    }

    public void show() {
        setVisible(true);
        if (!IPowerUp.getPowerUps().contains(this))
            IPowerUp.addPowerUp(this);
    }

    public void hide() {
        setVisible(false);
        toClean.add(this);
    }

    static public void clean() {
        for (Key key : toClean) {
            key.setVisible(false);
            IPowerUp.removePowerUp(key);
        }
        toClean.clear();
    }

    @Override
    public void affect(Player player) {
        player.setHasTheKey(true);
        hide();
    }

    @Override
    public boolean touchesPlayer(Player player) {
        double centersD = Point2D.distance(
                positionX, positionZ,
                player.getPositionWorldX(), player.getPositionWorldY()
        );
        return centersD <= this.radius + player.getRadius();
    }
}
