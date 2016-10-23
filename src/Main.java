import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

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

    private static void hillClimbingStrategy(){
        AzamonState azamonState = new AzamonState();
        System.out.println("Azamon - Hill Climbing Selected");
        System.out.println("Introduce el número de paquetes:");
        int numPaq = scan.nextInt();
        System.out.println("Introduce una semilla para generar los paquetes:");
        int seedPaquetes = scan.nextInt();
        System.out.println("Introduce una proporción para los transportes:");
        double proportion = scan.nextDouble();
        System.out.println("Introduce una semilla para generar los transportes:");
        int seedOfertas = scan.nextInt();
        azamonState.generateInitialStateSortPriority(numPaq, seedPaquetes, proportion, seedOfertas);
        try{
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunction(), new AzamonGoalTest(), new AzamonHeuristic());
            HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
            System.out.println("Iniciando Hill Climbing");
            SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
            printAgentActions(searchAgent.getActions());
            printAgentInstrumentation(searchAgent.getInstrumentation());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void simulatedAnnealingStrategy(){
        AzamonState azamonState = new AzamonState();
        System.out.println("Azamon - Simulated Annealing selected");
        System.out.println("Introduce el número de paquetes:");
        int numPaq = scan.nextInt();
        System.out.println("Introduce una semilla para generar los paquetes:");
        int seedPaquetes = scan.nextInt();
        System.out.println("Introduce una proporción para los transportes:");
        double proportion = scan.nextDouble();
        System.out.println("Introduce una semilla para generar los transportes:");
        int seedOfertas = scan.nextInt();
        azamonState.generateInitialStateSortPriority(numPaq, seedPaquetes, proportion, seedOfertas);
        try{
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunctionSimulatedAnnealing(), new AzamonGoalTest(), new AzamonHeuristic());
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
            SearchAgent searchAgent = new SearchAgent(problem, simulatedAnnealingSearch);
            printAgentActions(searchAgent.getActions());
            printAgentInstrumentation(searchAgent.getInstrumentation());
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
/*
    public static void main2(String[] args) {
        int npaquetes;
        double proporcion;
        Scanner scan = new Scanner(System.in);

        System.out.println("Introduce numero de paquetes");
        npaquetes = scan.nextInt();
        System.out.println("Introduce proporcion");
        proporcion = scan.nextDouble();

        AzamonState state = new AzamonState(npaquetes, 1, proporcion, 1);
        System.out.println("\nEstado inicial");
        System.out.println(state.toString());

        int control = 0;
        int paquete = 0;
        int ofertaOPaquete = 0;
        while(control != -1) {
            System.out.println("\n Mover = 0; Intercambio = 1; Salir = -1");
            control = scan.nextInt();
            switch (control) {
                case 0:
                    System.out.println("Paquete");
                    paquete = scan.nextInt();
                    System.out.println("Oferta");
                    ofertaOPaquete = scan.nextInt();

                    if (state.esMovible(paquete, ofertaOPaquete)) state.moverPaquete(paquete, ofertaOPaquete);
                    else System.out.println("No es possible mover");
                    System.out.println(state.toString());
                    break;
                case 1:
                    System.out.println("Paquete i");
                    paquete = scan.nextInt();
                    System.out.println("Paquete j");
                    ofertaOPaquete = scan.nextInt();

                    if (state.esIntercambiable(paquete, ofertaOPaquete))
                        state.intercambiarPaquete(paquete, ofertaOPaquete);
                    else System.out.println("No es possible intercambiar");
                    System.out.println(state.toString());
                    break;
                case -1:
                    //Salir
                    break;
            }
        }
    }
    */
}