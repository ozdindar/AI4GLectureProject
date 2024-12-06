package live.decisionmaking.behaviortree;

public interface Task<GameData> {

    boolean run(GameData gameData);

}
