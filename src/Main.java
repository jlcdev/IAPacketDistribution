import aima.search.framework.Problem;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.*;

/**
 * Created by Javier Lopez on 17/10/16.
 */
public class Main {

    public static void main(String[] args) {
        AzamonState azamonState = new AzamonState();
        //TODO change initial state
        hillClimbingStrategy(azamonState);
        simulatedAnnealingStrategy(azamonState);
    }

    private static void hillClimbingStrategy(AzamonState azamonState){
        System.out.println("Azamon - Hill Climbing Selected");
        try{
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunction(), new AzamonGoalTest(), new AzamonHeuristic());
            HillClimbingSearch hillClimbingSearch = new HillClimbingSearch();
            SearchAgent searchAgent = new SearchAgent(problem, hillClimbingSearch);
            printAgentActions(searchAgent.getActions());
            printAgentInstrumentation(searchAgent.getInstrumentation());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void simulatedAnnealingStrategy(AzamonState azamonState){
        System.out.println("Azamon - Simulated Annealing selected");
        try{
            Problem problem = new Problem(azamonState, new AzamonSuccessorFunction(), new AzamonGoalTest(), new AzamonHeuristic());
            //TODO change simulated annealing paramateres
            SimulatedAnnealingSearch simulatedAnnealingSearch = new SimulatedAnnealingSearch(2000, 100, 5, 0.001D);
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