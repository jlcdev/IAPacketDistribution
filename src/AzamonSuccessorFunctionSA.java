import aima.search.framework.HeuristicFunction;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Javier Lopez on 23/10/16.
 */
public class AzamonSuccessorFunctionSA implements SuccessorFunction{
    public AzamonSuccessorFunctionSA(){}

    @Override
    public List getSuccessors(Object o){
        AzamonState parentState = (AzamonState) o;
        ArrayList<Successor> retVal = new ArrayList<>();
        HeuristicFunction heuristic = (parentState.getSelectedHeuristic() == 1)? new AzamonHeuristic(): new AzamonHeuristicHappiness();
        Random random = AzamonState.getRandom();
        int p, q, t;
        for(int i = parentState.getStiter(); i > 0; --i){
            if(random.nextInt(2) == 0){
                p = random.nextInt(parentState.getPaquetes().size());
                t = random.nextInt(parentState.getTransporte().size());
                if(!parentState.esMovible(p, t)) continue;
                AzamonState newState = new AzamonState(parentState);
                newState.moverPaquete(p, t);
                retVal.add(new Successor("MOVER(" + p + ", " + t + ", coste: " + heuristic.getHeuristicValue(newState) + ")", newState));
            }else{
                p = random.nextInt(parentState.getPaquetes().size());
                do{
                    q = random.nextInt(parentState.getPaquetes().size());
                }while(p == q);
                if(!parentState.esIntercambiable(p, q)) continue;
                AzamonState newState = new AzamonState(parentState);
                newState.intercambiarPaquete(p, q);
                retVal.add(new Successor("INTERCAMBIO(" + p + ", " + q + ", coste: " + heuristic.getHeuristicValue(newState) + ")", newState));
            }
        }
        return retVal;
    }
}
