import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.text.DecimalFormat;

/**
 * Created by albert campano on 27/10/2016.
 */
public class Experiment {
    private static int nrounds = 10;
    private static int iterProp = 10;
    private static int iterPaq = 5;

    private static double sumaCosteInicial;
    private static double sumaCosteFinal;
    private static long sumaTime;
    private static int sumaPasos;


    public static void initial(String[] args) {
        exp1();
        System.out.println("\n----------------------------------------------------------------------\n");
        exp2();
        System.out.println("\n----------------------------------------------------------------------\n");
        //exp3();
        System.out.println("\n----------------------------------------------------------------------\n");
        exp4A();
        System.out.println("\n----------------------------------------------------------------------\n");
        exp4B();
        System.out.println("\n----------------------------------------------------------------------\n");
        //El experimento 5 sale de del 4A asi que no es necessario implementarlo
        exp6();
        System.out.println("\n----------------------------------------------------------------------\n");
        exp7();

    }

    private static void exp7() {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Experimento 7.1: Determinar conjunto de operadores");
        experimentoSA(1, 100, 1.2, 1, 10000, 0.001, 1000, 10);
        System.out.println("\n----------------------------------------------------------------------\n");
        System.out.println("Experimento 7.2: Determinar estrategia de generación de solucion inicial");
        for(int i = 1; i <= 3; i++){
            System.out.print("Generador " +i +"  ");
            experimentoSA(i, 100, 1.2, 1, 10000, 0.001, 1000, 10);
        }
        System.out.println("\n----------------------------------------------------------------------\n");
        //Exp3
        System.out.println("Experimento 7.4.a: Hallar la tendencia al incrementar  la proporción");
        double proporcion = 1.2;
        for(int i = 1; i < iterProp; i++){
            System.out.print("Proporcion: " +df.format(proporcion) +"  ");
            experimentoSA(1, 100, proporcion, 1, 10000, 0.001, 1000, 10);
            proporcion += 0.2;
        }
        System.out.println("\n----------------------------------------------------------------------\n");
        System.out.println("Experimento 7.4.b: Hallar la tendencia al incrementar el numero de paquetes");
        int paq = 100;
        for(int i = 1; i < iterPaq; i++){
            System.out.print("Paquetes: " +paq  +"  ");
            experimentoSA(1, paq, 1.2, 1, 10000, 0.001, 1000, 10);
            paq += 50;
        }
        System.out.println("\n----------------------------------------------------------------------\n");
        System.out.println("Experimento 7.6: Funciones heuristicas");
        experimentoSA(1, 100, 1.2, 2, 10000, 0.001, 1000, 10);
        System.out.println("\n----------------------------------------------------------------------\n");

    }

    private static void exp6() {
        System.out.println("Experimento 6: Funciones heuristicas");
        experimentoHC(1, 100, 1.2, 2);
    }

    private static void exp1(){
        System.out.println("Experimento 1: Determinar conjunto de operadores");
        //Solo un tipo de operadores:
        experimentoHC(1, 100, 1.2, 1);
    }

    private static void exp2() {
        System.out.println("Experimento 2: Determinar estrategia de generación de solucion inicial");
        for(int i = 1; i <= 3; i++){
            System.out.print("Generador " +i +"  ");
            experimentoHC(i, 100, 1.2, 1);
        }
    }

    private static void exp3() {
        System.out.println("Experimento 3: Determinar mejores parametros para Simulated Annealing");
        int startIterMax = 1;
        int endIterMax = 100000000;
        int startItStep = 1;
        int endItStep = 1000;
        int startK = 1;
        int endK = 50;
        double startLamb = 0.0000000001;
        double endLamb = 0.1;

        //Ya nos podemos pegar un tiro con esto
        for(int i = startIterMax; i < endIterMax; i++) {
            for (int j = startItStep; j < endItStep; j++) {
                for (int k = startK; k < endK; k++) {
                    for (double l = startLamb; l < endLamb; l += startLamb){
                        experimentoSA(3, 100, 1.2, 1, i, l, j, k);
                    }
                }
            }
        }
    }

    private static void exp4A() {
        System.out.println("Experimento 4.a: Hallar la tendencia al incrementar  la proporción");
        double proporcion = 1.2;
        DecimalFormat df = new DecimalFormat("#.##");
        for(int i = 1; i < iterProp; i++){
            System.out.print("Proporcion: " +df.format(proporcion) +"  ");
            experimentoHC(1, 100, proporcion, 1);
            proporcion += 0.2;
        }
    }

