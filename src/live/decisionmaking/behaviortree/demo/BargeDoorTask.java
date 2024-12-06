package live.decisionmaking.behaviortree.demo;


import live.decisionmaking.behaviortree.Task;

/**
 * Created by dindar.oz on 10/26/2017.
 */
public class BargeDoorTask implements Task<DemoGameData> {
    @Override
    public boolean run(DemoGameData gameData) {
        System.out.println("Barging the door...");
        return true;
    }
}
