package dungeonrunner.interfaces;

import dungeonrunner.Player;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public interface IPickup {

    List<IPickup> pickups = new ArrayList<>();

    void affect(Player player);

    static List<IPickup> getPickups() {
        return pickups;
    }

    static void addPickup(IPickup pickup) { pickups.add(pickup); }

    static void removePickup(IPickup pickup) { pickups.remove(pickup); }

    boolean touchesPlayer(Player player);
}