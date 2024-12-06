package live.decisionmaking.behaviortree;



public class Selector<GameData> extends CompositeTask<GameData> {
    @Override
    public boolean run(GameData gameData) {
        for (Task task:children)
        {
            if (task.run(gameData))
                return true;
        }

        return false;
    }
}
