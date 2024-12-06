package live.decisionmaking.behaviortree.demo;

/**
 * Created by dindar.oz on 10/26/2017.
 */
public class DemoGameData {


    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    private boolean hasKey= false;

    public boolean hasKey() {

        return hasKey;
    }

    enum DoorState { Open, Closed, Locked};

    DoorState doorState = DoorState.Open;

    public void setDoorState(DoorState doorState) {
        this.doorState = doorState;
    }

    public boolean isDoorOpen() {
        return (doorState==DoorState.Open);
    }

    public boolean isDoorLocked() {
        return doorState == DoorState.Locked;
    }

}
