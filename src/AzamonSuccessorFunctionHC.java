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
        int numPaq = parentState.numeroPaquetes()-1;
        int numTrans = parentState.numeroTransportes()-1;

        //MOVE OPERATOR
        for(int i = numPaq; i >= 0; --i){
            for(int j = numTrans; j >= 0; --j){
                if(parentState.canMove(i, j)){
                    AzamonState newState = new AzamonState(parentState);
                    newState.movePacket(i, j);
                    retVal.add(new Successor("MOVE(packet: " + i + ", offer: " + j + ", cost: " + heuristic.getHeuristicValue(newState) + ")", newState));
                }
            }
        }

        //CHANGE OPERATOR
        for(int i = numPaq; i >= 0; --i){
            for(int j = numPaq; j >= 0; --j){
                if(parentState.canInterchangePackets(i, j)){
                    AzamonState newState = new AzamonState(parentState);
                    newState.interchangePacket(i, j);
                    retVal.add(new Successor("INTERCHANGE(packet:" + i + ", packet:" + j + ", cost: " + heuristic.getHeuristicValue(newState) + ")", newState));
                }
            }
        }

        //EXCHANGE CONTAINER OPERATOR
        if(parentState.isOperadoresExtendido()){
            for (int i = numTrans; i >= 0; --i) {
                for (int j = i - 1; j >= 0; --j) {
                    if (parentState.canExchangeOffer(i, j)) {
                        AzamonState newState = new AzamonState(parentState);
                        newState.exchangeOffer(i, j);
                        retVal.add(new Successor("EXCHANGE(offer: " + i + ", offer: " + j + ", cost: " + heuristic.getHeuristicValue(newState) + ")", newState));
                    }
                }
            }
        }

        return retVal;

    }
}
