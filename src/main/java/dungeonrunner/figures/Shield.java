
package dungeonrunner.figures;

import dungeonrunner.DungeonMap;
import dungeonrunner.constants.Constants;
import dungeonrunner.interfaces.IPowerUp;
import dungeonrunner.Player;
import dungeonrunner.interfaces.IRotatingItem;
import javafx.animation.PauseTransition;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shield extends Sphere implements IPowerUp, IRotatingItem {

    private final double immunityDuration;
    private PauseTransition myTimer;
    private static List<Shield> expiredShields = new ArrayList<>();
    private final double positionX, positionY, positionZ;

    public Shield(double radius,
                  double positionX, double positionY, double positionZ,
                  double immunityDuration, double shieldDuration, double rotateDuration,
                  Color diffuse, Color specular) {
        super(radius);

        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;

        this.immunityDuration = immunityDuration;

        setUpSphere(diffuse, specular);

        setMyTimer(shieldDuration);

        IPowerUp.addPowerUp(this);

        this.getTransforms().add(
                new Translate(positionX, positionY, positionZ)
        );

        setUpAnimation(rotateDuration);
    }

    private void setUpSphere(Color diffuse, Color specular) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(diffuse);
        material.setSpecularColor(specular);
        this.setMaterial(material);
    }

    public static Shield generateRandomShields(DungeonMap map, double radius,
                                               Color diffuse, Color specular,
                                               double immunityDuration, double shieldDuration,
                                               double rotateDuration) {
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

        Shield shield = new Shield(radius,
                positionX, positionY, positionZ,
                immunityDuration, shieldDuration, rotateDuration,
                diffuse, specular);

        shield.myTimer.play();

        return shield;
    }

    private void setMyTimer(double seconds) {
        myTimer = new PauseTransition(Duration.seconds(seconds));

        myTimer.setOnFinished(event -> {
            this.setVisible(false);
            expiredShields.add(this);
        });
    }

    @Override
    public void affect(Player player) {
        player.makeImmune(immunityDuration);
        this.setVisible(false);
        expiredShields.add(this);
    }

    @Override
    public boolean touchesPlayer(Player player) {
        return player.overlapsCircle(getRadius(), positionX, positionZ);
    }

    public static void cleanExpired() {
        for (Shield shield : expiredShields)
            IPowerUp.removePowerUp(shield);
        expiredShields.clear();
    }

    public static List<Shield> getExpiredShields() {
        return expiredShields;
    }

    public static void setExpiredShields(List<Shield> expiredShields) {
        Shield.expiredShields = expiredShields;
    }

    @Override
    public double getPositionY() {
        return positionY;
    }

    @Override
    public double getHeight() {
        return getRadius() * 2;
    }
}
