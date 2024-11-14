package logica;

import dao.ReservaDAO;
import dto.Reserva;

import java.sql.Date;
import java.sql.Time;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MenuCase1 {

    static String greenText = "\u001B[32m";
    static String redText = "\u001B[31m";
    static String yellowText = "\u001B[33m";
    static String resetText = "\u001B[0m";

    public static void mostrarSubmenuAfegir(ReservaDAO reservaDAO) {
        Scanner scanner = new Scanner(System.in);
        int subopcio = -1;

        while (subopcio < 1 || subopcio > 4) {
            System.out.println("Afegir:");
            System.out.println("1. Afegir Usuari");
            System.out.println("2. Afegir Reserva");
            System.out.println("3. Afegir Pista");
            System.out.println("4. Tornar enrere");
            System.out.print(yellowText + "Selecciona una opció: " + resetText);

            try {
                subopcio = scanner.nextInt();
                if (subopcio < 1 || subopcio > 4) {
                    System.out.println(redText + "Opció no vàlida. Si us plau, selecciona una opció entre 1 i 4." + resetText);
                }
            } catch (InputMismatchException e) {
                System.out.println(redText + "Error: Entrada no vàlida. Si us plau, introdueix un número entre 1 i 4." + resetText);
                scanner.nextLine();  // Limpiar el buffer de entrada
            }
        }

        // Procedemos con la opción seleccionada
        switch (subopcio) {
            case 1:
                afegirUsuari(reservaDAO);
                break;

            case 2:
                afegirReserva(reservaDAO);
                break;

            case 3:
                afegirPista(reservaDAO);
                break;

            case 4:
                System.out.println(yellowText + "Tornant al menú principal..."+ resetText);
                return;  // Regresa al menú principal al salir del submenú

            default:
                System.out.println(redText + "Opció no vàlida." + resetText);
        }
    }
    private static boolean validarEmail(String email) {
        // Expresión regular para validar el formato de un email
        String regexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(regexEmail, email);
    }

    private static boolean validarTelefono(String telefono) {
        // Expresión regular para validar el teléfono (solo números)
        String regexTelefono = "^[0-9]{9,}$";  // Solo números, mínimo 9 dígitos
        return Pattern.matches(regexTelefono, telefono);
    }

    private static void afegirUsuari(ReservaDAO reservaDAO) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introdueix el nom de l'usuari: ");
        String nom = scanner.nextLine();

        System.out.print("Introdueix l'adreça: ");
        String adreca = scanner.nextLine();


        // Validar teléfono (solo números)
        String telefon = "";
        while (true) {
            System.out.print("Introdueix el telefon: ");
            telefon = scanner.nextLine();
            if (validarTelefono(telefon)) {
                break;  // Salir del bucle si el teléfono es válido
            } else {
                System.out.println(redText + "Error: El telèfon ha de ser minim 9 números." + resetText);
            }
        }

        // Validar email
        String email = "";
        while (true) {
            System.out.print("Introdueix l'email de l'usuari: ");
            email = scanner.nextLine();
            if (validarEmail(email)) {
                break;  // Salir del bucle si el email es válido
            } else {
                System.out.println( redText + "Error: El format de l'email no és correcte. EXEMPLE : nom@domini.com" + resetText);
            }
        }

        reservaDAO.afegirUsuari(nom, adreca, telefon, email);
    }

    private static void afegirReserva(ReservaDAO reservaDAO) {
        Scanner scanner = new Scanner(System.in);
        int membreId = 0;
        int pistaId = 0;
        int durada = 0;

        // Solicitar y validar Membre ID
        while (true) {
            System.out.print("Membre ID: ");
            try {
                membreId = scanner.nextInt();
                break;  // Salir del bucle si se ingresa un número válido
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31m" + "Error:L'ID ha de ser només números." + "\u001B[0m");
                scanner.nextLine(); // Limpiar el buffer para evitar un bucle infinito
            }
        }

        // Solicitar y validar Pista ID
        while (true) {
            System.out.print("Pista ID: ");
            try {
                pistaId = scanner.nextInt();
                break;  // Salir del bucle si se ingresa un número válido
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31m" + "Error: L'ID ha de ser només números." + "\u001B[0m");
                scanner.nextLine(); // Limpiar el buffer para evitar un bucle infinito
            }
        }


        Date data = obtenerFecha("Data (YYYY-MM-DD): ");
        Time hora = obtenerHora("Hora (HH:MM): ");


        // Solicitar y validar Membre ID
        while (true) {
            System.out.print("Hora: ");
            try {
                durada = scanner.nextInt();
                break;  // Salir del bucle si se ingresa un número válido
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31m" + "Error: Hora ha de ser en minuts, només números." + "\u001B[0m");
                scanner.nextLine(); // Limpiar el buffer para evitar un bucle infinito
            }
        }

        reservaDAO.afegirReserva(membreId, pistaId, data, hora, durada);
    }

    private static void afegirPista(ReservaDAO reservaDAO) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introdueix el nom de la pista: ");
        String nomPista = scanner.nextLine();

        System.out.print("Introdueix el tipus de pista (terra batuda, gespa.): ");
        String tipus = scanner.nextLine();

        reservaDAO.afegirPista(nomPista, tipus);
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

    private static Date obtenerFecha(String prompt) {
        Scanner scanner = new Scanner(System.in);
        Date fecha = null;
        boolean fechaValida = false;

        while (!fechaValida) {
            System.out.print(prompt);
            String fechaString = scanner.next();
            try {
                fecha = Date.valueOf(fechaString);  // Puede lanzar una excepción si el formato es incorrecto
                fechaValida = true;
            } catch (IllegalArgumentException e) {
                System.out.println(redText + "Error: Format de data incorrecte. Si us plau, utilitza el format YYYY-MM-DD." + resetText);
            }
        }
        return fecha;
    }

    private static Time obtenerHora(String prompt) {
        Scanner scanner = new Scanner(System.in);
        Time hora = null;
        boolean horaValida = false;

        while (!horaValida) {
            System.out.print(prompt);
            String horaString = scanner.next();

            // Si la hora no tiene segundos, añadir ":00"
            if (!horaString.contains(":")) {
                System.out.println(redText + "Error: El format d'hora ha de ser HH:MM, per exemple: 16:20." + resetText);
                continue;
            }

            // Si tiene un formato correcto de hora (HH:MM), añadir ":00" para completar
            if (horaString.length() == 5) {
                horaString = horaString + ":00";  // Añadir los segundos (00)
            }

            try {
                hora = Time.valueOf(horaString);  // Convertir el string con el formato HH:MM:SS
                horaValida = true;
            } catch (IllegalArgumentException e) {
                System.out.println(redText + "Error: Format d'hora incorrecte. Si us plau, utilitza el format HH:MM." + resetText);
            }
        }
        return hora;
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
}
