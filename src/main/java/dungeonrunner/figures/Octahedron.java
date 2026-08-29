package dungeonrunner.figures;

import javafx.scene.paint.Material;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Translate;

public class Octahedron extends MeshView {

    double width, height;

    public Octahedron(double height, double width,
                      double positionX, double positionY, double positionZ,
                      Material material) {
        super();
        this.width = width;
        this.height = height;
        makeFigure();
        this.setMaterial(material);
        this.getTransforms().add(
                new Translate(positionX, positionY, positionZ)
        );
    }

    private void makeFigure() {
        TriangleMesh mesh = new TriangleMesh();

        float[] points = {
                0, (float)(-height / 2.), 0,
                (float)(width / 2.), 0, (float)(width / 2.),
                (float)(width / 2.), 0, -(float)(width / 2.),
                -(float)(width / 2.), 0, -(float)(width / 2.),
                -(float)(width / 2.), 0, (float)(width / 2.),
                0, (float)(height / 2.), 0
        };

        float[] texCoords = {
                0.5f, 0f,
                0f,   1f,
                1f,   1f,

                0f,   0f,
                1f,   0f,
                0.5f, 1f
        };

        int[] sides = {
                2, 2,  0, 0,  3, 1,
                1, 2,  0, 0,  2, 1,
                4, 2,  0, 0,  1, 1,
                3, 2,  0, 0,  4, 1,
                3, 3,  5, 5,  2, 4,
                2, 3,  5, 5,  1, 4,
                1, 3,  5, 5,  4, 4,
                4, 3,  5, 5,  3, 4
        };

        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(texCoords);
        mesh.getFaces().addAll(sides);

        this.setMesh(mesh);
    }
}
