import aima.search.framework.Successor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mycol on 20/10/16.
 */
public class AzamonSuccessorFunction implements aima.search.framework.SuccessorFunction{
    public AzamonSuccessorFunction(){}

    @Override
    public List getSuccessors(Object o) {
        AzamonState state = (AzamonState)o;
        ArrayList retVal = new ArrayList();
        AzamonHeuristic heuristic = new AzamonHeuristic();
        for(int i = 0; i < state.numeroPaquetes(); i++){
            for(int j = 0; j < state.numeroTransportes(); ++j){
                if(state.esMovible(i, j)){
                    AzamonState newState = new AzamonState(state.getPaqueteEnOferta(), state.getPesoDisponibleOfertas(), state.getPaquetes(), state.getTransporte());
                    newState.moverPaquete(i, j);
                    retVal.add(new Successor("MOVER(" + i + ", " + j + ", coste: " + heuristic.getHeuristicValue(newState) + ")", newState));
                }
                if(state.esIntercambiable(i, j)){
                    AzamonState newState = new AzamonState(state.getPaqueteEnOferta(), state.getPesoDisponibleOfertas(), state.getPaquetes(), state.getTransporte());
                    newState.intercambiarPaquete(i, j);
                    retVal.add(new Successor("INTERCAMBIO(" + i + ", " + j + ", coste: " + heuristic.getHeuristicValue(newState) + ")", newState));
                }
            }
        }
        return retVal;
    }
}
