package live.decisionmaking.behaviortree.example;

import live.decisionmaking.behaviortree.Task;

public class PrintMessageTask implements Task<ExampleGameData> {
    @Override
    public boolean run(ExampleGameData exampleGameData) {
        System.out.println(exampleGameData.message);
        return true;
    }
}
