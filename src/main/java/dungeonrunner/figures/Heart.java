package dungeonrunner.figures;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Translate;

public class Heart extends MeshView {

    private final Color diffuse, specular;
    private double positionX, positionY, positionZ;
    private double height;
    private double width;

    public Heart(double size, double positionX, double positionY, double positionZ,
                 Color diffuse, Color specular) {

        this.height = size;
        this.width = height * 1.25;
        this.diffuse = diffuse;
        this.specular = specular;
        this.positionX = positionX;
        this.positionY = positionY;
        this.positionZ = positionZ;

        this.getTransforms().add (
                new Translate(positionX, positionY + height / 2., positionZ)
        );

        makeHeart();
    }

    private void makeHeart() {
        TriangleMesh mesh = new TriangleMesh();

        float[] texCoords = {0f, 0f};

        mesh.getPoints().addAll(makePoints());
        mesh.getTexCoords().addAll(texCoords);
        mesh.getFaces().addAll(makeFaces());

        this.setMesh(mesh);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(diffuse);
        material.setSpecularColor(specular);
        this.setMaterial(material);
    }

    private float[] makePoints() {
        float h = (float)(height / 2.);
        float w = (float)(width / 2.);
        float[] points = {
                0, -h, 0, // 0
                0, h, 0, // 1
                0, 0, -w * 0.3f, // 2
                0, 0, w * 0.3f, // 3
                0.75f * w, -0.9f * h, 0, // 4
                -0.75f * w, -0.9f * h, 0, // 5
                0, -h, -w * 0.25f, // 6
                0, -h, w * 0.25f, // 7
                -0.8f * w, -1.5f * h, -w * 0.15f, // 8
                -0.8f * w, -1.5f * h, w * 0.15f, // 9
                -0.3f * w, -1.5f * h, -w * 0.15f, // 10
                -0.3f * w, -1.5f * h, w * 0.15f, // 11
                0.3f * w, -1.5f * h, -w * 0.15f, // 12
                0.3f * w, -1.5f * h, w * 0.15f, // 13
                0.8f * w, -1.5f * h, -w * 0.15f, // 14
                0.8f * w, -1.5f * h, w * 0.15f, // 15
                -0.6f * w, -1.65f * h, 0, // 16
                0.6f * w, -1.65f * h, 0, // 17
        };
        return points;
    }

    private int[] makeFaces() {
        int[] faces = {
                //10, 0, 9, 0, 8, 0,    // square1
                //10, 0, 11, 0, 9, 0,   // square1
                //12, 0, 14, 0, 13, 0,  // square2
                //14, 0, 15, 0, 13, 0,  // square2
                6, 0, 0, 0, 10, 0,    // upLeft
                10, 0, 0, 0, 11, 0,   // upLeft
                0, 0, 7, 0, 11, 0,    // upLeft
                0, 0, 6, 0, 12, 0,    // upRight
                0, 0, 12, 0, 13, 0,   // upRight
                7, 0, 0, 0, 13, 0,    // upRight
                5, 0, 8, 0, 9, 0,     // sideLeftUp
                5, 0, 2, 0, 8, 0,     // frontLeft
                8, 0, 2, 0, 10, 0,    // frontLeft
                10, 0, 2, 0, 6, 0,    // frontLeft
                5, 0, 1, 0, 2, 0,     // frontLeft
                1, 0, 5, 0, 3, 0,     // backLeft
                3, 0, 5, 0, 9, 0,     // backLeft
                3, 0, 9, 0, 11, 0,    // backLeft
                3, 0, 11, 0, 7, 0,    // backLeft
                4, 0, 15, 0, 14, 0,   // sideRightUp
                1, 0, 4, 0, 2, 0,     // frontRight
                2, 0, 4, 0, 14, 0,    // frontRight
                2, 0, 14, 0, 12, 0,   // frontRight
                2, 0, 12, 0, 6, 0,    // frontRight
                1, 0, 3, 0, 4, 0,     // backRight
                4, 0, 3, 0, 15, 0,    // backRight
                3, 0, 13, 0, 15, 0,   // backRight
                3, 0, 7, 0, 13, 0,     // backRight
                9, 0, 8, 0, 16, 0,     // topLeft
                8, 0, 10, 0, 16, 0,     // topLeft
                10, 0, 11, 0, 16, 0,     // topLeft
                11, 0, 9, 0, 16, 0,     // topLeft
                13, 0, 12, 0, 17, 0,     // topRight
                12, 0, 14, 0, 17, 0,     // topRight
                14, 0, 15, 0, 17, 0,     // topRight
                15, 0, 13, 0, 17, 0,     // topRight
        };

        return faces;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public double getPositionZ() {
        return positionZ;
    }
}
