package comparators;

import IA.Azamon.Paquete;

import java.util.Comparator;

/**
 * Created by Javier Lopez on 23/10/16.
 */
public class PaquetePesoComparator implements Comparator<Paquete> {
    @Override
    public int compare(Paquete o1, Paquete o2) {
        if(o1.getPrioridad() < o2.getPrioridad()) return -1;
        if(o1.getPrioridad() > o2.getPrioridad()) return 1;
        return (o1.getPeso() < o2.getPeso())?-1:(o1.getPeso() == o2.getPeso())?0:1;
    }
}
