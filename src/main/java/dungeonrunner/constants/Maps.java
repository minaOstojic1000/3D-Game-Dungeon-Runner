package dungeonrunner.constants;

import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.*;

public class Maps {

    private static final int[][] MAP1 = {
            {1,1,1,1,1,1,1,1,1},
            {1,0,8,0,1,0,0,0,1},
            {1,0,16,0,0,0,12,10,1},
            {1,0,16,1,5,0,4,0,1},
            {1,0,16,0,16,16,16,16,1},
            {1,3,14,0,7,1,0,1,1},
            {1,9,11,0,0,0,0,2,1},
            {1,1,1,1,6,1,1,1,1},
    };
    private static final int[][] MAP2 = {
            {1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,8,1},
            {1,0,1,1,3,0,3,0,1},
            {1,0,0,0,16,16,16,16,1},
            {1,5,0,4,1,1,1,12,1},
            {1,8,0,0,0,0,11,0,2},
            {1,9,3,0,0,6,1,0,1},
            {1,1,1,1,1,1,1,1,1},
    };
    private static final int[][] MAP3 = {
            {1,1,1,1,1,1,1,1,1},
            {1,2,11,0,0,0,11,8,1},
            {1,1,7,1,1,0,1,9,1},
            {1,0,0,0,3,0,1,1,1},
            {1,0,6,0,0,0,0,0,1},
            {1,12,5,0,4,1,1,3,1},
            {1,16,16,16,0,8,0,15,1},
            {1,1,1,1,1,1,1,1,1},
    };
    private static final int[][] MAP4 = {
            {1,1,1,1,1,7,1,1,1},
            {1,16,1,1,0,0,16,0,1},
            {1,16,0,0,0,6,16,0,1},
            {1,0,3,1,0,1,1,10,1},
            {1,8,0,15,0,1,9,8,1},
            {1,10,1,1,0,1,5,0,4},
            {1,0,2,3,0,14,0,0,1},
            {1,1,1,1,1,1,1,1,1},
    };

    public static final List<int[][]> MAPS = List.of(MAP1, MAP2, MAP3, MAP4);

    // PAIR<CTRL, DOOR>
    private static final HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>
            controlsDoorsPairs1 = new HashMap<>(Map.ofEntries(
        Map.entry(new Pair<>(2, 6), new Pair<>(2, 7)),
        Map.entry(new Pair<>(5, 2), new Pair<>(6, 2))
    )); // pair<row, col>

    private static final HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>
            controlsDoorsPairs2 = new HashMap<>(Map.ofEntries(
            Map.entry(new Pair<>(4, 7), new Pair<>(5, 6))
    )); // pair<row, col>

    private static final HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>
            controlsDoorsPairs3 = new HashMap<>(Map.ofEntries(
            Map.entry(new Pair<>(6, 7), new Pair<>(1, 2)),
            Map.entry(new Pair<>(5, 1), new Pair<>(1, 6))
    )); // pair<row, col>

    private static final HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>
            controlsDoorsPairs4 = new HashMap<>(Map.ofEntries(
            Map.entry(new Pair<>(4, 3), new Pair<>(3, 7)),
            Map.entry(new Pair<>(6, 5), new Pair<>(5, 1))
    )); // pair<row, col>

    public static final List<HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>> CTRL_DOORS_PAIRS =
            List.of(controlsDoorsPairs1, controlsDoorsPairs2, controlsDoorsPairs3, controlsDoorsPairs4);

    public static final Image[] MAP_BRICKS_IMAGES = {
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/bricks/bricks1.jpg"))),
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/bricks/bricks2.jpg"))),
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/bricks/bricks3.jpg"))),
            new Image(Objects.requireNonNull(Maps.class.getResourceAsStream("/dungeonrunner/bricks/bricks4.jpg"))),
    };

    public static final List<Pair<Double, Double>> PLAYER_START_POSITION = List.of
            (
                    new Pair<>(1.5, 1.5),
                    new Pair<>(1.5, 1.5),
                    new Pair<>(7.5, 4.5),
                    new Pair<>(4.5, 6.5)
            );

    public static final List<Pair<Double, Double>> PLAYER_START_DIRECTION = List.of
            (
                    new Pair<>(1.0, 0.0),
                    new Pair<>(1.0, 0.0),
                    new Pair<>(-1.0, 0.0),
                    new Pair<>(0.0, -1.0)
            );

}
