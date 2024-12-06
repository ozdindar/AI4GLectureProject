package live.decisionmaking.statemachine;

public interface Condition {
    boolean test(GameData gameData);
}
