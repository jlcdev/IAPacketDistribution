import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;
import comparators.PaquetePesoComparator;
import comparators.PaquetePriorityComparator;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Javier Lopez on 19/10/16.
 */
public class AzamonState {
    private static Random random;
    private int[] paqueteEnOferta;
    private double[] pesoDisponibleOfertas;
    private Paquetes paquetes;
    private Transporte transporte;
    private int selectedHeuristic;

    public AzamonState(){}

    public AzamonState(final AzamonState oldAzamonState){
        this.paquetes = oldAzamonState.paquetes;
        this.transporte = oldAzamonState.transporte;
        this.paqueteEnOferta = oldAzamonState.paqueteEnOferta.clone();
        this.pesoDisponibleOfertas = oldAzamonState.pesoDisponibleOfertas.clone();
        this.selectedHeuristic = oldAzamonState.selectedHeuristic;
    }

    // NEW GENERATOR FUNCTIONS
    public void generatorA(int numPaq, int seedPaquetes, double proporcion, int seedOfertas){
        random = new Random((long)(seedPaquetes * seedOfertas));
        this.paquetes = new Paquetes(numPaq, seedPaquetes);
        this.transporte = new Transporte(this.paquetes, proporcion, seedOfertas);
        Collections.sort(this.paquetes, new PaquetePriorityComparator());
        this.paqueteEnOferta = new int[this.paquetes.size()];
        this.pesoDisponibleOfertas = new double[this.transporte.size()];
        boolean[] assigned = new boolean[this.paquetes.size()];
        Arrays.fill(assigned, false);
        for(int i = 0; i < this.transporte.size(); ++i){
            this.pesoDisponibleOfertas[i] = this.transporte.get(i).getPesomax();
        }
        int nPaq = this.paquetes.size(), nTrans = this.transporte.size();
        // prio == envio
        for(int i = 0; i < nPaq; ++i){
            for(int j = 0; j < nTrans; ++j){
                if(this.ponerPaquete(i, j, true)){
                    assigned[i] = true;
                    break;
                }
            }
        }
        // assign prio <= envio to unassigned paq.
        for(int i = nPaq -1; i >= 0; --i){
            if(assigned[i]) continue;
            for(int j = nTrans -1; j >= 0; --j){
                if(this.ponerPaquete(i, j, false)) break;
            }
        }
    }

    public void generatorB(final int numPaq, final int seedPaquetes, final double proporcion, final int seedOfertas){
        random = new Random();
        this.paquetes = new Paquetes(numPaq, seedPaquetes);
        this.transporte = new Transporte(this.paquetes, proporcion, seedOfertas);
        int transSize = this.transporte.size();
        this.pesoDisponibleOfertas = new double[transSize];
        for(int i = 0; i < this.transporte.size(); ++i){
            this.pesoDisponibleOfertas[i] = this.transporte.get(i).getPesomax();
        }
        this.paqueteEnOferta = new int[this.paquetes.size()];
        int pSelected = 0, numIterMax = numPaq * numPaq, iter = 0;

        while(iter < numIterMax && pSelected < numPaq){
            int o = random.nextInt(transSize);
            if(this.ponerPaquete(pSelected, o, false)){
                ++pSelected;
                iter = 0;
            }
            ++iter;
        }
        //check conditions
        if(pSelected < numPaq && !isGenerationCorrect()){
            this.transporte = null;
            this.paquetes = null;
            generatorB(numPaq, seedPaquetes, proporcion, seedOfertas);
            return;
        }else{
            System.out.println("Generation successfully");
        }
    }
    //solo verificar el caso de que la prioridad sea superior
    private boolean isGenerationCorrect(){
        for(int i = this.paqueteEnOferta.length -1; i >= 0; --i){
            int prioridad = paquetes.get(i).getPrioridad();
            int dias = transporte.get(this.paqueteEnOferta[i]).getDias();
            switch(prioridad){
                case 0:
                    if(dias > 1) return false;
                    break;
                case 1:
                    if(dias < 2 && dias > 3) return false;
                    break;
                case 2:
                    if(dias < 4) return false;
                    break;
            }
        }
        return true;
    }

