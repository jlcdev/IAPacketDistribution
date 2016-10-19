import aima.search.framework.Successor;

import java.util.Scanner;

/**
 * Created by Javier Lopez on 17/10/16.
 */
public class Main {
    public static void main(String[] args) {
        int npaquetes;
        double proporcion;
        Scanner scan = new Scanner(System.in);

        System.out.println("Introduce numero de paquetes");
        npaquetes = scan.nextInt();
        System.out.println("Introduce proporcion");
        proporcion = scan.nextDouble();

        AzamonState state = new AzamonState(3, 1, 10.0, 1);
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
        //Successor e = new Successor("", new Object());
    }
}