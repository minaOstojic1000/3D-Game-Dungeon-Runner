package dungeonrunner.interfaces;

import dungeonrunner.Player;

import java.util.ArrayList;
import java.util.List;

public interface IPowerUp extends IPickup {

    List<IPowerUp> powerUps = new ArrayList<>();

    void affect(Player player);

    static List<IPowerUp> getPowerUps() {
        return powerUps;
    }

    static void addPowerUp(IPowerUp powerUp) {
        powerUps.add(powerUp);
        pickups.add(powerUp);
    }

    static void removePowerUp(IPowerUp powerUp) {
        powerUps.remove(powerUp);
        pickups.remove(powerUp);
    }
}
