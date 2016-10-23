import IA.Azamon.Paquete;

import java.util.Comparator;

/**
 * Created by Javier Lopez on 23/10/16.
 */
public class PaquetePriorityComparator implements Comparator<Paquete> {
    @Override
    public int compare(Paquete o1, Paquete o2) {
        return (o1.getPrioridad() < o2.getPrioridad())? -1: (o1.getPrioridad() == o2.getPrioridad())? 0 : 1;
    }
}
