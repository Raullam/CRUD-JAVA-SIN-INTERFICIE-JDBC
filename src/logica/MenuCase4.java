package logica;

import dao.ReservaDAO;

import java.util.Scanner;

public class MenuCase4 {
    static String greenText = "\u001B[32m";
    static String redText = "\u001B[31m";
    static String yellowText = "\u001B[33m";
    static String resetText = "\u001B[0m";
    static Scanner sc = new Scanner(System.in);


    public static void mostrarSubmenuEliminar(ReservaDAO reservaDAO) {
        Scanner scanner = new Scanner(System.in);
        int subopcio;

        // Mostrar el submenu con 4 opciones
        System.out.println("\nEliminar:");
        System.out.println("1. Eliminar Usuaris");
        System.out.println("2. Eliminar Reserves");
        System.out.println("3. Eliminar Pistes");
        System.out.println("4. Tornar enrere");
        System.out.print(yellowText + "Selecciona una opció: " + resetText);
        subopcio = scanner.nextInt();

        switch (subopcio) {
            case 1:
                eliminarUsuaris(reservaDAO);
                break;

            case 2:
                eliminarReserva(reservaDAO);
                break;

            case 3:
                eliminarPistes(reservaDAO);
                break;

            case 4:
                System.out.println(yellowText + "Tornant al menú principal..."+ resetText);
                break;

            default:
                System.out.println(redText + "Opció no vàlida." + resetText);
                break;
        }
    }

    private static void eliminarUsuaris(ReservaDAO reservaDAO) {
        // Solicitar ID del usuario a eliminar
        System.out.print("Introduïu l'ID de l'usuari a eliminar: ");
        int idUsuariEliminar = sc.nextInt();

        // Primero, eliminar las reservas asociadas al usuario
        reservaDAO.eliminarReservaPerIdUsuari(idUsuariEliminar);

        // Luego, eliminar el usuario (membre)
        reservaDAO.eliminarMembre(idUsuariEliminar);

        // Mensaje de éxito
        System.out.println(greenText + "Usuari eliminat correctament!" + resetText);
    }

    private static void eliminarReserva(ReservaDAO reservaDAO) {
        // Solicitar ID de la reserva a eliminar
        System.out.print("Introduïu l'ID de la reserva eliminar: ");
        int idReserva = sc.nextInt();

        // Primero, eliminar las reservas asociadas al usuario
        reservaDAO.eliminarReserva(idReserva);

        // Mensaje de éxito
        System.out.println(greenText + "Reserva amb ID: " + idReserva + " eliminat" + resetText);
    }

    private static void eliminarPistes(ReservaDAO reservaDAO) {
        // Solicitar ID del usuario a eliminar
        System.out.print("Introduïu l'ID de la pista a eliminar: ");
        int idPistaEliminar = sc.nextInt();

        // Primero, eliminar las reservas asociadas al usuario
        reservaDAO.eliminarReservasPerIDdePista(idPistaEliminar);

        // Luego, eliminar el usuario (membre)
        reservaDAO.eliminarPista(idPistaEliminar);

        // Mensaje de éxito
        System.out.println(greenText + "Usuari eliminat correctament!" + resetText);
    }



}
