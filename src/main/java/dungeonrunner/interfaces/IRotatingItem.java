package dungeonrunner.interfaces;

import dungeonrunner.constants.Enums;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public interface IRotatingItem {

    default void setUpAnimation(double duration) {
        Rotate rotation = new Rotate(0, Enums.AXIS.Y.getValue());
        double positionY = this.getPositionY();
        rotation.setPivotY(positionY);
        ((Node)this).getTransforms().add(rotation);

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

        double disp = 0.2 * getHeight();

        Timeline timelineT = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(
                                ((Node)this).translateYProperty(),
                                positionY - disp,
                                Interpolator.EASE_BOTH)
                ),
                new KeyFrame(
                        Duration.seconds(duration / 2.),
                        new KeyValue(
                                ((Node)this).translateYProperty(),
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

    double getPositionY();

    double getHeight();
}
