import aima.search.framework.HeuristicFunction;

/**
 * Created by Javier Lopez on 19/10/16.
 */
public class AzamonHeuristic implements HeuristicFunction{
    public AzamonHeuristic(){}

    @Override
    public double getHeuristicValue(Object state){
        AzamonState azamonState = (AzamonState) state;
        double response = 0.0;
        int numPaq = azamonState.getPaqueteEnOferta().length;
        for(int i = 0; i < numPaq; ++i){
            response += azamonState.calcularPrecioPaqueteOferta(i);
        }
        return response;
    }
}
