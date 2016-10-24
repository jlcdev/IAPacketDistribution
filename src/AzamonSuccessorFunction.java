import java.util.ArrayList;
import java.util.List;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

/**
 * Created by Javier Lopez on 20/10/16.
 */
public class AzamonSuccessorFunction implements SuccessorFunction{
    public AzamonSuccessorFunction(){}

    @Override
    public List getSuccessors(Object o) {
        AzamonState state = (AzamonState)o;
        ArrayList retVal = new ArrayList();
        AzamonHeuristic heuristic = new AzamonHeuristic();
        int nPaq = state.numeroPaquetes(), nTrans = state.numeroTransportes(), delta = nTrans + (nPaq - nTrans);
        for(int i = 0; i < nPaq; i++){
            for(int j = 0; j < nTrans; ++j){
                if(state.esMovible(i, j)){
                    AzamonState newState = new AzamonState(state.getPaqueteEnOferta(), state.getPesoDisponibleOfertas(), state.getPaquetes(), state.getTransporte());
                    newState.moverPaquete(i, j);
                    retVal.add(new Successor("MOVER(paquete: " + i + ", oferta: " + j + ", coste: " + heuristic.getHeuristicValue(newState) + ")", newState));
                }
                if(state.esIntercambiable(i, j)){
                    AzamonState newState = new AzamonState(state.getPaqueteEnOferta(), state.getPesoDisponibleOfertas(), state.getPaquetes(), state.getTransporte());
                    newState.intercambiarPaquete(i, j);
                    retVal.add(new Successor("INTERCAMBIO(paquete:" + i + ", paquete:" + j + ", coste: " + heuristic.getHeuristicValue(newState) + ")", newState));
                }
            }
            for(int k = nTrans; k < delta; ++k){
                if(state.esIntercambiable(i, k)){
                    AzamonState newState = new AzamonState(state.getPaqueteEnOferta(), state.getPesoDisponibleOfertas(), state.getPaquetes(), state.getTransporte());
                    newState.intercambiarPaquete(i, k);
                    retVal.add(new Successor("INTERCAMBIO(paquete:" + i + ", paquete:" + k + ", coste: " + heuristic.getHeuristicValue(newState) + ")", newState));
                }
            }
        }
        return retVal;
    }
}
