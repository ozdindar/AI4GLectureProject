package live.decisionmaking.statemachine;

import java.util.List;

public interface Transition {

    boolean isTriggered(GameData gameData);

    State getTargetState();

    List<Action> getActions();
}
