package live.decisionmaking.behaviortree.demo;


import live.decisionmaking.behaviortree.Task;

/**
 * Created by dindar.oz on 10/26/2017.
 */
public class MoveToDoorTask implements Task<DemoGameData> {
    @Override
    public boolean run(DemoGameData gameData) {
        System.out.println("Moving to the door...");
        System.out.println("Moved to the door...");
        return true;
    }
}
