package dungeonrunner.generation;

import dungeonrunner.constants.Constants;
import dungeonrunner.figures.Doors;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DungeonMap {

    private int map[][];
    private List<Integer> canStep;
    private List<Integer> doorsTiles;
    private List<Integer> isWall;
    private HashMap<Pair<Integer, Integer>, Doors> doorsFigures;

    public DungeonMap (int map[][]) {
        this.map = new int[map.length][map[0].length];

        for ( int i = 0; i < map.length; i++ ) {
            for ( int j = 0; j < map[i].length; j++ ) {
                this.map[i][j] = map[i][j];
            }
        }

        doorsTiles = new ArrayList<>(Arrays.asList(
                Constants.DOORS_X,
                Constants.DOORS_Z
        ));

        canStep = new ArrayList<>(Arrays.asList(
                Constants.EMPTY,
                Constants.THORNS,
                Constants.KEY,
                Constants.GUARD
        ));

        canStep.addAll(doorsTiles);
        doorsFigures = new HashMap<>();
    }

    public List<Integer> getCanStep() {
        return canStep;
    }

    public int getRows ( ) {
        return this.map.length;
    }

    public int getCols ( ) {
        return this.map[0].length;
    }

    public int get ( int x, int y ) {
        return this.map[y][x];
    }

    public boolean canStep(int x, int y) {
        int tile = get(x, y);
        if (!canStep.contains(tile))
            return false;

        if (doorsTiles != null && doorsTiles.contains(tile)) {
            Doors doors = doorsFigures.get(new Pair<>(y, x));
            return !doors.getLocked();
        }
        return true;
    }

    public void setDoorsFigures(HashMap<Pair<Integer, Integer>, Doors> doorsFigures) {
        this.doorsFigures = doorsFigures;
    }

    public void addDoorsFigure(int x, int y, Doors doors) {
        this.doorsFigures.put(new Pair<>(x, y), doors);
    }
}