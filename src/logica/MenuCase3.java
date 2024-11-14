package logica;

import dao.ReservaDAO;
import dto.Membre;
import dto.Pista;
import dto.Reserva;

import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;

public class MenuCase3 {
    static String greenText = "\u001B[32m";
    static String redText = "\u001B[31m";
    static String yellowText = "\u001B[33m";
    static String resetText = "\u001B[0m";

    public static void mostrarSubmenuModificar(ReservaDAO reservaDAO) {
        Scanner scanner = new Scanner(System.in);
        int subopcio;

        System.out.println("\nModificar:");
        System.out.println("1. Modificar Usuaris");
        System.out.println("2. Modificar Reserves");
        System.out.println("3. Modificar Pistes");
        System.out.println("4. Tornar enrere");
        System.out.print(yellowText + "Selecciona una opció: " + resetText);
        subopcio = scanner.nextInt();

        switch (subopcio) {
            case 1:
                modificarUsuaris(reservaDAO);
                break;

            case 2:
                modificarReserves(reservaDAO);
                break;

            case 3:
                modificarPistes(reservaDAO);
                break;

            case 4:
                System.out.println(yellowText + "Tornant al menú principal..."+ resetText);
                break;

            default:
                System.out.println(redText + "Opció no vàlida." + resetText);
                break;
        }
    }




