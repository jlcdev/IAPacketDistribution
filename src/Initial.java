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
 * Created by albert campano on 27/10/2016.
 */
public class Initial {
    private static Scanner scan = new Scanner(System.in);
    public static void initial(String[] args) {
        System.out.println("Selecciona el algoritmo a aplicar:");
        System.out.println("1.-Hill Climbing");
        System.out.println("2.-Simulated Annealing");
        System.out.println("3.-Salir");
        switch(scan.nextInt()){
            case 1:
                hillClimbingStrategy();
                break;
            case 2:
                simulatedAnnealingStrategy();
                break;
            case 3:
                scan.close();
                return;
            default:
                initial(args);
        }
    }

    private static int selectHeuristic(){
        System.out.println("Selecciona el heurístico a utilizar:");
        System.out.println("1.- Minimizar coste de transporte y almacenamiento.");
        System.out.println("2.- Maximizar la felicidad de los clientes.");
        int response = scan.nextInt();
        if(response < 1 || response > 2) return selectHeuristic();
        return response;
    }

    private static int selectInitial(){
        System.out.println("Selecciona el generador de estado inicial a utilizar:");
        System.out.println("1.- Generador sequencial.");
        System.out.println("2.- Generador aleatorio.");
        System.out.println("3.- Generador ordenado por prioridad.");
        int response = scan.nextInt();
        if(response < 1 || response > 3) return selectInitial();
        return response;
    }

    private static AzamonState createState(){
        System.out.println("Introduce el número de paquetes:");
        int numPaq = scan.nextInt();
        System.out.println("Introduce una semilla para generar los paquetes:");
        int seedPaquetes = scan.nextInt();
        System.out.println("Introduce una proporción para los transportes:");
        double proportion = scan.nextDouble();
        System.out.println("Introduce una semilla para generar los transportes:");
        int seedOfertas = scan.nextInt();
        AzamonState azamonState = new AzamonState();
        int numGenerator = selectInitial();
        switch (numGenerator) {
            case 1:
                azamonState.generateInitialState(numPaq, seedPaquetes, proportion, seedOfertas);
                break;
            case 2:
                azamonState.generatorB(numPaq, seedPaquetes, proportion, seedOfertas);
                //azamonState.generateInitialStateRandom(numPaq, seedPaquetes, proportion, seedOfertas);
                break;
            case 3:
                azamonState.generatorA(numPaq, seedPaquetes, proportion, seedOfertas);
                //azamonState.generateInitialStateSortPriority(numPaq, seedPaquetes, proportion, seedOfertas);
                break;
        }
        return azamonState;
    }

    private static void execute(Problem p, Search search) throws Exception {
        long start = System.currentTimeMillis();
        SearchAgent searchAgent = new SearchAgent(p, search);
        long end = System.currentTimeMillis();
        printAgentActions(searchAgent.getActions());
        printAgentInstrumentation(searchAgent.getInstrumentation());
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Duration time: " + formatter.format((end - start)) + "ms");
    }

    private static void hillClimbingStrategy(){
        System.out.println("Azamon - Hill Climbing Selected");
        AzamonState azamonState = createState();
        try{
            int numHeuristic = selectHeuristic();
            numHeuristic = (numHeuristic == 1)?1:2;
            azamonState.setSelectedHeuristic(numHeuristic);
            HeuristicFunction heuristicFunction = (numHeuristic == 1)? new AzamonHeuristic(): new AzamonHeuristicHappiness();
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunctionHC(), new AzamonGoalTest(), heuristicFunction);
            HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
            System.out.println("Iniciando Hill Climbing");
            execute(problem, hillClimbingSearch);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void simulatedAnnealingStrategy(){
        System.out.println("Azamon - Simulated Annealing selected");
        AzamonState azamonState = createState();
        try{
            int numHeuristic = (selectHeuristic() == 1)?1:2;
            azamonState.setSelectedHeuristic(numHeuristic);
            HeuristicFunction heuristicFunction = (numHeuristic == 1)? new AzamonHeuristic(): new AzamonHeuristicHappiness();
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunctionSA(), new AzamonGoalTest(), heuristicFunction);
            System.out.println("--Configurando Simulated Annealing--");
            System.out.println("Introduce los steps:");
            int maxIterations = scan.nextInt();
            System.out.println("Introduce el stiter:");
            int stiter = scan.nextInt();
            System.out.println("Introduce el valor K:");
            int k = scan.nextInt();
            System.out.println("Introduce el valor lambda:");
            double lamb = scan.nextDouble();
            System.out.println("Iniciando Simulated Annealing");
            System.out.println("Coste inicial: " + heuristicFunction.getHeuristicValue(azamonState));
            SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(maxIterations, stiter, k, lamb);
            long start = System.currentTimeMillis();
            SearchAgent searchAgent = new SearchAgent(problem, simulatedAnnealingSearch);
            long end = System.currentTimeMillis();
            printAgentInstrumentation(searchAgent.getInstrumentation());
            NumberFormat formatter = new DecimalFormat("#0.00000");
            System.out.println("Duration time: " + formatter.format((end - start)) + "ms");
            System.out.println("Coste Final: " + heuristicFunction.getHeuristicValue(searchAgent.getActions().get(0)));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void printAgentActions(List actions){
        if(actions instanceof AzamonState){
            System.out.println(actions.toString());
        }else{
            for(Object action : actions.toArray()){
                System.out.println((String)action);
            }
        }
    }

    private static void printAgentInstrumentation(Properties properties){
        properties.list(System.out);
    }

}