    private static void exp4B() {
        System.out.println("Experimento 4.b: Hallar la tendencia al incrementar el numero de paquetes");
        int paq = 100;
        for(int i = 1; i < iterPaq; i++){
            System.out.print("Paquetes: " +paq  +"  ");
            experimentoHC(1, paq, 1.2, 1);
            paq += 50;
        }
    }

    private static void experimentoHC(int inicial, int nPaq, double prop, int heuristic) {
        sumaCosteInicial = 00.;
        sumaCosteFinal = 0.0;
        sumaTime = 0;
        sumaPasos = 0;
        for(int i = 0; i < nrounds; i++) {
            hillClimbingStrategy(inicial, nPaq, prop, heuristic);
        }
        double mediaCI = sumaCosteInicial/nrounds;
        double mediaCF = sumaCosteFinal/nrounds;
        long mediaT = sumaTime/nrounds;
        int mediaP = sumaPasos/nrounds;
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("C.Ini.: " +(df.format(mediaCI)) +" C.Fin.: " +(df.format(mediaCF)) +" Time: " +(mediaT) +" Pasos: " +(mediaP));
    }

    private static void experimentoSA(int inicial, int nPaq, double prop, int heuristic, int maxIt, double lamb, int stiter, int k) {
        sumaCosteInicial = 00.;
        sumaCosteFinal = 0.0;
        sumaTime = 0;
        sumaPasos = 0;
        for(int i = 0; i < nrounds; i++) {
            simulatedAnnealingStrategy(inicial, nPaq, prop, heuristic, maxIt, lamb, stiter, k);
        }
        double mediaCI = sumaCosteInicial/nrounds;
        double mediaCF = sumaCosteFinal/nrounds;
        long mediaT = sumaTime/nrounds;
        int mediaP = sumaPasos/nrounds;
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("C.Ini.: " +(df.format(mediaCI)) +" C.Fin.: " +(df.format(mediaCF)) +" Time: " +(mediaT) +" Pasos: " +(mediaP));
    }

    private static AzamonState selectgenerator(int i, int nPaq, double prop) {
        AzamonState aS = new AzamonState();
        if(i == 1) aS.generatorA(nPaq, 1234, prop, 1234);
        else if (i == 2) aS.generatorB(nPaq, 1234, prop, 1234);
        else if (i == 3) aS.generatorC(nPaq, 1234, prop, 1234);
        return aS;
    }

    private static void hillClimbingStrategy(int inicial, int nPaq, double prop, int heuristic){
        AzamonState azamonState = selectgenerator(inicial, nPaq, prop);
        azamonState.setSelectedHeuristic(heuristic);
        try{
            HeuristicFunction h = (heuristic == 1)? new AzamonHeuristic(): new AzamonHeuristicHappiness();
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunctionHC(), new AzamonGoalTest(), h);
            HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
            calculateHC(problem, hillClimbingSearch);
            }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void simulatedAnnealingStrategy(int inicial, int nPaq, double prop, int heuristic, int maxIt, double lamb, int stiter, int k){
        AzamonState azamonState = selectgenerator(inicial, nPaq, prop);
        azamonState.setSelectedHeuristic(heuristic);
        try{
            HeuristicFunction h = (heuristic == 1)? new AzamonHeuristic(): new AzamonHeuristicHappiness();
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunctionSA(), new AzamonGoalTest(), h);
            SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(maxIt, stiter, k, lamb);
            calculateSA(problem, simulatedAnnealingSearch);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void calculateHC(Problem problem, HillClimbingSearch hillClimbingSearch) throws Exception {
        sumaCosteInicial += ((AzamonState)problem.getInitialState()).coste();
        long start = System.currentTimeMillis();
        SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
        long end = System.currentTimeMillis();
        sumaTime += (end - start);
        sumaPasos += hillClimbingSearch.getNodesExpanded();
        sumaCosteFinal += ((AzamonState)hillClimbingSearch.getGoalState()).coste();
    }

    private static void calculateSA(Problem problem, SimulatedAnnealingSearch s) throws Exception {
        sumaCosteInicial += ((AzamonState)problem.getInitialState()).coste();
        long start = System.currentTimeMillis();
        SearchAgent searchAgent = new SearchAgent(problem, s);
        long end = System.currentTimeMillis();
        sumaTime += (end - start);
        sumaPasos += s.getNodesExpanded();
        sumaCosteFinal += ((AzamonState)s.getGoalState()).coste();
    }
}