    public static void modificarReserves(ReservaDAO reservaDAO) {
        // Actualitzar una reserva
        int idReserva = obtenirIdReserva();
        if (idReserva == -1) return;

        // Consultar la reserva amb l'ID per obtenir membreId i pistaId
        Reserva reservaExistente = ReservaDAO.obtenirReserva(idReserva);
        if (reservaExistente == null) {
            System.out.println(redText + "No s'ha trobat cap reserva amb aquest ID." + resetText);
            return;
        }
        System.out.println(greenText + "Reserva actual: " + resetText);
        System.out.println(greenText + reservaExistente.toString() + resetText);


        // Demanar la nova informació per actualitzar la reserva
        Reserva reservaActualitzada = demanarNovaInformacio(reservaExistente);

        // Actualitzar la reserva a la base de dades
        reservaDAO.actualitzarReserva(reservaActualitzada);
        System.out.println(greenText + "Reserva actualitzada correctament!" + resetText);
    }
    private static int obtenirIdReserva() {
        Scanner scanner = new Scanner(System.in);
        int idReserva = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.print("Introduïu l'ID de la reserva a actualitzar: ");
            if (scanner.hasNextInt()) {
                idReserva = scanner.nextInt();
                entradaValida = true;  // La entrada es válida, salimos del bucle
            } else {
                System.out.println(redText + "Error: Si us plau, introduïu un número enter vàlid." + resetText);
                scanner.next(); // Limpiar la entrada incorrecta
            }
        }
        return idReserva;
    }

    private static Reserva demanarNovaInformacio(Reserva reservaExistente) {
        Scanner scanner = new Scanner(System.in);

        Date nuevaFecha = null;

        while (true) {
            System.out.print("Nova data (YYYY-MM-DD) o prémer Enter per mantenir la data actual (" + reservaExistente.getData() + "): ");
            String input = scanner.nextLine();

            // Si el usuario presiona Enter sin escribir nada, mantener la fecha actual
            if (input.isEmpty()) {
                nuevaFecha = reservaExistente.getData();
                break;
            }

            // Intentar convertir la entrada a una fecha
            try {
                nuevaFecha = Date.valueOf(input);
                break;  // Salir del bucle si la fecha es válida
            } catch (IllegalArgumentException e) {
                System.out.println("\u001B[31m" + "Error: El formato de fecha debe ser YYYY-MM-DD." + "\u001B[0m");
            }
        }
// Solicitar nueva hora
        Time nuevaHora = null;
        while (true) {
            System.out.print("Nova hora (HH:MM) o prémer Enter per mantenir l'hora actual (" + reservaExistente.getHora() + "): ");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                nuevaHora = reservaExistente.getHora();
                break;
            }

            try {
                nuevaHora = Time.valueOf(input + ":00"); // Añadir ":00" para ajustarse al formato de Time (HH:MM:SS)
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("\u001B[31m" + "Error: El formato de hora debe ser HH:MM." + "\u001B[0m");
            }
        }

        // Solicitar nueva duración
        int nuevaDurada = 0;
        while (true) {
            System.out.print("Nova durada en minuts (actual: " + reservaExistente.getDurada() + " minuts): ");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                nuevaDurada = reservaExistente.getDurada();
                break;
            }

            try {
                nuevaDurada = Integer.parseInt(input);
                if (nuevaDurada > 0) {
                    break;
                } else {
                    System.out.println("\u001B[31m" + "Error: La durada ha de ser un número positiu." + "\u001B[0m");
                }
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31m" + "Error: La durada ha de ser només números enters." + "\u001B[0m");
            }
        }

        // Crear una nueva reserva con los valores actualizados
        return new Reserva(reservaExistente.getId(), reservaExistente.getMembreId(), reservaExistente.getPistaId(), nuevaFecha, nuevaHora, nuevaDurada ,reservaExistente.getCreated_at(),reservaExistente.getUpdated_at());
    }

    private static void modificarUsuaris(ReservaDAO reservaDAO) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduïu l'ID de l'usuari a modificar: ");
        int idUsuari = scanner.nextInt();

        // Obtener el usuario existente
        Membre usuariExistente = reservaDAO.obtenirUsuari(idUsuari);
        if (usuariExistente == null) {
            System.out.println(redText + "No s'ha trobat cap usuari amb aquest ID." + resetText);
            return;
        }

        // Mostrar información actual del usuario
        System.out.println(greenText + "Usuari actual: " + usuariExistente + resetText);

        // Solicitar nueva información del usuario
        scanner.nextLine();  // Limpiar el buffer
        System.out.print("Nou nom (o prémer Enter per mantenir el nom actual): ");
        String nouNom = scanner.nextLine();
        if (nouNom.isEmpty()) nouNom = usuariExistente.getNom();

        System.out.print("Nova adreça (o prémer Enter per mantenir el nom actual): ");
        String novaAdreca = scanner.nextLine();
        if (novaAdreca.isEmpty()) novaAdreca = usuariExistente.getAdreca();


        int telefon = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            System.out.print("Nou telefon (o prémer Enter per mantenir el telefon actual): ");
            String input = scanner.nextLine();  // Llegim tota la línia d'entrada

            if (input.isEmpty()) {
                // L'usuari ha premut Enter sense escriure res
                telefon = usuariExistente.getTelefono();
                entradaValida = true;  // Sortim del bucle mantenint el telèfon actual
            } else {
                try {
                    // Intentem convertir l'entrada en un enter
                    telefon = Integer.parseInt(input);
                    entradaValida = true;  // L'entrada és vàlida, sortim del bucle
                } catch (NumberFormatException e) {
                    // Si no és un enter, mostrem el missatge d'error
                    System.out.println("Error: Si us plau, introduïu un número enter vàlid.");
                }
            }
        }




        System.out.print("Nou correu electrònic (o prémer Enter per mantenir l'email actual): ");
        String nouEmail = scanner.nextLine();
        if (nouEmail.isEmpty()) nouEmail = usuariExistente.getEmail();

        // Actualizar el usuario en la base de datos
        Membre usuariActualitzat = new Membre(idUsuari, nouNom,novaAdreca,telefon, nouEmail,usuariExistente.getCreated_at(),usuariExistente.getUpdated_at());
        reservaDAO.actualitzarMembre(usuariActualitzat);
        System.out.println(greenText + "Usuari actualitzat correctament!" + resetText);
    }

    private static void modificarPistes(ReservaDAO reservaDAO) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduïu l'ID de la pista a modificar: ");
        int idPista = scanner.nextInt();

        // Obtener la pista existente
        Pista pistaExistente = reservaDAO.obtenirPista(idPista);
        if (pistaExistente == null) {
            System.out.println(redText + "No s'ha trobat cap pista amb aquest ID." + resetText);
            return;
        }

        // Mostrar información actual de la pista
        System.out.println(greenText + "Pista actual: " + resetText + pistaExistente);

        // Solicitar nueva información de la pista
        scanner.nextLine();  // Limpiar el buffer
        System.out.print("Nou nom de la pista (o prémer Enter per mantenir el nom actual): ");
        String nouNomPista = scanner.nextLine();
        if (nouNomPista.isEmpty()) nouNomPista = pistaExistente.getNom();

        System.out.print("Nou tipus o prem enter per mantenir el nom actual): ");
        String nouTipus = scanner.nextLine();
        if (nouTipus.isEmpty()) nouTipus = pistaExistente.getTipus();

        // Actualizar la pista en la base de datos
        Pista pistaActualitzada = new Pista(idPista, nouNomPista, nouTipus,pistaExistente.getCreated_at(),pistaExistente.getUpdated_at());
        reservaDAO.actualitzarPista(pistaActualitzada);
        System.out.println(greenText + "Pista actualitzada correctament!" + resetText);
    }




}
