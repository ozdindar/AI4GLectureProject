package live.decisionmaking.behaviortree.demo;


import live.decisionmaking.behaviortree.CompositeTask;
import live.decisionmaking.behaviortree.Selector;
import live.decisionmaking.behaviortree.Sequence;
import live.decisionmaking.behaviortree.Task;

/**
 * Created by dindar.oz on 10/26/2017.
 */
public class BehaviorTreeDemo {
    public static void main(String[] args) {
        Task<DemoGameData> bt = buildDemoBT();
        DemoGameData gameData = generateGameData();

        bt.run(gameData);
    }

    private static DemoGameData generateGameData() {
        DemoGameData demoGameData = new DemoGameData();
        demoGameData.setDoorState(DemoGameData.DoorState.Locked);
        demoGameData.setHasKey(true);

        return demoGameData;
    }

    private static Task buildDemoBT() {
        CompositeTask root = new Selector<DemoGameData>();

        CompositeTask doorOpenSequence = new Sequence<DemoGameData>();

        Task doorOpenTask = new DoorOpenTask();
        Task moveInTask = new MoveInTask();

        doorOpenSequence.addChild(doorOpenTask);
        doorOpenSequence.addChild(moveInTask);

        CompositeTask doorNotOpenSequence = new Sequence<DemoGameData>();

        CompositeTask openDoorSelector = new Selector<DemoGameData>();
        CompositeTask doorLockedSequence = new Sequence<DemoGameData>();
        CompositeTask bargeDoorSequence = new Sequence<DemoGameData>();

        doorLockedSequence.addChild(new DoorLockedTask());
        doorLockedSequence.addChild(new OpenDoorTask());
        bargeDoorSequence.addChild(new BargeDoorTask());
        bargeDoorSequence.addChild(doorOpenTask);

        openDoorSelector.addChild(doorLockedSequence);
        openDoorSelector.addChild(bargeDoorSequence);

        doorNotOpenSequence.addChild(new MoveToDoorTask());
        doorNotOpenSequence.addChild(openDoorSelector );
        doorNotOpenSequence.addChild(moveInTask);


        root.addChild(doorOpenSequence);
        root.addChild(doorNotOpenSequence);

        return root;
    }
}
