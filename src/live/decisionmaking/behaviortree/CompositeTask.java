package live.decisionmaking.behaviortree;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeTask<GameData> implements Task<GameData> {
    List<Task> children;

    public CompositeTask() {
        children = new ArrayList<>();
    }

    public CompositeTask(List<Task> children) {
        this.children = children;
    }

    public void addChild(Task task)
    {
        children.add(task);
    }
}
