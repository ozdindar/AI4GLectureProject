package live.decisionmaking.statemachine;



import java.util.List;

public class SimpleTransition  implements Transition{

    Condition condition;
    State targetState;
    List<Action> actions;


    @Override
    public boolean isTriggered(GameData gameData) {
        return condition.test(gameData);
    }

    @Override
    public State getTargetState() {
        return targetState;
    }

    @Override
    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public void setTargetState(State state) {
        this.targetState = state;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
