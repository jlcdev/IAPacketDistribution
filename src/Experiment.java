import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
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

    private static double sumaCosteInicial;
    private static double sumaCosteFinal;
    private static long sumaTime;
    private static int sumaPasos;


    public static void initial(String[] args) {
        //exp1();
        //System.out.println("\n----------------------------------------------------------------------\n");
        exp2();
        System.out.println("\n----------------------------------------------------------------------\n");
        //exp3();
        //System.out.println("\n----------------------------------------------------------------------\n");
        exp4A();
        System.out.println("\n----------------------------------------------------------------------\n");
        exp4B();
        System.out.println("\n----------------------------------------------------------------------\n");

    }

    private static void exp1(){
        System.out.println("Experimento 1: Determinar conjunto de operadores");
        //Solo un tipo de operadores:
        experimento(1, 100, 1.2);
    }

    private static void exp2() {
        System.out.println("Experimento 2: Determinar estrategia de generación de solucion inicial");
        for(int i = 1; i <= 3; i++){
            System.out.print("Generador " +i +"  ");
            experimento(i, 100, 1.2);
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
            System.out.print("Proporcion: " +proporcion +"   ");
            experimento(1, 100, proporcion);
            proporcion += 0.2;
        }
    }

    private static void exp4B() {
        System.out.println("Experimento 4.b: Hallar la tendencia al incrementar el numero de paquetes");
        int paq = 100;
        for(int i = 1; i < iter; i++){
            System.out.print("Paquetes: " +paq  +"   ");
            experimento(1, paq, 1.2);
            paq += 50;
        }
    }

    private static void experimento(int inicial, int nPaq, double prop) {
        sumaCosteInicial = 00.;
        sumaCosteFinal = 0.0;
        sumaTime = 0;
        sumaPasos = 0;
        for(int i = 0; i < nrounds; i++) {
            hillClimbingStrategy(inicial, nPaq, prop);
        }
        System.out.println("C.Ini.: " +(sumaCosteInicial/nrounds) +" C.Fin.: " +(sumaCosteFinal/nrounds) +"Time: " +(sumaTime/nrounds) +"Pasos: " +(sumaPasos/nrounds));
    }

    private static AzamonState selectgenerator(int i, int nPaq, double prop) {
        AzamonState aS = new AzamonState();
        if(i == 1) aS.generateInitialState(nPaq, 1234, prop, 1234);
        else if(i == 2) aS.generateInitialStateRandom(nPaq, 1234, prop, 1234);
        else aS.generateInitialStateSortPriority(nPaq, 1234, prop, 1234);
        return aS;
    }

    private static void hillClimbingStrategy(int inicial, int nPaq, double prop){
        AzamonState azamonState = selectgenerator(inicial, nPaq, prop);
        try{
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunctionHC(), new AzamonGoalTest(), new AzamonHeuristic());
            HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
            calculate(problem, hillClimbingSearch);
            }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void calculate(Problem problem, HillClimbingSearch hillClimbingSearch) throws Exception {
        sumaCosteInicial += ((AzamonState)problem.getInitialState()).coste();
        long start = System.currentTimeMillis();
        SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
        long end = System.currentTimeMillis();
        sumaTime += (end - start);
        sumaPasos += hillClimbingSearch.getNodesExpanded();
        sumaCosteFinal += ((AzamonState)hillClimbingSearch.getGoalState()).coste();
    }
}
