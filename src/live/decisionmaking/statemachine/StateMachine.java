package live.decisionmaking.statemachine;





import live.decisionmaking.statemachine.demo.DemoState;

import java.util.ArrayList;
import java.util.List;

public class StateMachine {
    State initialState;
    State currentState;

    public List<Action> update(GameData gameData)
    {
        List<Transition> transitions = currentState.getTransitions();

        List<Action> actions = new ArrayList<>();

        boolean aTransitionOccurred = false;

        for (Transition transition: transitions)
        {
            if (transition.isTriggered(gameData))
            {
                actions.addAll(currentState.getExitActions());
                currentState = transition.getTargetState();
                actions.addAll(currentState.getEntryActions());
                aTransitionOccurred = true;
                break;
            }
        }

        if (!aTransitionOccurred)
            actions = currentState.getActions();

        return actions;

    }

    public State getCurrentState() {
        return currentState;
    }

    public void init() {
        currentState = initialState;
    }

    public void setInitialState(DemoState state) {
        this.initialState = state;
    }
}
