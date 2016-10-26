import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import comparators.PaquetePriorityComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Javier Lopez on 19/10/16.
 */
public class AzamonState {
    private int[] paqueteEnOferta;
    private double[] pesoDisponibleOfertas;
    //private ArrayList<Integer> paqueteEnOferta;
    //private ArrayList<Double> pesoDisponibleOfertas;
    private Paquetes paquetes;
    private Transporte transporte;

    public AzamonState(){}

    public AzamonState(final int[] oldPaqueteEnOferta, final double[] oldPesoDisponibleOfertas, final Paquetes paquetes, final Transporte transporte) {
        this.paquetes = paquetes;
        this.transporte = transporte;
        this.paqueteEnOferta = oldPaqueteEnOferta.clone();
        this.pesoDisponibleOfertas = oldPesoDisponibleOfertas.clone();
    }

    public void generateInitialStateSortPriority(int numPaq, int seedPaquetes, double proporcion, int seedOfertas){
        this.paquetes = new Paquetes(numPaq, seedPaquetes);
        this.transporte = new Transporte(this.paquetes, proporcion, seedOfertas);
        Collections.sort(this.paquetes, new PaquetePriorityComparator());
        this.paqueteEnOferta = new int[this.paquetes.size()];
        this.pesoDisponibleOfertas = new double[this.transporte.size()];
        for(int i = 0; i < this.transporte.size(); ++i){
            this.pesoDisponibleOfertas[i] = this.transporte.get(i).getPesomax();
        }
        for(int i = 0; i < this.paquetes.size(); ++i){
            for(int j = 0; j < this.transporte.size(); ++j){
                if(this.ponerPaquete(i, j)) break;
            }
        }
    }

    //Pone el paquete en el primer sitio valido
    public void generateInitialState(int numPaq, int seedPaquetes, double proporcion, int seedOfertas){
        this.paquetes = new Paquetes(numPaq, seedPaquetes);
        this.transporte = new Transporte(this.paquetes, proporcion, seedOfertas);
        this.paqueteEnOferta = new int[this.paquetes.size()];
        this.pesoDisponibleOfertas = new double[this.transporte.size()];
        for(int i = 0; i < this.transporte.size(); ++i){
            this.pesoDisponibleOfertas[i] = this.transporte.get(i).getPesomax();
        }
        for(int i = 0; i < this.paquetes.size(); ++i){
            for(int j = 0; j < this.transporte.size(); ++j){
                if(this.ponerPaquete(i, j)) break;
            }
        }
    }

    //Pone el paquete en un sitio aleatorio valido
    public void generateInitialStateRandom(int numPaq, int seedPaquetes, double proporcion, int seedOfertas){
        this.paquetes = new Paquetes(numPaq, seedPaquetes);
        this.transporte = new Transporte(this.paquetes, proporcion, seedOfertas);
        this.paqueteEnOferta = new int[this.paquetes.size()];
        int nOfertas = this.transporte.size();
        this.pesoDisponibleOfertas = new double[nOfertas];
        for(int i = 0; i < nOfertas; ++i){
            this.pesoDisponibleOfertas[i] = this.transporte.get(i).getPesomax();
        }
        Random random = new Random();
        int randOferta;
        for(int i = 0; i < this.paquetes.size(); ++i){
            randOferta = random.nextInt(nOfertas);
            while(! this.ponerPaquete(i, randOferta)) randOferta = random.nextInt(nOfertas);
        }
    }

    public boolean esMovible(int pi, int oj){
        return ( ((pesoDisponibleOfertas[oj] - paquetes.get(pi).getPeso()) > 0.0) && (prioridad(pi, oj) >= 0) );
    }

    public void moverPaquete(int pi, int oj){
        double peso = paquetes.get(pi).getPeso();
        int oi = paqueteEnOferta[pi];

        paqueteEnOferta[pi] = oj;
        pesoDisponibleOfertas[oi] += peso;
        pesoDisponibleOfertas[oj] -= peso;
    }

