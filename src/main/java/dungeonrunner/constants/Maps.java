package dungeonrunner.constants;

import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.*;

public class Maps {

    private static final int[][] MAP1 = {
            {1,1,1,1,1,1,1,1,1},
            {1,0,8,9,1,0,0,0,1},
            {1,0,16,0,0,0,12,10,1},
            {1,0,16,1,5,0,4,0,1},
            {1,0,16,0,16,16,16,16,1},
            {1,3,14,0,7,1,0,1,1},
            {1,0,11,0,0,0,0,2,1},
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

    public static final HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>
            controlsDoorsPairs = new HashMap<>(Map.ofEntries(
        Map.entry(new Pair<>(2, 6), new Pair<>(2, 7)),
        Map.entry(new Pair<>(5, 2), new Pair<>(6, 2))
    )); // pair<row, col>

    public static final Image[] MAP_BRICKS_IMAGES = {
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/bricks/bricks1.jpg"))),
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/bricks/bricks2.jpg"))),
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/bricks/bricks3.jpg"))),
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/bricks/bricks4.jpg"))),
    };

}