    public void generatorC(int numPaq, int seedPaquetes, double proporcion, int seedOfertas){
        random = new Random((long)(seedPaquetes * seedOfertas));
        this.paquetes = new Paquetes(numPaq, seedPaquetes);
        this.transporte = new Transporte(this.paquetes, proporcion, seedOfertas);
        Collections.sort(this.paquetes, new PaquetePesoComparator());
        this.paqueteEnOferta = new int[this.paquetes.size()];
        this.pesoDisponibleOfertas = new double[this.transporte.size()];
        boolean[] assigned = new boolean[this.paquetes.size()];
        Arrays.fill(assigned, false);
        for(int i = 0; i < this.transporte.size(); ++i){
            this.pesoDisponibleOfertas[i] = this.transporte.get(i).getPesomax();
        }
        int nPaq = this.paquetes.size(), nTrans = this.transporte.size();
        // prio == envio
        for(int i = 0; i < nPaq; ++i){
            for(int j = 0; j < nTrans; ++j){
                if(this.ponerPaquete(i, j, true)){
                    assigned[i] = true;
                    break;
                }
            }
        }
        // assign prio <= envio to unassigned paq.
        for(int i = nPaq -1; i >= 0; --i){
            if(assigned[i]) continue;
            for(int j = nTrans -1; j >= 0; --j){
                if(this.ponerPaquete(i, j, false)) break;
            }
        }
    }

    //Funciones generadoras

