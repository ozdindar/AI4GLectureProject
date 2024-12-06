package live.decisionmaking.behaviortree;



public class Sequence<GameData> extends CompositeTask<GameData> {
    @Override
    public boolean run(GameData gameData) {
        for (Task task:children)
        {
            if (!task.run(gameData))
                return false;
        }

        return true;
    }
}
