package live.decisionmaking.statemachine.demo;

import live.decisionmaking.statemachine.Condition;
import live.decisionmaking.statemachine.GameData;

public class IntegerMatchCondition implements Condition {

    int value;
    public IntegerMatchCondition(int val) {
        value = val;
    }

    @Override
    public boolean test(GameData gameData) {
        return (value== ((DemoGameData)gameData).intVal);
    }
}
