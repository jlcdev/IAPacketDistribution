import java.util.ArrayList;
import java.util.List;

import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * Created by Javier Lopez on 20/10/16.
 */
public class AzamonSuccessorFunctionHC implements SuccessorFunction{
    public AzamonSuccessorFunctionHC(){}

    @Override
    public List getSuccessors(Object o) {
        AzamonState parentState = (AzamonState)o;
        ArrayList<Successor> retVal = new ArrayList<>();
        HeuristicFunction heuristic = (parentState.getSelectedHeuristic() == 1)? new AzamonHeuristic(): new AzamonHeuristicHappiness();

        //MOVE OPERATOR
        for(int i = parentState.numeroPaquetes()-1; i >= 0; --i){
            for(int j = parentState.numeroTransportes()-1; j >= 0; --j){
                if(parentState.esMovible(i, j)){
                    AzamonState newState = new AzamonState(parentState);
                    newState.moverPaquete(i, j);
                    retVal.add(new Successor("MOVER(paquete: " + i + ", oferta: " + j + ", coste: " + heuristic.getHeuristicValue(newState) + ")", newState));
                }
            }
        }

        //CHANGE OPERATOR
        for(int i = parentState.numeroPaquetes()-1; i >= 0; --i){
            for(int j = parentState.numeroPaquetes()-1; j >= 0; --j){
                if(parentState.esIntercambiable(i, j)){
                    AzamonState newState = new AzamonState(parentState);
                    newState.intercambiarPaquete(i, j);
                    retVal.add(new Successor("INTERCAMBIO(paquete:" + i + ", paquete:" + j + ", coste: " + heuristic.getHeuristicValue(newState) + ")", newState));
                }
            }
        }
        return retVal;
    }
}
