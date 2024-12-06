package live.decisionmaking.statemachine.demo;


import live.decisionmaking.statemachine.Action;
import live.decisionmaking.statemachine.GameData;

/**
 * Created by dindar.oz on 10/17/2017.
 */
public class DemoAction implements Action {
    private String text;

    public DemoAction(String text) {
        this.text = text;
    }

    @Override
    public void perform(GameData data) {
        System.out.println(text);
    }

    @Override
    public String toString() {
        return "Action-"+text;
    }
}
