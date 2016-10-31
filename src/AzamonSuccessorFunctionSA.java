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
            p, q, t, oi, oj;
        if(parentState.isOperadoresExtendido()){
            int PT = numPaq * numOf;
            int PP = numPaq * numPaq;
            int TT = numOf * numOf;
            int n = random.nextInt(PT + PP + TT);
            if(n < PT){
                do{
                    p = random.nextInt(numPaq);
                    t = random.nextInt(numOf);
                }while(!parentState.canMove(p,t));
                AzamonState newState = new AzamonState(parentState);
                newState.movePacket(p, t);
                retVal.add(new Successor("", newState));
            }else if(n < (PT + PP)){
                do{
                    p = random.nextInt(numPaq);
                    q = random.nextInt(numPaq);
                }while(p == q && !parentState.canInterchangePackets(p,q));
                AzamonState newState = new AzamonState(parentState);
                newState.interchangePacket(p, q);
                retVal.add(new Successor("", newState));
            }else{
                do{
                    oi = random.nextInt(numOf);
                    oj = random.nextInt(numOf);
                }while(oi == oj && !parentState.canExchangeOffer(oi, oj));
                AzamonState newState = new AzamonState(parentState);
                newState.exchangeOffer(oi, oj);
                retVal.add(new Successor("", newState));
            }
        }else{
            int PT = numPaq * numOf;
            int PP = numPaq * numPaq;
            if(random.nextInt(PT + PP) < PT){
                do{
                    p = random.nextInt(numPaq);
                    t = random.nextInt(numOf);
                }while(!parentState.canMove(p,t));
                AzamonState newState = new AzamonState(parentState);
                newState.movePacket(p, t);
                retVal.add(new Successor("", newState));
            }else{
                do{
                    p = random.nextInt(numPaq);
                    q = random.nextInt(numPaq);
                }while(p == q && !parentState.canInterchangePackets(p,q));
                AzamonState newState = new AzamonState(parentState);
                newState.interchangePacket(p, q);
                retVal.add(new Successor("", newState));
            }
        }
        return retVal;
    }
}
