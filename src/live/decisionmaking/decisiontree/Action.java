package live.decisionmaking.decisiontree;

public abstract class Action implements DecisionTreeNode {
    @Override
    public Action makeDecision(DecisionData data) {
        return this;
    }
}
