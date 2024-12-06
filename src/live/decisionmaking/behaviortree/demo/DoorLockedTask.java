package live.decisionmaking.behaviortree.demo;


import live.decisionmaking.behaviortree.Task;

/**
 * Created by dindar.oz on 10/26/2017.
 */
public class DoorLockedTask implements Task<DemoGameData> {
    @Override
    public boolean run(DemoGameData gameData) {
        System.out.println("Is door locked?");
        if (gameData.isDoorLocked()) {
            System.out.println("Door is locked!");
            return true;
        }
        System.out.println("Door is not locked!");
        return false;
    }
}
