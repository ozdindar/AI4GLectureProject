package live.decisionmaking.statemachine.demo;



import live.decisionmaking.statemachine.Action;
import live.decisionmaking.statemachine.State;
import live.decisionmaking.statemachine.Transition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dindar.oz on 10/17/2017.
 */
public class DemoState implements State {

    List<String> actionTexts;
    List<String> entryTexts;
    List<String> exitTexts;

    List<Transition> transitions;

    public void setActionTexts(List<String> actionTexts) {
        this.actionTexts = actionTexts;
    }

    public void setEntryTexts(List<String> entryTexts) {
        this.entryTexts = entryTexts;
    }

    public void setExitTexts(List<String> exitTexts) {
        this.exitTexts = exitTexts;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    @Override
    public List<Action> getActions() {
        List<Action> actions = new ArrayList<>(actionTexts.size());

        for (String actionText:actionTexts)
        {
            actions.add(new DemoAction(actionText));
        }
        return actions;
    }

    @Override
    public List<Action> getEntryActions() {
        List<Action> actions = new ArrayList<>(entryTexts.size());

        for (String actionText:entryTexts)
        {
            actions.add(new DemoAction(actionText));
        }
        return actions;
    }

    @Override
    public List<Action> getExitActions() {
        List<Action> actions = new ArrayList<>(exitTexts.size());

        for (String actionText:exitTexts)
        {
            actions.add(new DemoAction(actionText));
        }
        return actions;
    }

    @Override
    public List<Transition> getTransitions() {
        return transitions;
    }


}
