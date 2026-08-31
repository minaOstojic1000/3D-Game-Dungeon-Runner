package dungeonrunner.constants;

import javafx.scene.image.Image;

import java.util.List;
import java.util.Objects;

public class Maps {

    private static final int[][] MAP1 = {
            {1,1,1,1,1,1,1,1,1},
            {1,0,0,9,1,0,0,0,1},
            {1,0,8,0,0,0,1,0,1},
            {1,0,3,1,5,0,4,0,1},
            {1,0,0,0,0,0,0,0,1},
            {1,3,1,0,7,1,0,1,1},
            {1,0,0,0,0,0,0,2,1},
            {1,1,1,1,6,1,1,1,1},
    };
    private static final int[][] MAP2 = {
            {1,1,1,1,1,1,1,1,1},
            {1,0,0,9,1,0,0,0,1},
            {1,0,8,0,0,0,1,0,1},
            {1,0,3,1,5,0,4,0,1},
            {1,0,0,0,0,0,0,0,1},
            {1,3,1,0,7,1,0,1,1},
            {1,0,0,0,0,0,0,2,1},
            {1,1,1,1,6,1,1,1,1},
    };
    private static final int[][] MAP3 = {
            {1,1,1,1,1,1,1,1,1},
            {1,0,0,9,1,0,0,0,1},
            {1,0,8,0,0,0,1,0,1},
            {1,0,3,1,5,0,4,0,1},
            {1,0,0,0,0,0,0,0,1},
            {1,3,1,0,7,1,0,1,1},
            {1,0,0,0,0,0,0,2,1},
            {1,1,1,1,6,1,1,1,1},
    };
    private static final int[][] MAP4 = {
            {1,1,1,1,1,1,1,1,1},
            {1,0,0,9,1,0,0,0,1},
            {1,0,8,0,0,0,1,0,1},
            {1,0,3,1,5,0,4,0,1},
            {1,0,0,0,0,0,0,0,1},
            {1,3,1,0,7,1,0,1,1},
            {1,0,0,0,0,0,0,2,1},
            {1,1,1,1,6,1,1,1,1},
    };

    public static final List<int[][]> MAPS = List.of(MAP1, MAP2, MAP3, MAP4);

    public static final Image[] MAP_BRICKS_IMAGES = {
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/bricks/bricks1.jpg"))),
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/bricks/bricks2.jpg"))),
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/bricks/bricks3.jpg"))),
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/bricks/bricks4.jpg"))),
    };

}
