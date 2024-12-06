package live.decisionmaking.behaviortree.demo;


import live.decisionmaking.behaviortree.Task;

/**
 * Created by dindar.oz on 10/26/2017.
 */
public class DoorOpenTask implements Task<DemoGameData> {
    @Override
    public boolean run(DemoGameData gameData) {
        System.out.println("Is door open?");
        if (gameData.isDoorOpen()) {
            System.out.println("Door is open!");
            return true;
        }
        System.out.println("Door is not open!");
        return false;
    }
}
