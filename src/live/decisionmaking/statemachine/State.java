package live.decisionmaking.statemachine;

import java.util.List;

public interface State {

    List<Action> getActions();
    List<Action> getEntryActions();
    List<Action> getExitActions();

    List<Transition> getTransitions();
}
