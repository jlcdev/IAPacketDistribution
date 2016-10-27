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
    private static int nrounds = 10;
    private static int iter = 10;

    public static void initial(String[] args) {
        exp1();
        System.out.println("\n----------------------------------------------------------------------\n");
        exp2();
        System.out.println("\n----------------------------------------------------------------------\n");
        exp3();
        System.out.println("\n----------------------------------------------------------------------\n");
        exp4A();
        System.out.println("\n----------------------------------------------------------------------\n");
        exp4B();
        System.out.println("\n----------------------------------------------------------------------\n");

    }

    private static void exp1(){
        System.out.println("Experimento 1: Determinar conjunto de operadores");
        //Solo un tipo de operadores:
        for (int i = 0; i < nrounds; i++){
            hillClimbingStrategy(1, 100, 1.2);
        }
    }

    private static void exp2() {
        System.out.println("Experimento 2: Determinar estrategia de generación de solucion inicial");
        for(int i = 1; i <= 3; i++){
            System.out.println("Generador " +i);
            for (int j = 0; j < nrounds; j++){
                hillClimbingStrategy(i, 100, 1.2);
            }
        }
    }

    private static void exp3() {
        System.out.println("Experimento 3: Determinar mejores parametros para Simulated Annealing");
        for(int i = 0; i < nrounds; i++){
            //Llamar a SA con varios parametros
        }
    }

    private static void exp4A() {
        System.out.println("Experimento 4.a: Hallar la tendencia al incrementar  la proporción");
        double proporcion = 1.2;
        for(int i = 1; i < iter; i++){
            System.out.println("Proporcion: " +proporcion);
            for (int j = 0; j < nrounds; j++){
                hillClimbingStrategy(i, 100, proporcion);
            }
            proporcion += 0.2;
        }
    }

    private static void exp4B() {
        System.out.println("Experimento 4.b: Hallar la tendencia al incrementar el numero de paquetes");
        int paq = 100;
        for(int i = 1; i < iter; i++){
            System.out.println("Paquetes: " +paq);
            for (int j = 0; j < nrounds; j++){
                hillClimbingStrategy(i, paq, 1.2);
            }
            paq += 50;
        }
    }


    private static void hillClimbingStrategy(int inicial, int nPaq, double prop){
        AzamonState azamonState = new AzamonState();
        if(inicial == 1) azamonState.generateInitialState(nPaq, 1234, prop, 1234);
        else if(inicial == 2) azamonState.generateInitialStateSortPriority(nPaq, 1234, prop, 1234);
        else azamonState.generateInitialStateRandom(nPaq, 1234, prop, 1234);
        double costeInicial = azamonState.coste();
        try{
            HeuristicFunction heuristicFunction = new AzamonHeuristic();
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunctionHC(), new AzamonGoalTest(), heuristicFunction);
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
