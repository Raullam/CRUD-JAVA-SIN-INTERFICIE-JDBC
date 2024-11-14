package logica;

import db.CrearBD;
import dao.ReservaDAO;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    static String greenText = "\u001B[32m";
    static String redText = "\u001B[31m";
    static String yellowText = "\u001B[33m";
    static String resetText = "\u001B[0m";

   public static void inici(){
       CrearBD.crearDB(); // Cream la base de dades
       ReservaDAO reservaDAO = new ReservaDAO();

       mostrarMenuPrincipal(reservaDAO);
   }


private static void mostrarMenuPrincipal(ReservaDAO reservaDAO) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
        int opcio = -1;

        try {
            System.out.println(yellowText + "\nGestió de Reserves del Club de Tennis" + resetText);
            System.out.println("1. Afegir    [ Usuaris, Reserves o Pistes ]");
            System.out.println("2. Consultar [ Usuaris, Reserves o Pistes ]");
            System.out.println("3. Modifica  [ Usuaris, Reserves o Pistes ]");
            System.out.println("4. Eliminar  [ Usuaris, Reserves o Pistes ]");
            System.out.println("5. Transacció afegir membres d'un .txt");
            System.out.println("0. Sortir");
            System.out.print(yellowText + "Selecciona una opció: " + resetText);
            opcio = scanner.nextInt();

        } catch (InputMismatchException e) {
            System.out.println(redText + "Error: Entrada no vàlida. Introdueix un número." + resetText);
            scanner.nextLine();
            continue;
        }

        switch (opcio) {
            case 1:
                MenuCase1.mostrarSubmenuAfegir(reservaDAO);
                break;

            case 2:
                MenuCase2.mostrarSubmenuConsultar(reservaDAO);
                break;

            case 3:
                MenuCase3.mostrarSubmenuModificar(reservaDAO);
                break;

            case 4:
                MenuCase4.mostrarSubmenuEliminar(reservaDAO);
                break;
            case 5:
                // TRANSACCIÓ
                ReservaDAO.insertarMiembrosDesdeArchivo("src/mesMembres.txt");
                break;
            case 0:
                System.out.println(yellowText + "Sortint del sistema."+ resetText);
                return;

            default:
                System.out.println(redText + "Opció no vàlida." + resetText);
        }
    }
}
}
