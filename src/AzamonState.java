import IA.Azamon.Oferta;
import IA.Azamon.Paquete;
import IA.Azamon.Paquetes;
import IA.Azamon.Transporte;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Javier Lopez on 19/10/16.
 */
public class AzamonState {
    private ArrayList<Integer> paqueteEnOferta;
    private ArrayList<Double> pesoDisponibleOfertas;
    private Paquetes paquetes;
    private Transporte transporte;

    public AzamonState(){}

    public AzamonState(int numPaq, int seedPaquetes, double proporcion, int seedOfertas) {
        paquetes = new Paquetes(numPaq, seedPaquetes);
        transporte = new Transporte(this.paquetes, proporcion, seedOfertas);
        paqueteEnOferta = new ArrayList<>();
        pesoDisponibleOfertas = new ArrayList<>();
        Iterator<Oferta> it = transporte.iterator();
        while(it.hasNext()){
            Oferta o = it.next();
            pesoDisponibleOfertas.add(o.getPesomax());
        }
        for(Paquete p: paquetes){
            paqueteEnOferta.add(-1);
        }
    }

    public void generateInitialState1(){
        //Añadir paquetes en ofertas hasta llenar capacidad
        //TODO crear generador inicial 1
    }

    public void generateInitialState2(){
        //Asignar paquetes a ofertas intentando prio(p) == prio(o)
        int sizeP = paquetes.size(), sizeO = transporte.size();
        for(int i = 0; i < sizeP; ++i){
            for(int j = 0; j < sizeO; ++j){
                if(this.ponerPaquete(i, j)) break;
            }
        }
    }

    public void generateInitialState3(){
        //Ordenar ofertas x dia y por precio kg y añadir hasta que no quepan en la oferta
        //Paquetes ordenados por prioridad
        //TODO crear generador inicial 3
    }

    public boolean esMovible(int pi, int oj) {
        return (pesoDisponibleOfertas.get(oj) - paquetes.get(pi).getPeso()) > 0.0;
    }

    public void moverPaquete(int pi, int oj){
        double peso = paquetes.get(pi).getPeso();
        int oi = paqueteEnOferta.get(pi);
        double pesoLibrei = pesoDisponibleOfertas.get(oi);
        double pesoLibrej = pesoDisponibleOfertas.get(oj);

        paqueteEnOferta.set(pi, oj);
        pesoDisponibleOfertas.set(oi, pesoLibrei + peso);
        pesoDisponibleOfertas.set(oj, pesoLibrej - peso);
    }

    public boolean esIntercambiable(int pi, int pj) {
        double pesoi = paquetes.get(pi).getPeso();
        double pesoj = paquetes.get(pj).getPeso();
        int ofertai = paqueteEnOferta.get(pi);
        int ofertaj = paqueteEnOferta.get(pj);
        double pesoLibrei = pesoDisponibleOfertas.get(ofertai);
        double pesoLibrej = pesoDisponibleOfertas.get(ofertaj);

        boolean condi = (pesoLibrei + pesoi - pesoj) > 0.0;
        boolean condj = (pesoLibrej + pesoj - pesoi) > 0.0;
        return condi && condj;
    }

    public void intercambiarPaquete(int pi, int pj){
        double pesoi = paquetes.get(pi).getPeso();
        double pesoj = paquetes.get(pj).getPeso();
        int ofertai = paqueteEnOferta.get(pi);
        int ofertaj = paqueteEnOferta.get(pj);
        double pesoLibrei = pesoDisponibleOfertas.get(ofertai);
        double pesoLibrej = pesoDisponibleOfertas.get(ofertaj);

        //intercambio de ofertas
        paqueteEnOferta.set(pj, ofertai);
        paqueteEnOferta.set(pi, ofertaj);

        //actualizacion pesos
        pesoDisponibleOfertas.set(ofertai, pesoLibrei + pesoi - pesoj);
        pesoDisponibleOfertas.set(ofertaj, pesoLibrej + pesoj - pesoi);

    }

    public Boolean isGoal(){
        //TODO Crear la función isGoal
        return false;
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

    //o.getDias() = {1, 2, 3, 4, 5}
    //p.getPrioridad() = {0, 1, 2}
    //0 = dias(1)
    //1 = dias(2, 3)
    //2 = dias(4, 5)
    private int prioridad(int pi, int oj) {
        int res, prioridad = this.paquetes.get(pi).getPrioridad(), dias = this.transporte.get(oj).getDias();
        switch(prioridad)
        {
            case 0:
                if(dias == 1) res = 0;
                else res = -1;
                break;
            case 1:
                if(dias == 1) res = 1;
                else if(dias <= 3) res = 0;
                else res = -1;
                break;
            case 2:
                if(dias == 1) res = 2;
                else if(dias <= 3) res = 1;
                else res = 0;
                break;
            default:
                res = -1;
                break;
        }
        return res;
    }

    private boolean ponerPaquete(int pi, int oj){
        double deltaPeso = this.pesoDisponibleOfertas.get(oj) - this.paquetes.get(pi).getPeso();
        if(deltaPeso > 0.0 && (this.prioridad(pi, oj) == 0)) {
            this.paqueteEnOferta.set(pi, oj);
            this.pesoDisponibleOfertas.set(oj, deltaPeso);
            return true;
        }
        return false;
    }
}
