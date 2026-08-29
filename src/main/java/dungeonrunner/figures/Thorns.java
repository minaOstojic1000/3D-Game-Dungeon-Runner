package dungeonrunner.figures;

import dungeonrunner.Player;
import dungeonrunner.constants.Constants;
import dungeonrunner.interfaces.IEnemy;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableFloatArray;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Material;
import javafx.scene.shape.*;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Thorns extends Group implements IEnemy {

    private int number;
    private double height, width;
    private double startY, endY;
    private List<MeshView> thorns = new ArrayList<>();

    private boolean visible = false;

    public Thorns(double height, double aggWidth, int number,
                      double rowGap, double colGap,
                      double positionX, double positionY, double positionZ,
                      double speed, double period,
                      Material material) {
        super();

        this.height = height;
        this.number = number;

        this.startY = positionY;
        this.endY = startY + height;

        makeFigures(aggWidth, rowGap, colGap, material);

        setTranslateX(positionX);
        setTranslateZ(positionZ);

        setUpAnimation(period, speed);

        IEnemy.addEnemy(this);
    }

    private void makeFigures(double aggWidth, double rowGap, double colGap, Material material) {

        int rows = (int) Math.floor(Math.sqrt(number));

        int cols = number / rows;
        int rest = number % (cols * rows);
        rows += (rest > 0) ? 1 : 0;
        cols += rest;

        this.width = (aggWidth - (rows - 1) * rowGap) / rows;

        int jackRow = rows / 2;
        ArrayList<Integer> jackCols = new ArrayList<>();

        for (int i = 0; i < rest; i++) {
            jackCols.add((int) (((i + 1) * (double) cols) / (rest + 1)));
        }

        int[][] mat = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rest > 0 &&
                        ((i == jackRow && !jackCols.contains(j)) ||
                        (i != jackRow && jackCols.contains(j)))) {
                    mat[i][j] = 0;
                }
                else {
                    mat[i][j] = 1;
                }
            }
        }

        double initialDisp = width / 2. - aggWidth / 2.;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mat[i][j] == 0)
                    continue;
                MeshView thorn = makeOne((float)(i * (rowGap + width) + initialDisp), (float)(j * (colGap + width) + initialDisp));
                thorn.setMaterial(material);
                this.getChildren().add(thorn);
                thorns.add(thorn);
            }
            System.out.println();
        }
    }

    private MeshView makeOne(float x, float z) {

        TriangleMesh mesh = new TriangleMesh();

        float[] points = {
                x, -(float)(height), z,
                x + (float)(width / 2.), 0, z + (float)(width / 2.),
                x + (float)(width / 2.), 0, z - (float)(width / 2.),
                x - (float)(width / 2.), 0, z - (float)(width / 2.),
                x - (float)(width / 2.), 0, z + (float)(width / 2.),
        };

        float[] texCoords = {
                0.5f, 0f,
                0f,   1f,
                1f,   1f
        };

        int[] sides = {
                2, 2,  0, 0,  3, 1,
                1, 2,  0, 0,  2, 1,
                4, 2,  0, 0,  1, 1,
                3, 2,  0, 0,  4, 1,
        };

        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(texCoords);
        mesh.getFaces().addAll(sides);

        MeshView thorn = new MeshView();
        thorn.setMesh(mesh);
        return thorn;
    }

    private void setUpAnimation(double periodTime, double speed) {

        double movingTime = height / speed;

        KeyValue startState = new KeyValue(translateYProperty(), startY); // up
        KeyValue endState = new KeyValue(translateYProperty(), endY); // down

        KeyFrame startFrame = new KeyFrame(
                Duration.ZERO,
                event -> visible = true,
                startState
        );

        KeyFrame endFrame = new KeyFrame(
                Duration.seconds(movingTime),
                event -> visible = false,
                endState);

        KeyFrame pauseFrame = new KeyFrame(
                Duration.seconds(movingTime + periodTime),
                endState
        );

        Timeline timeline = new Timeline(startFrame, endFrame, pauseFrame);
        timeline.setOnFinished(event -> visible = true);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public boolean touchesPlayer(Player player) {

        if (!visible)
            return false;

        for(MeshView thorn : thorns) {
            TriangleMesh mesh = (TriangleMesh) thorn.getMesh();
            boolean inOne = overlapsThorn(player, mesh);
            if (inOne) return true;
        }
        return false;
    }

    private boolean overlapsThorn(Player player, TriangleMesh mesh) {

        ObservableFloatArray points = mesh.getPoints();

        double left  = points.get(9) + this.getTranslateX();
        double right = left + width;
        double up    = points.get(5) + this.getTranslateZ();
        double down  = up + width;

        return player.overlapsRectangle(left, right, up, down);

    }

}
