package dungeonrunner.constants;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;

public class Enums {
    public enum DIRECTION {
        LEFT(-1),
        RIGHT(1);

        private final int value;

        private DIRECTION(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum AXIS {
        X(Rotate.X_AXIS),
        Y(Rotate.Y_AXIS),
        Z(Rotate.Z_AXIS);

        private final Point3D value;

        private AXIS(Point3D value) {
            this.value = value;
        }

        public Point3D getValue() {
            return this.value;
        }
    }
}
