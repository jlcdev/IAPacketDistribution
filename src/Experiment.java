import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by albert campano on 27/10/2016.
 */
public class Experiment {

    public static void initial(String[] args) {
        exp1();

    }

    private static void exp1(){
        int nrounds = 10;
        System.out.println("Experimento 1: Determinar conjunto de operadores");
        //Solo un tipo de operadores:
        for (int i = 0; i < 10; i++){
            hillClimbingStrategy(1, 100, 1.2);
        }

    }
    private static void hillClimbingStrategy(int inicial, int nPaq, double prop){
        AzamonState azamonState = new AzamonState();
        azamonState.generateInitialState(nPaq, 1234, prop, 1234);
        //azamonState.generateInitialStateSortPriority(nPaq, 1234, prop, 1234);
        //azamonState.generateInitialStateRandom(nPaq, 1234, prop, 1234);
        double costeInicial = azamonState.coste();
        try{
            HeuristicFunction heuristicFunction = new AzamonHeuristic();
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunction(), new AzamonGoalTest(), heuristicFunction);
            HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
            long start = System.currentTimeMillis();
            SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
            long end = System.currentTimeMillis();
            printInformation(searchAgent, (end-start), costeInicial, ((AzamonState)hillClimbingSearch.getGoalState()).coste());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    private static void printInformation(SearchAgent s, long time, double coste1, double coste2){
        //coste inicial i Coste final
        System.out.println("Coste inicial: " +coste1 + "  Coste final: " +coste2);
        //Nodos expandidos
        s.getInstrumentation().list(System.out);
        //Tiempo
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Duration time: " + formatter.format(time) + "ms");
    }
}
