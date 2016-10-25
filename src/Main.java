import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
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
                main(args);
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

    private static void hillClimbingStrategy(){
        System.out.println("Azamon - Hill Climbing Selected");
        System.out.println("Introduce el número de paquetes:");
        int numPaq = scan.nextInt();
        System.out.println("Introduce una semilla para generar los paquetes:");
        int seedPaquetes = scan.nextInt();
        System.out.println("Introduce una proporción para los transportes:");
        double proportion = scan.nextDouble();
        System.out.println("Introduce una semilla para generar los transportes:");
        int seedOfertas = scan.nextInt();
        AzamonState azamonState = new AzamonState();
        azamonState.generateInitialStateSortPriority(numPaq, seedPaquetes, proportion, seedOfertas);
        try{
            int numHeuristic = selectHeuristic();
            HeuristicFunction heuristicFunction = (numHeuristic == 1)? new AzamonHeuristic(): new AzamonHeuristicHappiness();
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunction(), new AzamonGoalTest(), heuristicFunction);
            HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
            System.out.println("Iniciando Hill Climbing");
            long start = System.currentTimeMillis();
            SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
            long end = System.currentTimeMillis();
            printAgentActions(searchAgent.getActions());
            printAgentInstrumentation(searchAgent.getInstrumentation());
            NumberFormat formatter = new DecimalFormat("#0.00000");
            System.out.println("Duration time: " + formatter.format((end - start)) + "ms");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void simulatedAnnealingStrategy(){
        System.out.println("Azamon - Simulated Annealing selected");
        System.out.println("Introduce el número de paquetes:");
        int numPaq = scan.nextInt();
        System.out.println("Introduce una semilla para generar los paquetes:");
        int seedPaquetes = scan.nextInt();
        System.out.println("Introduce una proporción para los transportes:");
        double proportion = scan.nextDouble();
        System.out.println("Introduce una semilla para generar los transportes:");
        int seedOfertas = scan.nextInt();
        AzamonState azamonState = new AzamonState();
        azamonState.generateInitialStateSortPriority(numPaq, seedPaquetes, proportion, seedOfertas);
        try{
            int numHeuristic = selectHeuristic();
            HeuristicFunction heuristicFunction = (numHeuristic == 1)? new AzamonHeuristic(): new AzamonHeuristicHappiness();
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunctionSimulatedAnnealing(), new AzamonGoalTest(), heuristicFunction);
            System.out.println("--Configurando Simulated Annealing--");
            System.out.println("Introduce los steps:");
            int steps = scan.nextInt();
            System.out.println("Introduce el stiter:");
            int stiter = scan.nextInt();
            System.out.println("Introduce el valor K:");
            int k = scan.nextInt();
            System.out.println("Introduce el valor lambda:");
            double lamb = scan.nextDouble();
            System.out.println("Iniciando Simulated Annealing");
            SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(steps, stiter, k, lamb);
            long start = System.currentTimeMillis();
            SearchAgent searchAgent = new SearchAgent(problem, simulatedAnnealingSearch);
            long end = System.currentTimeMillis();
            printAgentActions(searchAgent.getActions());
            printAgentInstrumentation(searchAgent.getInstrumentation());
            NumberFormat formatter = new DecimalFormat("#0.00000");
            System.out.println("Duration time: " + formatter.format((end - start)) + "ms");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void printAgentActions(List actions){
        for(Object action : actions.toArray()){
            System.out.println((String)action);
        }
    }

    private static void printAgentInstrumentation(Properties properties){
        properties.list(System.out);
    }
}