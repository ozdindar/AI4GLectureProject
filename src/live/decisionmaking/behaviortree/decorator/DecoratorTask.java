package live.decisionmaking.behaviortree.decorator;

import live.decisionmaking.behaviortree.Task;


public abstract class DecoratorTask<GameData> implements Task<GameData> {
    Task<GameData> child;

    public DecoratorTask(Task<GameData> child) {
        this.child = child;
    }
}
