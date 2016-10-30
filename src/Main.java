import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by Javier Lopez on 17/10/16.
 */
public class Main {
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Selecciona:");
        System.out.println("1.-Prueba manual");
        System.out.println("2.-Experimento preparado");
        System.out.println("3.-Salir");
        switch(scan.nextInt()){
            case 1:
                Initial.initial(args);
                break;
            case 2:
                Experiment.initial(args);
                break;
            case 3:
                scan.close();
                return;
            default:
                main(args);
        }
    }
}