package live.decisionmaking.statemachine.demo;



import live.decisionmaking.statemachine.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by dindar.oz on 10/17/2017.
 */
public class StateMachineDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StateMachine sm = buildDemoStateMachine();
        sm.init();

        System.out.println("State Machines Demo");
        while (true)
        {
            // Display the current situation
            if (sm.getCurrentState() != null) {
                System.out.println("Current State: " + sm.getCurrentState().getActions().get(0));

                System.out.println("Transitions from this state:\n");

                for (Transition transition:sm.getCurrentState().getTransitions())
                {
                    SimpleTransition simpleTransition = (SimpleTransition)transition;
                    System.out.println( simpleTransition.getActions().get(0)+" to be "+ simpleTransition.getTargetState().getActions().get(0));
                }
                // Output all valid transitions from here

            } else {
                System.out.println("\n\nNo Current state\nNo Current transitions\n");
            }

            // Ask for input
            System.out.println("Which transition should be allowed to trigger (0[None]-15)\n");
            int option = scanner.nextInt();


            // Run one iteration of the state machine
            GameData gameData = new DemoGameData(option,0);
            List<Action> actions = sm.update(gameData);
            for (Action action:actions)
            {
                action.perform(gameData);
            }
        }
    }

    private static StateMachine buildDemoStateMachine() {
           final String allText[] = {
            // State text
                    "Entering State A", "In State A", "Exiting State A",
                    "Entering State B", "In State B", "Exiting State B",
                    "Entering State C", "In State C", "Exiting State C",
                    "Entering State D", "In State D", "Exiting State D",
                    "Entering State E", "In State E", "Exiting State E",
                    "Entering State F", "In State F", "Exiting State F",
                    "Entering State G", "In State G", "Exiting State G",

                    // Transition text
                    "Transition 1",
                    "Transition 2",
                    "Transition 3",
                    "Transition 4",
                    "Transition 5",
                    "Transition 6",
                    "Transition 7",
                    "Transition 8",
                    "Transition 9",
                    "Transition 10",
                    "Transition 11",
                    "Transition 12",
                    "Transition 13",
                    "Transition 14",
                    "Transition 15"
        };

        DemoState states[] = new DemoState[7];
        SimpleTransition transitions[] = new SimpleTransition[15];

        for (int i = 0; i < 7; i++) {
            DemoState demoState = new DemoState();
            demoState.setEntryTexts(Arrays.asList(allText[i*3]));
            demoState.setActionTexts(Arrays.asList(allText[i*3+1]));
            demoState.setExitTexts(Arrays.asList(allText[i*3+2]));
            states[i] = demoState;
        }

        for (int i = 0; i < 15; i++) {
            SimpleTransition transition = new SimpleTransition();
            transition.setActions(Arrays.asList(new DemoAction(allText[21+i])));

            transition.setCondition(new IntegerMatchCondition(i+1));
            transitions[i]= transition;
        }
        transitions[0].setTargetState( states[1]);
        transitions[1].setTargetState( states[1]);
        transitions[2].setTargetState( states[2]);
        transitions[3].setTargetState( states[3]);
        transitions[4].setTargetState( states[4]);
        transitions[5].setTargetState( states[0]);
        transitions[6].setTargetState( states[5]);
        transitions[7].setTargetState( states[6]);
        transitions[8].setTargetState( states[5]);
        transitions[9].setTargetState( states[2]);
        transitions[10].setTargetState( states[6]);
        transitions[11].setTargetState( states[4]);
        transitions[12].setTargetState( states[6]);
        transitions[13].setTargetState( states[4]);
        transitions[14].setTargetState( states[6]);

        states[0].setTransitions(Arrays.asList(transitions[0],transitions[4]));
        states[1].setTransitions(Arrays.asList(transitions[1],transitions[2],transitions[6],transitions[7]));
        states[2].setTransitions(Arrays.asList(transitions[3],transitions[8]));
        states[3].setTransitions(Arrays.asList(transitions[10]));
        states[4].setTransitions(Arrays.asList(transitions[13],transitions[14]));
        states[5].setTransitions(Arrays.asList(transitions[5],transitions[11],transitions[12]));
        states[6].setTransitions(Arrays.asList(transitions[9]));

        StateMachine stateMachine = new StateMachine();
        stateMachine.setInitialState(states[0]);

        return stateMachine;

    }
}