    public void generateInitialStateSortPriority(int numPaq, int seedPaquetes, double proporcion, int seedOfertas){
        random = new Random((long)(seedPaquetes * seedOfertas));
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
                if(this.ponerPaquete(i, j, true)) break;
            }
        }
    }
    //Pone el paquete en el primer sitio valido
    public void generateInitialState(int numPaq, int seedPaquetes, double proporcion, int seedOfertas){
        random = new Random((long)(seedPaquetes * seedOfertas));
        this.paquetes = new Paquetes(numPaq, seedPaquetes);
        this.transporte = new Transporte(this.paquetes, proporcion, seedOfertas);
        this.paqueteEnOferta = new int[this.paquetes.size()];
        this.pesoDisponibleOfertas = new double[this.transporte.size()];
        for(int i = 0; i < this.transporte.size(); ++i){
            this.pesoDisponibleOfertas[i] = this.transporte.get(i).getPesomax();
        }
        for(int i = 0; i < this.paquetes.size(); ++i){
            for(int j = 0; j < this.transporte.size(); ++j){
                if(this.ponerPaquete(i, j, true)) break;
            }
        }
    }
    //Pone el paquete en un sitio aleatorio valido
    public void generateInitialStateRandom(int numPaq, int seedPaquetes, double proporcion, int seedOfertas){
        random = new Random((long)(seedPaquetes * seedOfertas));
        this.paquetes = new Paquetes(numPaq, seedPaquetes);
        this.transporte = new Transporte(this.paquetes, proporcion, seedOfertas);
        this.paqueteEnOferta = new int[this.paquetes.size()];
        int nOfertas = this.transporte.size();
        this.pesoDisponibleOfertas = new double[nOfertas];
        for(int i = 0; i < nOfertas; ++i){
            this.pesoDisponibleOfertas[i] = this.transporte.get(i).getPesomax();
        }
        int randOferta;
        for(int i = 0; i < this.paquetes.size(); ++i){
            randOferta = random.nextInt(nOfertas);
            while(! this.ponerPaquete(i, randOferta, true)) randOferta = random.nextInt(nOfertas);
        }
    }


    //Condiciones de aplicabilidad

    public boolean esMovible(int pi, int oj){
        return ( ((pesoDisponibleOfertas[oj] - paquetes.get(pi).getPeso()) > 0.0) && (calcDiasFelicidad(pi, oj) >= 0) );
    }

    public boolean esIntercambiable(int pi, int pj) {
        double pesoi = paquetes.get(pi).getPeso();
        double pesoj = paquetes.get(pj).getPeso();
        int ofertai = paqueteEnOferta[pi];
        int ofertaj = paqueteEnOferta[pj];
        double pesoLibrei = pesoDisponibleOfertas[ofertai];
        double pesoLibrej = pesoDisponibleOfertas[ofertaj];

        boolean condp = this.calcDiasFelicidad(pi, ofertaj) >= 0 && this.calcDiasFelicidad(pj, ofertai) >= 0;
        boolean condi = (pesoLibrei + pesoi - pesoj) > 0.0;
        boolean condj = (pesoLibrej + pesoj - pesoi) > 0.0;
        return condi && condj && condp;
    }


    //Operaciones

    public void moverPaquete(int pi, int oj){
        double peso = paquetes.get(pi).getPeso();
        int oi = paqueteEnOferta[pi];

        paqueteEnOferta[pi] = oj;
        pesoDisponibleOfertas[oi] += peso;
        pesoDisponibleOfertas[oj] -= peso;
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

    //Si estricto = true solo permitimos prio == envio, sino permitimos prio <= envio
    private boolean ponerPaquete(int pi, int oj, boolean estricto){
        double deltaPeso = this.pesoDisponibleOfertas[oj] - this.paquetes.get(pi).getPeso();
        int prioridad = calcDiasFelicidad(pi, oj);
        boolean control = (estricto)?(prioridad >= 0 && prioridad <= 1):(prioridad >= 0);
        if(deltaPeso > 0.0 && control) {
            this.paqueteEnOferta[pi] = oj;
            this.pesoDisponibleOfertas[oj] = deltaPeso;
            return true;
        }
        return false;
    }

    /**
     * 0 = 0 -1 -2 -3 -4
     * 1 = 2  1  0 -1 -2
     * 2 = 4  3  2  1  0
     */
    public int calcDiasFelicidad(int pi, int oj) {
        int prioridad = this.paquetes.get(pi).getPrioridad(), dias = this.transporte.get(oj).getDias();
        return (1-dias) + (2*prioridad);
    }

    public double calcularPrecioPaqueteOferta(int paquete){
        Oferta o = this.getTransporte().get(this.getPaqueteEnOferta()[paquete]);
        double pesoPaq = this.getPaquetes().get(paquete).getPeso();
        return ((o.getPrecio() * pesoPaq) + (0.25 * ((o.getDias() == 1)?0:(o.getDias() > 1 && o.getDias() < 4)?1:2) * pesoPaq));
    }

    public void setSelectedHeuristic(int selectedHeuristic) {
        this.selectedHeuristic = selectedHeuristic;
    }

    //Funciones getters

    public int numeroPaquetes () {
        return this.paqueteEnOferta.length;
    }
    public int numeroTransportes(){ return this.transporte.size(); }
    public int[] getPaqueteEnOferta() {
        return paqueteEnOferta;
    }
    public Paquetes getPaquetes() {
        return paquetes;
    }
    public Transporte getTransporte() {
        return transporte;
    }

    public int getSelectedHeuristic() {
        return selectedHeuristic;
    }

    public static Random getRandom() {
        return random;
    }

    @Override
    public String toString() {
        String s = "AzamonState{";
        String spaq = this.paquetes.toString();
        String spaqof = this.paqueteEnOferta.toString();
        String sof = this.transporte.toString();
        String spof = this.pesoDisponibleOfertas.toString();

        return s + "\n" + spaq + "; \n" + spaqof + "; \n" + sof + "; \n" + spof + "}";
    }

    public double coste() {
        double response = 0.0;
        int numPaq = this.getPaqueteEnOferta().length;
        for(int i = 0; i < numPaq; ++i){
            response += this.calcularPrecioPaqueteOferta(i);
        }
        return response;
    }
}
