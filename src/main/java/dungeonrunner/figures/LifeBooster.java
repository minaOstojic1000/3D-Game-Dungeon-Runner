package dungeonrunner.figures;

import dungeonrunner.DungeonMap;
import dungeonrunner.constants.Constants;
import dungeonrunner.constants.Enums;
import dungeonrunner.interfaces.IPowerUp;
import dungeonrunner.Player;
import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LifeBooster extends Heart implements IPowerUp {

    private PauseTransition myTimer;
    private static List<LifeBooster> expiredHearts = new ArrayList<>();
    private final double radius;
    private final double height;

    LifeBooster(double size, double positionX, double positionY, double positionZ,
                Color fillColor, Color strokeColor, double duration, double rotateDuration) {
        super(size, positionX, positionY, positionZ, fillColor, strokeColor);

        setMyTimer(duration);

        this.height = size;
        this.radius = size;

        setUpAnimation(rotateDuration);

        IPowerUp.addPowerUp(this);
    }

    public static LifeBooster generateRandomLifeBooster(DungeonMap map, double size, Color diffuse, Color specular,
                                                        double duration, double rotateDuration) {
        int rows = map.getRows();
        int cols = map.getCols();
        if (rows < 3 || cols < 3)
            return null;

        int rowMin = 1, rowMax = rows - 2;
        int colMin = 1, colMax = cols - 2;
        int row = 0, col = 0;
        Random rnd = new Random();
        row = rnd.nextInt(rowMax - rowMin + 1) + rowMin;
        col = rnd.nextInt(colMax - colMin + 1) + colMin;
        while (map.get(col, row) != Constants.EMPTY && map.get(col, row) != Constants.THORNS) {
            row = rnd.nextInt(rowMax - rowMin + 1) + rowMin;
            col = rnd.nextInt(colMax - colMin + 1) + colMin;
        }

        double positionX = col * Constants.CELL_SIZE + Constants.CELL_SIZE / 2.0;
        double positionY = 0;
        double positionZ = row * Constants.CELL_SIZE + Constants.CELL_SIZE / 2.0;
        LifeBooster life = new LifeBooster(size,
                positionX, positionY, positionZ,
                diffuse, specular, duration, rotateDuration);

        life.myTimer.play();

        return life;
    }

    private void setUpAnimation(double duration) {
        Rotate rotation = new Rotate(0, Enums.AXIS.Y.getValue());
        double positionY = getPositionY();
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

    private void setMyTimer(double seconds) {
        myTimer = new PauseTransition(Duration.seconds(seconds));

        myTimer.setOnFinished(event -> {
            this.setVisible(false);
            expiredHearts.add(this);
        });
    }

    @Override
    public void affect(Player player) {
        player.addLives(1);
        this.setVisible(false);
        expiredHearts.add(this);
    }

    @Override
    public boolean touchesPlayer(Player player) {
        double centersD = Point2D.distance(
                getPositionX(), getPositionZ(),
                player.getPositionWorldX(), player.getPositionWorldY()
        );
        return centersD <= this.radius + player.getRadius();
    }

    public static void cleanExpired() {
        for (LifeBooster life : expiredHearts)
            IPowerUp.removePowerUp(life);
        expiredHearts.clear();
    }

}
