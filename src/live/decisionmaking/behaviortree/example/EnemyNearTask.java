package live.decisionmaking.behaviortree.example;

import live.decisionmaking.behaviortree.Task;

public class EnemyNearTask implements Task<ExampleGameData> {

    int maxDistance;

    public EnemyNearTask(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean run(ExampleGameData exampleGameData) {
        return exampleGameData.pos.distance(exampleGameData.enemyPos)<maxDistance;
    }
}
