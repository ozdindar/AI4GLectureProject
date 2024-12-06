package live.decisionmaking.behaviortree.demo;


import live.decisionmaking.behaviortree.Task;

/**
 * Created by dindar.oz on 10/26/2017.
 */
public class OpenDoorTask implements Task<DemoGameData> {
    @Override
    public boolean run(DemoGameData gameData) {
        System.out.println("Opening the door...");
        if (gameData.hasKey()) {
            System.out.println("Opened the door with the key!");
            return true;
        }
        else {
            System.out.println("No keys!.");
            return false;
        }
    }
}
