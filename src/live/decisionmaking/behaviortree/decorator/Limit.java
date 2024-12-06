package live.decisionmaking.behaviortree.decorator;

import live.decisionmaking.behaviortree.Task;

public class Limit<GameData> extends DecoratorTask<GameData> {

    int runLimit;
    int runSoFar=0;

    public Limit(Task<GameData> child) {
        super(child);
    }

    @Override
    public boolean run(GameData gameData) {
        if (runSoFar>runLimit)
            return false;

        runSoFar++;
        return child.run(gameData);
    }
}
