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
        List retVal = new ArrayList<>();
        Random random = AzamonState.getRandom();
        int numPaq = parentState.numeroPaquetes(),
            numOf = parentState.numeroTransportes(),
            p, q, t;
        if(random.nextInt(numPaq + numOf) >= numPaq){
            do{
                p = random.nextInt(numPaq);
                t = random.nextInt(numOf);
            }while(!parentState.esMovible(p,t));
            AzamonState newState = new AzamonState(parentState);
            newState.moverPaquete(p, t);
            retVal.add(new Successor("", newState));
        }else{
            do{
                p = random.nextInt(numPaq);
                q = random.nextInt(numPaq);
            }while(p == q && !parentState.esIntercambiable(p,q));
            AzamonState newState = new AzamonState(parentState);
            newState.intercambiarPaquete(p, q);
            retVal.add(new Successor("", newState));
        }
        return retVal;
    }
}
