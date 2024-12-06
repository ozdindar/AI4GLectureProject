package live.decisionmaking.decisiontree.demo;


import live.decisionmaking.decisiontree.Action;
import live.decisionmaking.decisiontree.DecisionData;

/**
 * Created by dindar.oz on 10/11/2017.
 */
public class PrintAction extends Action {
    String text;

    public PrintAction(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "[ "+ text+ " ]";
    }

    @Override
    public Action makeDecision(DecisionData data) {
        System.out.println("[DECIDED ACTION]:"+ text);
        return this;
    }
}
