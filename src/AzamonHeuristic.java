import aima.search.framework.HeuristicFunction;

/**
 * Created by Javier Lopez on 19/10/16.
 */
public class AzamonHeuristic implements HeuristicFunction{
    @Override
    public double getHeuristicValue(Object state) {
        //TODO create heuristic function
        AzamonState as = (AzamonState) state;
        return 0;
    }
}
