package dungeonrunner.figures;

import dungeonrunner.generation.DungeonMap;
import dungeonrunner.Player;
import dungeonrunner.constants.Constants;
import dungeonrunner.interfaces.IEnemy;
import dungeonrunner.interfaces.IRotatingItem;
import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Potion extends Group implements IEnemy, IRotatingItem {

    private final double radius;
    private double height;
    private final double positionX;
    private final double positionY;
    private final double positionZ;
    private final double effectDuration;
    private PauseTransition myTimer;
    private static List<Potion> expiredPotions = new ArrayList<>();

    public Potion(double radius,
                  double positionX, double positionY, double positionZ,
                  double effectDuration, double potionDuration, double rotateDuration,
                  Color diffuseGlass, Color diffuseLiquid, Color specular) {

        this.radius = radius;
        this.height = radius * 3;
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;

        this.effectDuration = effectDuration;

        createPotion(diffuseGlass, diffuseLiquid, specular);

        setMyTimer(potionDuration);

        IEnemy.addEnemy(this);

        this.getTransforms().add(
                new Translate(positionX, positionY, positionZ)
        );

        setUpAnimation(rotateDuration);
    }

    private void createPotion(Color diffuseGlass, Color diffuseLiquid, Color specular) {
        double radiusC = radius * 0.15;
        double heightC = height * 0.4;
        Cylinder cylinder = new Cylinder(radiusC, heightC);
        cylinder.getTransforms().add(
                new Translate(0, -cylinder.getHeight() / 2.)
        );

        double liquidPercentage = 0.55;
        double lH = (height - heightC) * liquidPercentage;
        double gH = (height - heightC) * (1 - liquidPercentage);
        double radiusG = (gH * radius) / (gH + lH);

        MeshView liquid = new MeshView();
        TriangleMesh liquidMesh = createTriangleMesh(radius, radiusG, lH);
        liquid.setMesh(liquidMesh);
        PhongMaterial liquidMaterial = new PhongMaterial();
        liquidMaterial.setDiffuseColor(diffuseLiquid);
        liquidMaterial.setSpecularColor(specular);
        liquid.setMaterial(liquidMaterial);
        liquid.getTransforms().add(
                new Translate(0, gH + lH / 2., 0)
        );

        MeshView glass = new MeshView();

        TriangleMesh glassMesh = createTriangleMesh(radiusG, radiusC, gH);
        glass.setMesh(glassMesh);
        PhongMaterial glassMaterial = new PhongMaterial();
        glassMaterial.setDiffuseColor(diffuseGlass);
        glassMaterial.setSpecularColor(specular);
        glass.setMaterial(glassMaterial);
        cylinder.setMaterial(glassMaterial);
        glass.getTransforms().add(
                new Translate(0, gH / 2., 0)
        );

        glassMaterial.setSpecularPower(64.0);
        this.getChildren().addAll(liquid, glass, cylinder);
    }

    private TriangleMesh createTriangleMesh(double radiusB, double radiusS, double height) {
        ArrayList<Float> points = new ArrayList<>();
        int num = 25;
        double angle = 360. / num;
        for (int i = 0; i < num; i++) {
            points.add((float)(radiusB * Math.cos(Math.toRadians(angle * i))));
            points.add((float)(height / 2.));
            points.add((float)(radiusB * Math.sin(Math.toRadians(angle * i))));
        }

        for (int i = 0; i < num; i++) {
            points.add((float)(radiusS * Math.cos(Math.toRadians(angle * i))));
            points.add((float)(-height / 2.));
            points.add((float)(radiusS * Math.sin(Math.toRadians(angle * i))));
        }

        float[] texCoords = { 0f, 0f };
        ArrayList<Integer> faces = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            faces.addAll(List.of(i, 0));
            faces.addAll(List.of((i + 1) % num, 0));
            faces.addAll(List.of((i + num), 0));

            faces.addAll(List.of((i + 1) % num, 0));
            faces.addAll(List.of((i + 1) % num + num, 0));
            faces.addAll(List.of((i + num), 0));
        }

        float[] pointsArray = new float[points.size()];

        for (int i = 0; i < points.size(); i++) {
            pointsArray[i] = points.get(i);
        }

        int[] facesArray = new int[faces.size()];

        for (int i = 0; i < faces.size(); i++) {
            facesArray[i] = faces.get(i);
        }

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(pointsArray);
        mesh.getTexCoords().addAll(texCoords);
        mesh.getFaces().addAll(facesArray);

        return mesh;
    }

    public static Potion generateRandomPotions(DungeonMap map, double radius,
                                               Color diffuseGlass, Color diffuseLiquid, Color specular,
                                               double effectDuration, double potionDuration,
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

        Potion potion = new Potion(radius,
                positionX, positionY, positionZ,
                effectDuration, potionDuration, rotateDuration,
                diffuseGlass, diffuseLiquid, specular);

        potion.myTimer.play();

        return potion;
    }

    private void setMyTimer(double seconds) {
        myTimer = new PauseTransition(Duration.seconds(seconds));

        myTimer.setOnFinished(event -> {
            this.setVisible(false);
            expiredPotions.add(this);
        });
    }

    @Override
    public void affect(Player player) {
        if (!player.getImmune())
            player.switchDirection(effectDuration);
        this.setVisible(false);
        expiredPotions.add(this);
    }

    public static void cleanExpired() {
        for (Potion potion : expiredPotions)
            IEnemy.removeEnemy(potion);
        expiredPotions.clear();
    }

    public static List<Potion> getExpiredPotions() {
        return expiredPotions;
    }

    public static void setExpiredPotions(List<Potion> expiredPotions) {
        Potion.expiredPotions = expiredPotions;
    }

    @Override
    public double getPositionY() {
        return positionY;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean touchesPlayer(Player player) {
        return player.overlapsCircle(radius, positionX, positionZ);
    }
}
