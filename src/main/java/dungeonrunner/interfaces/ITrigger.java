package dungeonrunner.interfaces;

import java.util.List;

public interface ITrigger {

    default void trigger() {
        for (ITargetAction action : getActions()) {
            action.execute();
        }
    }

    void addAction(ITargetAction action);

    void removeAction(ITargetAction action);

    List<ITargetAction> getActions();
}