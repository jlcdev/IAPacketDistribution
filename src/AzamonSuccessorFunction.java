import aima.search.framework.Successor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mycol on 20/10/16.
 */
public class AzamonSuccessorFunction implements aima.search.framework.SuccessorFunction {
    @Override
    public List getSuccessors(Object o) {
        AzamonState state = (AzamonState)o;
        ArrayList retVal = new ArrayList();
        for (int i = 0; i < state.numeroPaquetes() ; i++) {
            AzamonState newState = new AzamonState(state);

            //TODO aplicar todos los operadores y añadirlos a retVal

            retVal.add(new Successor("", newState));
        }



        return retVal;
    }
}
