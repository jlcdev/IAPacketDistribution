import aima.search.framework.GoalTest;

/**
 * Created by Javier Lopez on 19/10/16.
 */
public class AzamonGoalTest implements GoalTest{
    @Override
    public boolean isGoalState(Object state) {
        return ((AzamonState) state).isGoal();
    }
}
