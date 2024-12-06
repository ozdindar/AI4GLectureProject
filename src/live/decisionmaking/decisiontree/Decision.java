package live.decisionmaking.decisiontree;

public abstract class Decision implements DecisionTreeNode {

    protected DecisionTreeNode trueBranch;
    protected DecisionTreeNode falseBranch;

    public Decision(DecisionTreeNode trueBranch, DecisionTreeNode falseBranch) {
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    protected abstract DecisionTreeNode getBranch(DecisionData testValue);

    @Override
    public Action makeDecision(DecisionData data) {

        DecisionTreeNode child = getBranch(data);

        return child.makeDecision(data);
    }
}