    public boolean esIntercambiable(int pi, int pj) {
        double pesoi = paquetes.get(pi).getPeso();
        double pesoj = paquetes.get(pj).getPeso();
        int ofertai = paqueteEnOferta[pi];
        int ofertaj = paqueteEnOferta[pj];
        double pesoLibrei = pesoDisponibleOfertas[ofertai];
        double pesoLibrej = pesoDisponibleOfertas[ofertaj];

        boolean condp = this.prioridad(pi, ofertaj) >= 0 && this.prioridad(pj, ofertai) >= 0;
        boolean condi = (pesoLibrei + pesoi - pesoj) > 0.0;
        boolean condj = (pesoLibrej + pesoj - pesoi) > 0.0;
        return condi && condj && condp;
    }

    public void intercambiarPaquete(int pi, int pj){
        double pesoi = paquetes.get(pi).getPeso();
        double pesoj = paquetes.get(pj).getPeso();
        int ofertai = paqueteEnOferta[pi];
        int ofertaj = paqueteEnOferta[pj];

        //intercambio de ofertas
        paqueteEnOferta[pj] = ofertai;
        paqueteEnOferta[pi] = ofertaj;

        //actualizacion pesos
        pesoDisponibleOfertas[ofertai] += (pesoi - pesoj);
        pesoDisponibleOfertas[ofertaj] -= (pesoi - pesoj);
    }

    @Override
    public String toString() {
        String s = "AzamonState{";

        String spaq = "[";
        for (Paquete p: this.paquetes) {
            spaq += ("peso(" + p.getPeso() + ") pr(" +p.getPrioridad() + "), ");
        }
        spaq += "]";

        String spaqof = "[";
        for (Integer o: this.paqueteEnOferta)
        {
            spaqof += (o.toString() + ", ");
        }
        spaqof += "]";

        String sof = "[";
        for (Oferta of: this.transporte)
        {
            sof += ("pmax(" +of.getPesomax() +") dias(" +of.getDias() + "), ");
        }
        sof += "]";

        String spof = "[";
        for (Double peso: this.pesoDisponibleOfertas)
        {
            spof += (peso.toString() + ", ");
        }
        spof += "]";
        return s + "\n" + spaq + "; \n" + spaqof + "; \n" + sof + "; \n" + spof + "}";
    }

    public int numeroPaquetes () {
        return this.paqueteEnOferta.length;
    }
    public int numeroTransportes(){ return this.transporte.size(); }

    /**
     * 0 = 0 -1 -2 -3 -4
     * 1 = 2  1  0 -1 -2
     * 2 = 4  3  2  1  0
     */
    public int calcDiasFelicidad(int pi, int oj) {
        int prioridad = this.paquetes.get(pi).getPrioridad(), dias = this.transporte.get(oj).getDias();
        return (1-dias) + (2*prioridad);
    }

    //o.getDias() = {1, 2, 3, 4, 5}  p.getPrioridad() = {0, 1, 2}
    //pr(0) = dias(1)   pr(1) = dias(2, 3)     pr(2) = dias(4, 5)
    private int prioridad(int pi, int oj) {
        int prioridad = this.paquetes.get(pi).getPrioridad(), dias = this.transporte.get(oj).getDias();
        if (dias == 1) return prioridad;
        else if(dias <= 3) return prioridad - 1;
        else return prioridad - 2;
    }

    private boolean ponerPaquete(int pi, int oj){
        double deltaPeso = this.pesoDisponibleOfertas[oj] - this.paquetes.get(pi).getPeso();
        if(deltaPeso > 0.0 && (this.prioridad(pi, oj) == 0)) {
            this.paqueteEnOferta[pi] = oj;
            this.pesoDisponibleOfertas[oj] = deltaPeso;
            return true;
        }
        return false;
    }

    public double calcularPrecioPaqueteOferta(int paquete){
        Oferta o = this.getTransporte().get(this.getPaqueteEnOferta()[paquete]);
        double pesoPaq = this.getPaquetes().get(paquete).getPeso();
        return ((o.getPrecio() * pesoPaq) + (0.25 * ((o.getDias() == 1)?0:(o.getDias() > 1 && o.getDias() < 4)?1:2) * pesoPaq));
    }

    public int[] getPaqueteEnOferta() {
        return paqueteEnOferta;
    }

    public double[] getPesoDisponibleOfertas() {
        return pesoDisponibleOfertas;
    }

    public Paquetes getPaquetes() {
        return paquetes;
    }

    public Transporte getTransporte() {
        return transporte;
    }
}
