import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Javier Lopez on 23/10/16.
 */
public class AzamonSuccessorFunctionSimulatedAnnealing implements SuccessorFunction{
    public AzamonSuccessorFunctionSimulatedAnnealing(){}

    @Override
    public List getSuccessors(Object o){
        AzamonState azamonState = (AzamonState) o;
        ArrayList retVal = new ArrayList();
        AzamonHeuristic heuristic = new AzamonHeuristic();
        Random random = new Random();
        int i = random.nextInt(azamonState.getPaquetes().size()), j;
        do{
            j = random.nextInt(azamonState.getTransporte().size());
        }while(i == j);

        if(azamonState.esMovible(i, j)){
            AzamonState newState = new AzamonState(azamonState.getPaqueteEnOferta(), azamonState.getPesoDisponibleOfertas(), azamonState.getPaquetes(), azamonState.getTransporte());
            newState.moverPaquete(i, j);
            retVal.add(new Successor("MOVER(" + i + ", " + j + ", coste: " + heuristic.getHeuristicValue(newState) + ")", newState));
        }
        if(azamonState.esIntercambiable(i, j)){
            AzamonState newState = new AzamonState(azamonState.getPaqueteEnOferta(), azamonState.getPesoDisponibleOfertas(), azamonState.getPaquetes(), azamonState.getTransporte());
            newState.intercambiarPaquete(i, j);
            retVal.add(new Successor("INTERCAMBIO(" + i + ", " + j + ", coste: " + heuristic.getHeuristicValue(newState) + ")", newState));
        }

        return retVal;
    }
}
