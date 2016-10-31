import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by albert campano on 27/10/2016.
 */
public class Experiment {
    private static int nrounds = 8;
    private static int iterProp = 8;
    private static int iterPaq = 4;
    private static boolean operadoresExtendido = false;

    private static int numPaquetes = 100;
    private static double numProporcion = 1.2;
    private static int numMaxIt = 1705621;
    private static double numLambda = 0.00001;
    private static int numStiter = 100;
    private static int numK = 6;

    private static int numMaxVarRandom = 4;

    private static boolean extendido = false;
    private static double sumaCosteFelicidadIncial;
    private static double sumaCosteFelicidadFinal;
    private static double sumaCosteAlmInicial;
    private static double sumaCosteAlmFinal;

    private static double sumaCosteInicial;
    private static double sumaCosteFinal;
    private static long sumaTime;
    private static long sumaTimeGeneration;
    private static int sumaPasos;


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
        //El experimento 5 sale de del 4A asi que no es necessario implementarlo
        exp6();
        System.out.println("\n----------------------------------------------------------------------\n");
        exp7();

    }

    private static void exp7() {
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Experimento 7.1: Determinar conjunto de operadores");
        experimentoSA(1, numPaquetes, numProporcion, 1, numMaxIt, numLambda, numStiter, numK);
        System.out.println("\n----------------------------------------------------------------------\n");
        System.out.println("Experimento 7.2: Determinar estrategia de generación de solucion inicial");
        for(int i = 1; i <= 3; i++){
            System.out.print("Generador " +i +"  ");
            experimentoSA(i, numPaquetes, numProporcion, 1, numMaxIt, numLambda, numStiter, numK);
        }
        System.out.println("\n----------------------------------------------------------------------\n");
        //Exp3
        System.out.println("Experimento 7.4.a: Hallar la tendencia al incrementar  la proporción");
        double proporcion = 1.2;
        for(int i = 1; i < iterProp; i++){
            System.out.print("Proporcion: " +df.format(proporcion) +"  ");
            experimentoSA(1, numPaquetes, proporcion, 1, numMaxIt, numLambda, numStiter, numK);
            proporcion += 0.2;
        }
        System.out.println("\n----------------------------------------------------------------------\n");
        System.out.println("Experimento 7.4.b: Hallar la tendencia al incrementar el numero de paquetes");
        int paq = 100;
        for(int i = 1; i < iterPaq; i++){
            System.out.print("Paquetes: " +paq  +"  ");
            experimentoSA(1, paq, numProporcion, 1, numMaxIt, numLambda, numStiter, numK);
            paq += 50;
        }
        System.out.println("\n----------------------------------------------------------------------\n");
        System.out.println("Experimento 7.6: Funciones heuristicas");
        extendido = true;
        experimentoSA(1, numPaquetes, numProporcion, 2, numMaxIt, numLambda, numStiter, numK);
        extendido = false;
        System.out.println("\n----------------------------------------------------------------------\n");

    }

    private static void exp6() {
        System.out.println("Experimento 6: Funciones heuristicas");
        extendido = true;
        experimentoHC(1, numPaquetes, numProporcion, 2);
        extendido = false;
    }

    private static void exp1(){
        System.out.println("Experimento 1: Determinar conjunto de operadores");
        System.out.println("Operadores: mover + intercambiar Paquetes + intercambiar Ofertas");
        operadoresExtendido = true;
        experimentoHC(1, numPaquetes, numProporcion, 1);
        operadoresExtendido = false;
        System.out.println("Operadores: mover + intercambiar Paquetes");
        experimentoHC(1, numPaquetes, numProporcion, 1);
    }

    private static void exp2() {
        System.out.println("Experimento 2: Determinar estrategia de generación de solucion inicial");
        for(int i = 1; i <= 3; i++){
            System.out.print("Generador " +i +"  ");
            experimentoHC(i, numPaquetes, numProporcion, 1);
        }
    }

    private static void exp3() {
        System.out.println("Experimento 3: Determinar mejores parametros para Simulated Annealing");
        randomSAParamsSearch();
    }

    private static void exp4A() {
        System.out.println("Experimento 4.a: Hallar la tendencia al incrementar  la proporción");
        double proporcion = 1.2;
        DecimalFormat df = new DecimalFormat("#.##");
        for(int i = 1; i < iterProp; i++){
            System.out.print("Proporcion: " +df.format(proporcion) +"  ");
            experimentoHC(1, numPaquetes, proporcion, 1);
            proporcion += 0.2;
        }
    }

    private static void exp4B() {
        System.out.println("Experimento 4.b: Hallar la tendencia al incrementar el numero de paquetes");
        int paq = 100;
        for(int i = 1; i < iterPaq; i++){
            System.out.print("Paquetes: " +paq  +"  ");
            experimentoHC(1, paq, numProporcion, 1);
            paq += 50;
        }
    }

    private static void init() {
        if(extendido) sumaCosteFelicidadIncial = sumaCosteFelicidadFinal = sumaCosteAlmInicial = sumaCosteAlmFinal = 0.0;
        sumaCosteInicial = sumaCosteFinal = 0.0;
        sumaTimeGeneration = sumaTime =  sumaPasos = 0;
    }

    private static void experimentoHC(int inicial, int nPaq, double prop, int heuristic) {
        init();
        for(int i = 0; i < nrounds; i++) {
            hillClimbingStrategy(inicial, nPaq, prop, heuristic);
        }
        double mediaCI = sumaCosteInicial/nrounds;
        double mediaCF = sumaCosteFinal/nrounds;
        long mediaT = sumaTime/nrounds;
        int mediaP = sumaPasos/nrounds;
        long mediaTG = sumaTimeGeneration/nrounds;
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("T.Generation: " +(mediaTG) +" ms    T.Alg.: " +(mediaT) +" ms    T.Total: " +(mediaT+mediaTG +" ms"));
        System.out.println("C.Ini.: " +(df.format(mediaCI)) +" C.Fin.: " +(df.format(mediaCF)) +" Pasos: " +(mediaP));
        if(extendido) {
            double mediaCFI = sumaCosteFelicidadIncial/nrounds;
            double mediaCFF = sumaCosteFelicidadFinal/nrounds;
            double mediaCTI = sumaCosteAlmInicial /nrounds;
            double mediaCTF = sumaCosteAlmFinal /nrounds;
            System.out.print("C.Ini.Fel: " +(df.format(mediaCFI)) +" C.Fin.Fel: " +(df.format(mediaCFF)) );
            System.out.println(" C.Ini.Alm: " +(df.format(mediaCTI)) +" C.Fin.Alm: " +(df.format(mediaCTF)) );
        }
    }

    private static void experimentoSA(int inicial, int nPaq, double prop, int heuristic, int maxIt, double lamb, int stiter, int k) {
        init();
        for(int i = 0; i < nrounds; i++) {
            simulatedAnnealingStrategy(inicial, nPaq, prop, heuristic, maxIt, lamb, stiter, k);
        }
        double mediaCI = sumaCosteInicial/nrounds;
        double mediaCF = sumaCosteFinal/nrounds;
        long mediaT = sumaTime/nrounds;
        int mediaP = sumaPasos/nrounds;
        long mediaTG = sumaTimeGeneration/nrounds;
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("T.Generation: " +(mediaTG) +" ms    T.Alg.: " +(mediaT) +" ms    T.Total: " +(mediaT+mediaTG +" ms"));
        System.out.println("C.Ini.: " +(df.format(mediaCI)) +" C.Fin.: " +(df.format(mediaCF)) +" Pasos: " +(mediaP));
        if(extendido) {
            double mediaCFI = sumaCosteFelicidadIncial/nrounds;
            double mediaCFF = sumaCosteFelicidadFinal/nrounds;
            double mediaCTI = sumaCosteAlmInicial /nrounds;
            double mediaCTF = sumaCosteAlmFinal /nrounds;
            System.out.print("C.Ini.Fel: " +(df.format(mediaCFI)) +" C.Fin.Fel: " +(df.format(mediaCFF)) );
            System.out.println(" C.Ini.Alm: " +(df.format(mediaCTI)) +" C.Fin.Alm: " +(df.format(mediaCTF)) );
        }
    }

    private static AzamonState selectgenerator(int i, int nPaq, double prop) {
        AzamonState aS = new AzamonState();
        long start = System.currentTimeMillis();
        if(i == 1) aS.generatorA(nPaq, 1234, prop, 1234);
        else if (i == 2) aS.generatorB(nPaq, 1234, prop, 1234);
        else if (i == 3) aS.generatorC(nPaq, 1234, prop, 1234);
        long end = System.currentTimeMillis();
        sumaTimeGeneration += (end-start);
        return aS;
    }

    private static void hillClimbingStrategy(int inicial, int nPaq, double prop, int heuristic){
        AzamonState azamonState = selectgenerator(inicial, nPaq, prop);
        azamonState.setSelectedHeuristic(heuristic);
        if(operadoresExtendido) azamonState.setOperadoresExtendido(true);
        else azamonState.setOperadoresExtendido(false);
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
        if(operadoresExtendido) azamonState.setOperadoresExtendido(true);
        else azamonState.setOperadoresExtendido(false);
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
        sumaCosteInicial += ((AzamonState)problem.getInitialState()).heuristicValue();
        if(extendido) {
            sumaCosteAlmInicial += ((AzamonState)problem.getInitialState()).coste();
            sumaCosteFelicidadIncial += ((AzamonState)problem.getInitialState()).felicidad();
        }
        long start = System.currentTimeMillis();
        SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
        long end = System.currentTimeMillis();
        sumaTime += (end - start);
        sumaPasos += hillClimbingSearch.getNodesExpanded();
        sumaCosteFinal += ((AzamonState)hillClimbingSearch.getGoalState()).heuristicValue();
        if(extendido) {
            sumaCosteAlmFinal += ((AzamonState)hillClimbingSearch.getGoalState()).coste();
            sumaCosteFelicidadFinal += ((AzamonState)hillClimbingSearch.getGoalState()).felicidad();
        }
    }

    private static void calculateSA(Problem problem, SimulatedAnnealingSearch s) throws Exception {
        sumaCosteInicial += ((AzamonState)problem.getInitialState()).heuristicValue();
        if(extendido) {
            sumaCosteAlmInicial += ((AzamonState)problem.getInitialState()).coste();
            sumaCosteFelicidadIncial += ((AzamonState)problem.getInitialState()).felicidad();
        }
        long start = System.currentTimeMillis();
        SearchAgent searchAgent = new SearchAgent(problem, s);
        long end = System.currentTimeMillis();
        sumaTime += (end - start);
        sumaPasos += s.getNodesExpanded();
        sumaCosteFinal += ((AzamonState)s.getGoalState()).coste();
        sumaCosteFinal += ((AzamonState)s.getGoalState()).heuristicValue();
        if(extendido) {
            sumaCosteAlmFinal += ((AzamonState)s.getGoalState()).coste();
            sumaCosteFelicidadFinal += ((AzamonState)s.getGoalState()).felicidad();
        }

    }

    private static void randomSAParamsSearch(){
        int minP1 = 0;
        int minP2 = 0;
        int minK = 0;
        double minLamda = 0.0;
        double minCoste = 1000;
        double[] lambdaList = new double[]{0.0001, 0.00001, 0.000001, 0.0000001};
        Random random = new Random();
        AzamonState azamonState = new AzamonState();
        azamonState.generatorA(100, 1234, 1.2, 1234);
        AzamonHeuristic h = new AzamonHeuristic();
        Problem p = new Problem(azamonState, new AzamonSuccessorFunctionSA(), new AzamonGoalTest(), h);
        try{
            for(int i = 0; i < numMaxVarRandom; ++i){
                int p1 = random.nextInt(2000000);
                int p2 = random.nextInt(500);
                int p3 = random.nextInt(50);
                double p4 = lambdaList[random.nextInt(lambdaList.length)];
                SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(p1, p2, p3, p4);
                SearchAgent searchAgent = new SearchAgent(p, simulatedAnnealingSearch);
                double coste = h.getHeuristicValue(searchAgent.getActions().get(0));
                if(coste < minCoste){
                    minP1 = p1;
                    minP2 = p2;
                    minK = p3;
                    minLamda = p4;
                    minCoste = coste;
                }
            }
            System.out.println("Prarametros minimos encontrados:");
            System.out.println("Steps: " + minP1 + ", stiter: " + minP2 + ", k: " + minK + ", lamda: " + minLamda);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
