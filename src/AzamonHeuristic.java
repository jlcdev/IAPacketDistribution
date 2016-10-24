import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import aima.search.framework.HeuristicFunction;

/**
 * Created by Javier Lopez on 19/10/16.
 */
public class AzamonHeuristic implements HeuristicFunction{
    @Override
    public double getHeuristicValue(Object state){
        AzamonState azamonState = (AzamonState) state;
        double response = 0.0;
        int dias;
        for(int i = 0; i < azamonState.getPaqueteEnOferta().size(); ++i){
            Oferta o = azamonState.getTransporte().get(azamonState.getPaqueteEnOferta().get(i));
            Paquete p = azamonState.getPaquetes().get(i);
            dias = (o.getDias() == 1)?0:(o.getDias() > 1 && o.getDias() < 4)?1:2;
            response += o.getPrecio()* p.getPeso() + (0.25 * dias * p.getPeso());
        }
        return response;
    }
}
