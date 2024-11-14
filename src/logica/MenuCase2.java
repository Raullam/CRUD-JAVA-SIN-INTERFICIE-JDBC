package logica;

import dao.ReservaDAO;
import dto.Membre;
import dto.Pista;
import dto.Reserva;

import java.util.List;
import java.util.Scanner;

public class MenuCase2 {
    static String greenText = "\u001B[32m";
    static String redText = "\u001B[31m";
    static String yellowText = "\u001B[33m";
    static String resetText = "\u001B[0m";


    // Método para mostrar el submenu de consulta
    public static void mostrarSubmenuConsultar(ReservaDAO reservaDAO) {
        Scanner scanner = new Scanner(System.in);
        int subopcio;

        System.out.println("\nConsultar:");
        System.out.println("1. Consultar Usuaris");
        System.out.println("2. Consultar Reserves");
        System.out.println("3. Consultar Pistes");
        System.out.println("4. Tornar enrere");
        System.out.print(yellowText + "Selecciona una opció: " + resetText);
        subopcio = scanner.nextInt();

        switch (subopcio) {
            case 1:
                consultarUsuaris(reservaDAO);
                break;

            case 2:
                consultarReserves(reservaDAO);
                break;

            case 3:
                consultarPistes(reservaDAO);
                break;

            case 4:
                System.out.println(yellowText + "Tornant al menú principal..."+ resetText);
                break;

            default:
                System.out.println(redText + "Opció no vàlida." + resetText);
                break;
        }
    }

    private static void consultarUsuaris(ReservaDAO reservaDAO) {
        List<Membre> usuaris = reservaDAO.obtenirMembres();
        if (usuaris.isEmpty()) {
            System.out.println(redText + "No hi ha usuaris registrats." + resetText);
        } else {
            System.out.println("\nUsuaris registrats:");
            for (Membre membre : usuaris) {
                System.out.println("ID: " + membre.getId() + ", Nom: " + membre.getNom() +
                        ", Adreça: " + membre.getAdreca() + ", Telefon: " + membre.getTelefono() +
                        ", Email: " + membre.getEmail());
            }
        }
    }

    // Método para consultar reservas
    private static void consultarReserves(ReservaDAO reservaDAO) {
        List<Reserva> reserves = reservaDAO.obtenirReserves();
        if (reserves.isEmpty()) {
            System.out.println(redText + "No hi ha reserves registrades." + resetText);
        } else {
            System.out.println("\nReserves registrades:");
            for (Reserva reserva : reserves) {
                System.out.println(reserva.toString());
            }
        }
    }

    // Método para consultar pistas
    private static void consultarPistes(ReservaDAO reservaDAO) {
        List<Pista> pistes = reservaDAO.obtenirPistas();
        if (pistes.isEmpty()) {
            System.out.println(redText + "No hi ha pistes registrades." + resetText);
        } else {
            System.out.println("\nPistes registrades:");
            for (Pista pista : pistes) {
                System.out.println("ID: " + pista.getId() + ", Nom: " + pista.getNom() + ", Tipus: " + pista.getTipus());
            }
        }
    }
}
